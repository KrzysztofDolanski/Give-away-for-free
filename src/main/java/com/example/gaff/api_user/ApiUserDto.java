package com.example.gaff.api_user;

import com.example.gaff.article.Article;
import com.example.gaff.booking.Booking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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

    private List<MultipartFile> files = new ArrayList<MultipartFile>();
    private List<String> removeImages = new ArrayList<>();
    private List<Article> article;
    private List<Booking> booking;
    ConfirmationToken confirmationToken;
}

