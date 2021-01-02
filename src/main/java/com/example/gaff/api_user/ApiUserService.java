package com.example.gaff.api_user;

import com.example.gaff.article.Article;
import com.example.gaff.exceptions.ApiUserAlreadyExistsException;
import com.example.gaff.image.UploadPathImpl;
import com.example.gaff.image.UploadPathService;
import com.example.gaff.image.UserFileRepository;
import com.example.gaff.image.UserFiles;
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
    final UploadPathService uploadPathService;
    final UserFileRepository userFileRepository;
    final ServletContext servletContext;

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

    public List<ApiUser> getAllUsers() {
        return apiUserRepository.findAll();
    }

    public ApiUserDto findById(Long userId) {
        Optional<ApiUser> byId = apiUserRepository.findById(userId);
        return byId.map(apiUserMapping::mapToApiUserDto).orElse(null);
    }

    public List<UserFiles> findFilesByUserId(Long userId) {
        return userFileRepository.findUserFilesByUserId(userId);
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
}
