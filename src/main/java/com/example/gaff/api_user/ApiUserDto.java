package com.example.gaff.api_user;

import com.example.gaff.article.Article;
import com.example.gaff.booking.Booking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiUserDto {


    private String username;
    private String password;
    private String email;
    private String region;
    private String city;
    private String street;
    private String streetNo;
    private String zipCode;
    private LocalDateTime dateOfRegistration;
    private boolean isActive;
    private byte[] logotype;


    @OneToMany
    private List<Article> article;
    @OneToMany
    private List<Booking> booking;


    @OneToOne
    ConfirmationToken confirmationToken;
}
