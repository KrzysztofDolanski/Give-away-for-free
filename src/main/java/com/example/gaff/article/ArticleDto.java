package com.example.gaff.article;

import com.example.gaff.api_user.ApiUser;
import com.example.gaff.booking.Booking;
import com.example.gaff.category.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDto {


    Long id;
    private String title;
    private String description;
    private ProductCondition productCondition;
    private boolean isAvailable;
    private Long noOfVisits;
    private String dateOfOffer;
//
//    @ManyToOne
//    private ApiUser user;

    Long userId;


    @OneToOne
    private Booking booking;

    @ManyToOne
    private Category category;

    private List<MultipartFile> files = new ArrayList<MultipartFile>();
    private List<String> removeImages = new ArrayList<>();
}
