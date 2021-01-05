package com.example.gaff.article;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
public class ArticleController {
    final ArticleFetchService articleFetchService;
    final ArticleCreateService articleCreateService;
    final ArticleMapper articleMapper;

    @GetMapping("/article")
    List<Article> getAllArticle() {
        return articleFetchService.getAllArticle();
    }

    @GetMapping("/article/{title}")
    ArticleDto findArticleByTitle(String title) {
        Article article = articleFetchService.findArticleByTitle(title);
        return articleMapper.mapToArticleDto(article);
    }

    @GetMapping("/article/{id}")
    ArticleDto findByArticleById(@PathVariable Long id ){
        Article article = articleFetchService.findArticleById(id);
        return articleMapper.mapToArticleDto(article);
    }

    @PostMapping
    void addArticle() {
    }
}
