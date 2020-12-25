package com.example.gaff.api_user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ApiUserServiceTest {

    @InjectMocks
    ApiUserService apiUserService;

    @Mock
    ApiUserMapping apiUserMapping;

    @Mock
    ApiUserRepository apiUserRepository;

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @Test
    void signUpUser() throws MessagingException {
        apiUserRepository.deleteAll();
        ApiUser apiUserDto = ApiUser.builder()
                .username("Krzystzof")
                .password("Krzysztof123")
                .email("k.dolanski@wp.pl")
                .city("Gdańsk")
                .build();


        ConfirmationToken confirmationToken = new ConfirmationToken(apiUserDto);
        apiUserRepository.save(apiUserDto);
        confirmationTokenRepository.save(confirmationToken);
//
//        apiUserMapping.mapToApiUser(apiUserDto);
//        apiUserService.signUpUser(apiUserDto);
    }
}
