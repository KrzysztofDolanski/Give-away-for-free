package com.example.gaff.article;

import com.example.gaff.api_user.ApiUser;
import com.example.gaff.booking.Booking;
import com.example.gaff.category.Category;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private ProductCondition productCondition;
    private boolean isAvailable;
    private Long noOfVisits;
    private LocalDateTime timeToPickup;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    private ApiUser user;


    Long userId;


    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Booking booking;

    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Category category;

    @Transient
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<MultipartFile> files = new ArrayList<MultipartFile>();

    @Transient
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<String> removeImages = new ArrayList<>();
}
