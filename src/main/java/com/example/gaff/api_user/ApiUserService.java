package com.example.gaff.api_user;

import com.example.gaff.api_user.localisation.GoogleMapsClientProperties;
import com.example.gaff.article.Article;
import com.example.gaff.exceptions.ApiUserAlreadyExistsException;
import com.example.gaff.exceptions.NoUsernameException;
import com.example.gaff.exceptions.UserIdNotFoundException;
import com.example.gaff.image.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final UploadPathService uploadPathService;
    private final UserFileRepository userFileRepository;
    private final ServletContext servletContext;
    private final UserFilesMapper userFilesMapper;


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

    @Override
    public void sendEmail(Email email) {
    }

    public void signUpUser(ApiUserDto apiUserDto) throws MessagingException, IOException, ApiUserAlreadyExistsException {
        if ((apiUserRepository.findByUsername(apiUserDto.getUsername())) != null) {
            throw new ApiUserAlreadyExistsException("User with this name already exist");
        }
        final String encryptedPassword = bCryptPasswordEncoder().encode(apiUserDto.getPassword());
        apiUserDto.setPassword(encryptedPassword);
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        apiUserDto.setDateOfRegistration(LocalDateTime.now().format(df));
        ApiUser apiUser = apiUserMapping.mapToApiUser(apiUserDto);
        if (apiUser.getFiles() != null && apiUser.getFiles().size() > 0) {
            for (MultipartFile file : apiUser.getFiles()) {
                String fileName = file.getOriginalFilename();
                String modifiedFileName = FilenameUtils.getBaseName(fileName) + "_" + System.currentTimeMillis() + "." + FilenameUtils.getExtension(fileName);
                File storeFile = uploadPathService.getFilePath(modifiedFileName, "/images");
                if (storeFile != null) {
                    try {
                        FileUtils.writeByteArrayToFile(storeFile, file.getBytes());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                UserFiles files = new UserFiles();
                files.setFileExtension(FilenameUtils.getExtension(fileName));
                files.setFileName(fileName);
                files.setModifiedFilename(modifiedFileName);
                files.setUser(apiUser);
                userFileRepository.save(files);
            }
        }
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

    public List<UserFilesDto> findFilesByUserId(Long userId) {
        List<UserFiles> userFilesByUserId = userFileRepository.findUserFilesByUserId(userId);
        return userFilesByUserId.stream().map(userFilesMapper::mapToUserFilesDto).collect(Collectors.toList());
    }

    public ApiUserDto update(ApiUserDto apiUserDto) {
        ApiUser apiUser1 = apiUserMapping.mapToApiUser(apiUserDto);
        if (apiUser1 != null && apiUser1.getRemoveImages() != null && apiUser1.getRemoveImages().size() > 0) {
            userFileRepository.deleteUserFilesByUserIdAndFileName(apiUser1.getId(), apiUser1.getRemoveImages());
            for (String file : apiUser1.getRemoveImages()) {
                File dbFile = new File(servletContext.getRealPath("/images/" + File.separator + file));
                if (dbFile.exists()) {
                    dbFile.delete();
                }
            }
        }
        if (apiUser1 != null && apiUser1.getFiles().size() > 0) {
            for (MultipartFile file : apiUser1.getFiles()) {
                if (file != null && StringUtils.hasText(file.getOriginalFilename())) {
                    String fileName = file.getOriginalFilename();
                    String modifiedFileName = FilenameUtils.getBaseName(fileName) + "_" + System.currentTimeMillis() + "." + FilenameUtils.getExtension(fileName);
                    File storeFile = uploadPathService.getFilePath(modifiedFileName, "images");
                    if (storeFile != null) {
                        try {
                            FileUtils.writeByteArrayToFile(storeFile, file.getBytes());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    UserFiles files = new UserFiles();
                    files.setFileExtension(FilenameUtils.getExtension(fileName));
                    files.setFileName(fileName);
                    files.setModifiedFilename(modifiedFileName);
                    files.setUser(apiUser1);
                    userFileRepository.save(files);
                }
            }
            apiUserRepository.save(apiUser1);
        }
        return apiUserMapping.mapToApiUserDto(apiUser1);
    }

    public String createGoogleMapQuery(String username) throws NoUsernameException {
        ApiUser byUsername = apiUserRepository.findByUsername(username);
        if (byUsername.getUsername() != null) {
            return API_URL + googleMapsClientProperties.getToken() + QUERY + byUsername.getCity() + "," + byUsername.getStreet() + byUsername.getStreetNo();
        } else throw new NoUsernameException("No username: " + username + " found");
    }
}
