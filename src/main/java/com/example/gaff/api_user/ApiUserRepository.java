package com.example.gaff.api_user;

import com.example.gaff.image.UserFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiUserRepository extends JpaRepository<ApiUser, Long> {

    ApiUser findByUsername(String username);
    ApiUser findByEmail(String email);


}
