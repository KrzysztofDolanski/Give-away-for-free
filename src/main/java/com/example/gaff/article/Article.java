package com.example.gaff.article;

import com.example.gaff.api_user.ApiUser;
import com.example.gaff.booking.Booking;
import com.example.gaff.category.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    @Id
    Long id;

    private String title;

    private String description;

    private byte[] photo;


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
