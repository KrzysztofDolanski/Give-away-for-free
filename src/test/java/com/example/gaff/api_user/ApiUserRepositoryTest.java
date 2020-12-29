package com.example.gaff.api_user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.mail.MessagingException;

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
