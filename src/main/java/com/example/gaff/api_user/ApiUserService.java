package com.example.gaff.api_user;

import com.example.gaff.api_user.map.GoogleMapsClientProperties;
import com.example.gaff.article.Article;
import com.example.gaff.article.ArticleDto;
import com.example.gaff.article.ArticleService;
import com.example.gaff.exceptions.ApiUserAlreadyExistsException;
import com.example.gaff.exceptions.NoUserInDataBaseFoundException;
import com.example.gaff.exceptions.NoUsernameException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ApiUserService implements UserDetailsService, MailService {

    private final ApiUserRepository apiUserRepository;
    private final ApiUserMapping apiUserMapping;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final GmailService gmailService;
    private final ServletContext servletContext;
//    private final ArticleService articleService;


    private static final String API_URL = "https://www.google.com/maps/embed/v1/place?key=";
    private static final String QUERY = "&q=";
    private final GoogleMapsClientProperties googleMapsClientProperties;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final ApiUser byUsername = apiUserRepository.findByUsername(username);
        if (!byUsername.getUsername().isEmpty()) {
            return new org.springframework.security.core.userdetails.User(byUsername.getUsername(), byUsername.getPassword(), byUsername.getAuthorities());
        } else {
            throw new UsernameNotFoundException(MessageFormat.format("User with username {0} cannot be found. ", username));
        }
    }


    public boolean isEmailValid(String email) {
        final Pattern EMAIL_REGEX = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);
       return EMAIL_REGEX.matcher(email).matches();
    }


    @Override
    public void sendEmail(Email email) {
    }

    public void signUpUser(ApiUserDto apiUserDto, byte[] multipartFile) throws MessagingException, IOException, ApiUserAlreadyExistsException {
        if ((apiUserRepository.findByUsername(apiUserDto.getUsername())) != null) {
            throw new ApiUserAlreadyExistsException("User with this name already exist");

        }
        final String encryptedPassword = bCryptPasswordEncoder().encode(apiUserDto.getPassword());
        apiUserDto.setPassword(encryptedPassword);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        apiUserDto.setDateOfRegistration(LocalDateTime.now().format(df));

        apiUserDto.setImg(multipartFile);

        ApiUser apiUser = apiUserMapping.mapToApiUser(apiUserDto);

        apiUserRepository.save(apiUser);
        ConfirmationToken confirmationToken = new ConfirmationToken(apiUser);
        confirmationTokenRepository.save(confirmationToken);
        sendConfirmationEmail(apiUserDto.getEmail(), confirmationToken.getConfirmationToken());
    }

    public void confirmUser(ConfirmationToken confirmationToken) {
        ApiUser apiUser = confirmationToken.getApiUser();
        apiUser.setEnabled(true);
        apiUserRepository.save(apiUser);
        confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());
    }

    public void sendConfirmationEmail(String userEmail, String token) {
        String subject = "GIVE AWAY FOR FREE confirmation email";
        String content = "Please click to below link to active your account http://localhost:8080/register/confirm?token=" + token;
        MailConfiguration mailConfiguration = new MailConfiguration();
        Email email = new Email(userEmail, subject, content);
        new GmailService(mailConfiguration).sendEmail(email);
    }

    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public List<Article> showUserArticles(String username) {
        ApiUser byUsername = apiUserRepository.findByUsername(username);
        return byUsername.getArticle();
    }

    public ApiUserDto getUserByUsername(String username) {
        return apiUserMapping.mapToApiUserDto(apiUserRepository.findByUsername(username));
    }

    public List<ApiUserDto> getAllUsers() {
        List<ApiUser> all = apiUserRepository.findAll();
        return apiUserMapping.mapToApiUserDtoList(all);
    }

    public ApiUserDto findById(Long userId) {
        Optional<ApiUser> byId = apiUserRepository.findById(userId);
        return byId.map(apiUserMapping::mapToApiUserDto).orElse(null);
    }


    public ApiUserDto update(ApiUserDto apiUserDto) {
        ApiUser apiUser1 = apiUserMapping.mapToApiUser(apiUserDto);
            apiUserRepository.save(apiUser1);
        return apiUserMapping.mapToApiUserDto(apiUser1);
    }

    public String createGoogleMapQuery(String username) throws NoUsernameException {
        ApiUser byUsername = apiUserRepository.findByUsername(username);
        if (byUsername.getUsername() != null) {
            return API_URL + googleMapsClientProperties.getToken() + QUERY + byUsername.getCity() + "," + byUsername.getStreet() + byUsername.getStreetNo();
        } else throw new NoUsernameException("No username: " + username + " found");
    }


    public String currentUsername(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        return principal.getName();
    }

    public void saveUser(ApiUserDto userByUsername) {
        ApiUser apiUser = apiUserMapping.mapToApiUser(userByUsername);
        apiUserRepository.save(apiUser);
    }

    public String getAcctualImage() {
        if (getAllUsers().size()>0){
            return new String(Base64.getEncoder().encode(getAllUsers().get(getAllUsers().size() - 1).getImg()));
        }else return "";
    }

    //todo wyświetlenie dystansu pomiędzy

    public String createDistanceMapQuery(Long userId, HttpServletRequest hsr){
        String currentUsername = currentUsername(hsr);
        ApiUser currentUser = apiUserRepository.findByUsername(currentUsername);
        ApiUser apiUser = apiUserRepository.findById(userId).orElseThrow();

        return "https://maps.googleapis.com/maps/api/directions/json?origin="
                + currentUser.getCity() + "," + currentUser.getStreet() +","+ currentUser.getStreetNo()
                + "&destination="
                + apiUser.getCity() + "," + apiUser.getStreet() + "," + apiUser.getStreetNo()
                + "&key=" + googleMapsClientProperties.getToken();
    }
}
