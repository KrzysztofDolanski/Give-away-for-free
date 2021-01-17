package com.example.gaff.api_user;

import com.example.gaff.article.Article;
import com.example.gaff.article.ArticleDto;
import com.example.gaff.booking.Booking;
import com.example.gaff.img.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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
    private String region;
    private String city;
    private String street;
    private String streetNo;
    private String zipCode;
    private String dateOfRegistration;

    @Lob
    private byte[] img;


//    private Image image;

//    private List<MultipartFile> files = new ArrayList<MultipartFile>();
//    private List<String> removeImages = new ArrayList<>();

    private List<ArticleDto> article;
    private List<Booking> booking;
    ConfirmationToken confirmationToken;
}

