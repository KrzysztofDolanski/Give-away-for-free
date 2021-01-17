package com.example.gaff.img;

import com.example.gaff.api_user.ApiUser;
import com.example.gaff.article.Article;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {


    private Long id;

    @Lob
    private byte[] img;

    private ApiUser user;

    private Article article;


}
