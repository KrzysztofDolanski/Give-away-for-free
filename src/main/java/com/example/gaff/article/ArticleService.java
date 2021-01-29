package com.example.gaff.article;

import com.example.gaff.api_user.ApiUserDto;
import com.example.gaff.api_user.ApiUserService;
import com.example.gaff.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor

public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final ApiUserService apiUserService;


    public List<ArticleDto> getAllArticle() {
        return articleMapper.mapToArticleDtoList(articleRepository.findAll());
    }

    public List<ArticleDto> getAllAvailableArticles() {
        List<ArticleDto> articleDtos = articleMapper.mapToArticleDtoList(articleRepository.getAllAvailableArticles());

        for (ArticleDto articleDto : articleDtos) {
            articleDto.setImageToFrontend(new String(Base64.getEncoder().encode(articleDto.getImg())));
        }
        return articleDtos;
    }

    public List<ArticleDto> getAllAvailableArticlesExceptLoggedUser(HttpServletRequest request) {
        
        List<ArticleDto> collect = null;
        try {
        ApiUserDto userByUsername = apiUserService.getUserByUsername(apiUserService.currentUsername(request));
        Stream<ArticleDto> articleDtoStream = articleMapper.mapToArticleDtoList(articleRepository.getAllAvailableArticles())
                .stream()
                .filter(articleDto -> !articleDto.getUserId().equals(userByUsername.getId()));
        collect = articleDtoStream.collect(Collectors.toList());
        } catch (NullPointerException e){
            e.getMessage();
        }

        for (ArticleDto articleDto : collect) {
            articleDto.setImageToFrontend(new String(Base64.getEncoder().encode(articleDto.getImg())));
        }
        return collect;

    }

    public List<ArticleDto> findArticleByTitle(String title) {
        return articleMapper.mapToArticleDtoList(articleRepository.findArticlesByTitle(title));
    }

    public ArticleDto findArticleById(Long id) {
        return articleMapper.mapToArticleDto(articleRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Not found location: " + id)));
    }

    public void saveArticle(ArticleDto articleDto, HttpServletRequest request, byte[] multipartFile) throws SQLException {
        boolean flag = true;
        while (flag) {
        ApiUserDto userByUsername = apiUserService.getUserByUsername(apiUserService.currentUsername(request));
        articleDto.setImg(multipartFile);
            try{
                Article article = articleMapper.mapToArticle(articleDto);
                article.setUserId(userByUsername.getId());
                if (articleDto.getTitle().length()<60 && articleDto.getDescription().length()<60){
                articleRepository.save(article);
                flag = false;
                }else break;
            } catch (TransactionSystemException e){
                e.getStackTrace();
                flag = false;
            }

        }
    }


    public void availabilityOfArticle(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        article.setAvailable(false);
        articleRepository.save(article);
    }

    public List<String> getImagesOfAllAvailableArticles() {
        return getAllAvailableArticles().stream()
                .map(articles ->
                        new String(Base64.getEncoder().encode(articles.getImg())))
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        articleRepository.deleteById(id);
    }

    public List<ArticleDto> getAllUserArtiles(HttpServletRequest request) {
        List<ArticleDto> collect = null;
        try {
            ApiUserDto userByUsername = apiUserService.getUserByUsername(apiUserService.currentUsername(request));
            Stream<ArticleDto> articleDtoStream = articleMapper.mapToArticleDtoList(articleRepository.getAllAvailableArticles())
                    .stream()
                    .filter(articleDto -> articleDto.getUserId().equals(userByUsername.getId()));
            collect = articleDtoStream.collect(Collectors.toList());
        } catch (NullPointerException e){
            e.getMessage();
        }

        for (ArticleDto articleDto : collect) {
            articleDto.setImageToFrontend(new String(Base64.getEncoder().encode(articleDto.getImg())));
        }
        return collect;
    }
}
