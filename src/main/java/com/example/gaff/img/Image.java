package com.example.gaff.img;

import com.example.gaff.api_user.ApiUser;
import com.example.gaff.article.Article;
import lombok.*;


import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {


    @Id
    @GeneratedValue
    private Long id;

    @Lob
    private byte[] img;

    private LocalDate createdDate;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private ApiUser user;


    @ManyToOne
    @JoinColumn(name="article_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Article article;

}

