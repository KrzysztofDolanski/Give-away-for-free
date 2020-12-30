package com.example.gaff.api_user;

import com.example.gaff.article.Article;
import com.example.gaff.booking.Booking;
import com.example.gaff.image.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
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
    private String dateOfRegistration;
    private boolean isActive;

    private byte[] logotype;

    @OneToMany
    private List<Article> article;
    @OneToMany
    private List<Booking> booking;


    @OneToOne
    ConfirmationToken confirmationToken;

//    @Transient
//    public String getLogotype() {
//        if (logotype == null || username == null) return null;
//
//        return "/user-photos/" + username + "/" + logotype;
//    }
}

