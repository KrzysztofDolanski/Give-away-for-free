package com.example.gaff.api_user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.stereotype.Repository;
import org.springframework.test.web.servlet.MockMvc;

import javax.mail.MessagingException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApiUserRepositoryTest {


    @Autowired
    MockMvc mockMvc;

    ApiUser one;

    @Autowired
    ApiUserRepository apiUserRepository;

    @Autowired
    ApiUserService apiUserService;

    @Autowired
    ApiUserMapping apiUserMapping;


    @BeforeEach
    void setup() throws MessagingException {
        apiUserRepository.deleteAll();
        ApiUser one = new ApiUser();
        one.setUsername("Krzysztof");
        one.setPassword("Krzysztof123");
        one.setEmail("k.dolanski@wp.pl");
//        ApiUserDto apiUserDto = apiUserMapping.mapToApiUserDto(one);

//        apiUserService.signUpUser(apiUserDto);
        apiUserRepository.save(one);

    }

    @Test
    void getUserFromDatabase() throws Exception {
        //given
        Long id = one.getId();
        //when

        //then
    }

}
