package com.example.gaff.booking;

import com.example.gaff.api_user.ApiUser;
import com.example.gaff.article.Article;
import lombok.*;

import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDto {

    Long id;
    private LocalDate bookingDate;
    private LocalDate collectionDate;
    private LocalDateTime collectionTime;

    private Long articleId;

    private Long sellerId;

    private Long buyerId;
}
