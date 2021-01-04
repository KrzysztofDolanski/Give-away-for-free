package com.example.gaff.article;

import com.example.gaff.api_user.ApiUser;
import com.example.gaff.booking.Booking;
import com.example.gaff.category.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

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
    private LocalDateTime timeToPickup;

    @ManyToOne
    private ApiUser user;
    @OneToOne
    private Booking booking;
    @ManyToOne
    private Category category;
}
