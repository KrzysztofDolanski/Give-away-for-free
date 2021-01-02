package com.example.gaff.article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    List<Article> getAllArticle() {

        return articleRepository.findAll();
    }

    Article findArticleByTitle(String title) {
        return articleRepository.findArticleByTitle(title);
    }


}
