package com.example.gaff.article;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component

public class ArticleMapper {

    public ArticleDto mapToArticleDto(Article newArticle) {
        return ArticleDto.builder()
                .id(newArticle.getId())
                .title(newArticle.getTitle())
                .description(newArticle.getDescription())
                .productCondition(newArticle.getProductCondition())
                .noOfVisits(newArticle.getNoOfVisits())
                .dateOfOffer(newArticle.getDateOfOffer())
                .userId(newArticle.getUserId())
                .booking(newArticle.getBooking())
                .img(newArticle.getImg())
                .imageToFrontend(newArticle.getImageToFrontend())
                .build();
    }

    public Article mapToArticle(ArticleDto articleDto) {
        return Article.builder()
                .title(articleDto.getTitle())
                .description(articleDto.getDescription())
                .productCondition(articleDto.getProductCondition())
                .noOfVisits(articleDto.getNoOfVisits())
                .dateOfOffer(articleDto.getDateOfOffer())
                .userId(articleDto.getUserId())
                .booking(articleDto.getBooking())
                .img(articleDto.getImg())
                .imageToFrontend(articleDto.getImageToFrontend())
                .build();
    }

    public List<ArticleDto> mapToArticleDtoList(List<Article> articles) {
        return articles.stream().map(this::mapToArticleDto).collect(Collectors.toList());
    }

    public List<Article> mapToArticleList(List<ArticleDto> article) {
        return article.stream().map(this::mapToArticle).collect(Collectors.toList());
    }
}
