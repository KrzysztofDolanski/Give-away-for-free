package com.example.gaff.article;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ArticleService articleService;

    @Autowired
    ArticleMapper articleMapper;

    @Test
    void saveArticle() {
        //given
        Article article = new Article();
        article.setTitle("Aasdas");
        ArticleDto articleDto = new ArticleDto();
        articleDto.setTitle("aaa");
        HttpServletRequest request = null;

        byte[] image = new byte[2];
        //when
        articleRepository.save(article);
        articleMapper.mapToArticle(articleDto);
        articleService.saveArticle(articleDto, request, image);
        Article aasdas = articleRepository.findByTitle("Aasdas");
        //then
        assertEquals(aasdas.getTitle(), article.getTitle());

    }
}
