package com.example.gaff.booking;

import com.example.gaff.api_user.ApiUser;
import com.example.gaff.article.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDto {

    private LocalDate bookingDate;
    private LocalDate collectionDate;
    private LocalDateTime collectionTime;

    private List<Article> article;
    private List<ApiUser> apiUsers;
}
