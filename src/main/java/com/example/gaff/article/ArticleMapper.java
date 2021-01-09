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
                .files(newArticle.getFiles())
                .removeImages(newArticle.getRemoveImages())
                .build();
    }

    public Article mapToArticle(ArticleDto articleDto) {
        return Article.builder()
                .id(articleDto.getId())
                .title(articleDto.getTitle())
                .description(articleDto.getDescription())
                .productCondition(articleDto.getProductCondition())
                .noOfVisits(articleDto.getNoOfVisits())
                .timeToPickup(articleDto.getTimeToPickup())
                .user(articleDto.getUser())
                .booking(articleDto.getBooking())
                .category(articleDto.getCategory())
                .files(articleDto.getFiles())
                .removeImages(articleDto.getRemoveImages())
                .build();
    }
}
