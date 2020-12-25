package com.example.gaff.api_user;

import com.example.gaff.exceptions.NoUsernameException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ApiUserService implements UserDetailsService, MailService {

    private final ApiUserRepository apiUserRepository;
    private final ApiUserMapping apiUserMapping;

    final ConfirmationTokenRepository confirmationTokenRepository;
    final ConfirmationTokenService confirmationTokenService;
    MailService mailService;
    final GmailService gmailService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final ApiUser byEmail = apiUserRepository.findByEmail(email);
        if (!byEmail.getUsername().isEmpty()) {
            return byEmail;
        } else {
            throw new UsernameNotFoundException(MessageFormat.format("User with email {0} cannot be found. ", email));
        }
    }

    public void signUpUser(ApiUserDto apiUserDto) throws MessagingException {
        final String encryptedPassword = bCryptPasswordEncoder().encode(apiUserDto.getPassword());
        apiUserDto.setPassword(encryptedPassword);
        ApiUser apiUser = apiUserMapping.mapToApiUser(apiUserDto);
        apiUserRepository.save(apiUser);
        ConfirmationToken confirmationToken = new ConfirmationToken(apiUser);
        confirmationTokenRepository.save(confirmationToken);
        log.info(confirmationToken.toString());
        sendConfirmationEmail(apiUserDto.getEmail(),confirmationToken.getConfirmationToken());
//        confirmationTokenService.saveConfirmationToken(confirmationToken);
    }

    public void confirmUser(ConfirmationToken confirmationToken) {
        ApiUser apiUser = confirmationToken.getApiUser();
        apiUser.setEnabled(true);
        apiUserRepository.save(apiUser);
        confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());
    }

    public void sendConfirmationEmail(String userEmail, String token) throws MessagingException {
        String subject = "GIVE AWAY FOR FREE confirmation email";
        String content = "Please click to below link to active your account http://localhost:8080/sign-up/confirm?token="+token;
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
}
