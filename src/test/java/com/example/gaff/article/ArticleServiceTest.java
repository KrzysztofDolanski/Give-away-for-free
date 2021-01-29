package com.example.gaff.article;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.util.List;

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
    void saveArticle() throws SQLException {
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
        List<Article> aasdas = articleRepository.findArticlesByTitle("Aasdas");
        //then
        assertEquals(aasdas.get(0).getTitle(), article.getTitle());

    }
}
