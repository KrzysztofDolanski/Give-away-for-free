package com.example.gaff.article;

import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {

    ArticleDto mapToArticleDto(Article newArticle) {
        return ArticleDto.builder()
                .id(newArticle.getId())
                .title(newArticle.getTitle())
                .description(newArticle.getDescription())
                .productCondition(newArticle.getProductCondition())
                .noOfVisits(newArticle.getNoOfVisits())
                .timeToPickup(newArticle.getTimeToPickup())
                .user(newArticle.getUser())
                .booking(newArticle.getBooking())
                .category(newArticle.getCategory())
                .build();
    }
}
