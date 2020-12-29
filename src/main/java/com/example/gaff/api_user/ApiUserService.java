package com.example.gaff.api_user;

import com.example.gaff.article.Article;
import com.example.gaff.exceptions.ApiUserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ApiUserService implements UserDetailsService, MailService {

    private final ApiUserRepository apiUserRepository;
    private final ApiUserMapping apiUserMapping;

    final ConfirmationTokenRepository confirmationTokenRepository;
    final ConfirmationTokenService confirmationTokenService;
    final GmailService gmailService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final ApiUser byUsername = apiUserRepository.findByUsername(username);
        if (!byUsername.getUsername().isEmpty()) {
            return byUsername;
        } else {
            throw new UsernameNotFoundException(MessageFormat.format("User with username {0} cannot be found. ", username));
        }
    }

    public void signUpUser(ApiUserDto apiUserDto) throws MessagingException, IOException, ApiUserAlreadyExistsException {
        if ((apiUserRepository.findByUsername(apiUserDto.getUsername())) != null) {
            throw new ApiUserAlreadyExistsException("User with this name already exist");
        }

        final String encryptedPassword = bCryptPasswordEncoder().encode(apiUserDto.getPassword());
        apiUserDto.setPassword(encryptedPassword);
//        File file = new File(Arrays.toString(apiUserDto.getLogotype()));
//        byte[] bytes = Files.readAllBytes(file.toPath());
//        apiUserDto.setLogotype(bytes);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        apiUserDto.setDateOfRegistration(LocalDateTime.now().format(df));
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

    @Override
    public void sendEmail(Email email) {
    }

    public List<Article> showUserArticles(String username) {
        ApiUser byUsername = apiUserRepository.findByUsername(username);
        return byUsername.getArticle();
    }

    public ApiUser getUserByUsername(String username) {
        return apiUserRepository.findByUsername(username);
    }
}
