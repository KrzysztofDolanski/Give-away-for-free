package com.example.gaff.api_user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiUserRepository extends JpaRepository<ApiUser, Long> {

    ApiUser findByUsername(String username);
    ApiUser findByEmail(String email);
    
}
