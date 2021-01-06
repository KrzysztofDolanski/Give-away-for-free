package com.example.gaff.article;

import com.example.gaff.api_user.ApiUser;
import com.example.gaff.article.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ArticleFetchService {
    private final ArticleRepository articleRepository;

    public List<Article> getAllArticle() {

        return articleRepository.findAll();
    }

    public Article findArticleByTitle(String title) {
        return articleRepository.findArticleByTitle(title);
    }

    public Article findArticleById(Long id) {
        return articleRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Not found location: " + id));
    }

    }


