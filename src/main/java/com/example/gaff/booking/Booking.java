package com.example.gaff.booking;

import com.example.gaff.api_user.ApiUser;
import com.example.gaff.article.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue
    Long bookingId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate bookingDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime collectionDateTime;

    @OneToOne
    private Article article;

    @OneToMany
    List<ApiUser> apiUsers = new ArrayList<>();

}
