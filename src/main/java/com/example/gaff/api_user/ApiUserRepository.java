package com.example.gaff.api_user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiUserRepository extends JpaRepository<ApiUser, Long> {

    ApiUser findByUsername(String username);
    ApiUser findByEmail(String email);



//    List<ApiUser> getApiUsersById(List<Long> id);
}
