package com.example.gaff.article;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
public class ArticleController {
    final ArticleService articleService;
    final ArticleMapper articleMapper;

    @GetMapping("/article")
    List<Article> getAllArticle(){
        return articleService.getAllArticle();
    }

    @GetMapping("/article/{title}")
    ArticleDto findArticleByTitle(String title) {
        Article article = articleService.findArticleByTitle(title);
        return articleMapper.mapToArticleDto(article);
    }

    @PostMapping
    void addArticle() {
    }
}
