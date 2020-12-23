package com.example.gaff.api_user;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Repository

class ApiUserRepositoryTest {

    @Mock
    ApiUserRepository apiUserRepository;

    @Test
    void saveUserInDatabase(){
        ApiUser apiUser = new ApiUser();
        apiUser.setUsername("Krzysztof");
        apiUser.setPassword("Krzystzof123");
        apiUser.setEmail("k.dolanski@wp.pl");
        apiUserRepository.save(apiUser);

        ApiUser one = apiUserRepository.findByEmail("k.dlanski@wp.pl");

        assertEquals(apiUser.getUsername(),one.getUsername());
    }

}
