package com.example.gaff.article;

import com.example.gaff.api_user.ApiUser;
import com.example.gaff.booking.Booking;
import com.example.gaff.category.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String title;
    private String description;
    private ProductCondition productCondition;
    private boolean isAvailable;
    private Long noOfVisits;
    private LocalDateTime timeToPickup;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApiUser user;
    @OneToOne
    private Booking booking;
    @ManyToOne
    private Category category;

    @Transient
    private List<MultipartFile> files = new ArrayList<MultipartFile>();

    @Transient
    private List<String> removeImages = new ArrayList<>();
}
