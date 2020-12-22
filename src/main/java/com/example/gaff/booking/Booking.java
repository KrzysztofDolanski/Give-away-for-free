package com.example.gaff.booking;

import com.example.gaff.api_user.ApiUser;
import com.example.gaff.article.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    Long id;


    @OneToOne
    private Article article;
    @OneToMany
    private List<ApiUser> user;
    private LocalDateTime timeToPickup;
}
