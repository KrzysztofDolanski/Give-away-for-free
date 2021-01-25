package com.example.gaff.api_user;

import com.example.gaff.article.ArticleDto;
import com.example.gaff.booking.Booking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiUserDto {

    Long id;
    @NotBlank (message = "Fill your username")
    private String username;
    @NotBlank (message = "Fill your password")
    private String password;
    @NotNull
    @Email (message = "Fill proper email address")
    private String email;
    private String phone;
    private String region;
    private String city;
    private String street;
    private String streetNo;
    private String zipCode;
    private String dateOfRegistration;

    @Lob
    private byte[] img;

    private List<ArticleDto> article;
    private List<Booking> booking;
    ConfirmationToken confirmationToken;
}

