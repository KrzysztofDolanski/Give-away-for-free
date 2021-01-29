package com.example.gaff.article;

import com.example.gaff.booking.Booking;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Transactional
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 60)
    private String title;
    @Column(length = 60)
    private String description;
    private ProductCondition productCondition;
    @Builder.Default
    private boolean isAvailable = true;
    private Long noOfVisits;
    private String dateOfOffer;
    private Long userId;
    @OneToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Booking booking;
    @Lob
    private byte[] img;

    private String imageToFrontend;

}
