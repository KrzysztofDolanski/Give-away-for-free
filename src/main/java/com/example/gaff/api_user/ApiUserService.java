package com.example.gaff.api_user;

import com.example.gaff.exceptions.NoUsernameException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

@Service
@RequiredArgsConstructor
@Transactional
public class ApiUserService implements UserDetailsService {

    private final ApiUserRepository apiUserRepository;
    private final ApiUserMapping apiUserMapping;
    final PasswordEncoder passwordEncoder;
    final ConfirmationTokenService confirmationTokenService;
    final EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final ApiUser byEmail = apiUserRepository.findByEmail(email);
        if (!byEmail.getUsername().isEmpty()) {
            return byEmail;
        } else {
            throw new UsernameNotFoundException(MessageFormat.format("User with email {0} cannot be found. ", email));
        }
    }

    public void signUpUser(ApiUserDto apiUserDto) {
        final String encryptedPassword = passwordEncoder.encode(apiUserDto.getPassword());
        apiUserDto.setPassword(encryptedPassword);
        ApiUser apiUser = apiUserMapping.mapToApiUser(apiUserDto);
        apiUserRepository.save(apiUser);
        ConfirmationToken confirmationToken = new ConfirmationToken(apiUser);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
    }

    public void confirmUser(ConfirmationToken confirmationToken) {
        ApiUser apiUser = confirmationToken.getApiUser();
        apiUser.setEnabled(true);
        apiUserRepository.save(apiUser);
        confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());
    }

    public void sendConfirmationEmail(String userEmail, String token){
        final SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userEmail);
        mailMessage.setFrom("dolanskicwiczeniajava@gmail.com");
        mailMessage.setSubject("GIVE AWAY FOR FREE confirmation email");
        mailMessage.setText("plese click to below link to active your account\n"
                + "http://localhost:8080/sign-up/confirm?token="+token);
        emailService.sendEmail(mailMessage);
    }

}
