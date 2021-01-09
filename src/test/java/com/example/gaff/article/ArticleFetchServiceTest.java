package com.example.gaff.article;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ArticleFetchServiceTest {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ArticleFetchService articleFetchService;

    @Autowired
    ArticleMapper articleMapper;

    @Test
    void saveArticle() {
        //given
        Article article = new Article();
        article.setTitle("Aasdas");
        ArticleDto articleDto = new ArticleDto();
        articleDto.setTitle("aaa");
        //when
        articleRepository.save(article);
        articleMapper.mapToArticle(articleDto);
        articleFetchService.saveArticle(articleDto);
        Article aasdas = articleRepository.findByTitle("Aasdas");
        //then
        assertEquals(aasdas.getTitle(), article.getTitle());

    }
}
