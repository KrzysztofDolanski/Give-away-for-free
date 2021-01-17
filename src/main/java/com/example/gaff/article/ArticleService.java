package com.example.gaff.article;

import com.example.gaff.api_user.ApiUserDto;
import com.example.gaff.api_user.ApiUserMapping;
import com.example.gaff.api_user.ApiUserService;
import com.example.gaff.exceptions.NotFoundException;
import com.example.gaff.image.ArticleFileRepository;
import com.example.gaff.image.ArticleFiles;
import com.example.gaff.image.UploadPathService;
import com.example.gaff.img.Image;
import com.example.gaff.img.ImageForm;
import com.example.gaff.img.ImageRepositoty;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.mapping.Collection;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor

public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final UploadPathService uploadPathService;
    private final ArticleFileRepository articleFileRepository;
    private final ApiUserMapping apiUserMapping;
    private final ApiUserService apiUserService;
    private final ImageRepositoty imageRepositoty;


    public List<ArticleDto> getAllArticle() {
        return articleMapper.mapToArticleDtoList(articleRepository.findAll());
    }

    public List<ArticleDto> getAllAvailableArticles() {
        return articleMapper.mapToArticleDtoList(articleRepository.getAllAvailableArticles());
    }

    public List<ArticleDto> getAllAvailableArticlesExceptLoggedUser(HttpServletRequest request) {
        ApiUserDto userByUsername = apiUserService.getUserByUsername(apiUserService.currentUsername(request));

        Stream<ArticleDto> articleDtoStream = articleMapper.mapToArticleDtoList(articleRepository.getAllAvailableArticles())
                .stream()
                .filter(articleDto -> !articleDto.userId.equals(userByUsername.getId()));
        return articleDtoStream.collect(Collectors.toList());

    }


    public ArticleDto findArticleByTitle(String title) {
        return articleMapper.mapToArticleDto(articleRepository.findByTitle(title));
    }

    public ArticleDto findArticleById(Long id) {
        return articleMapper.mapToArticleDto(articleRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Not found location: " + id)));
    }

    public void saveArticle(ArticleDto articleDto, HttpServletRequest request) {

        ApiUserDto userByUsername = apiUserService.getUserByUsername(apiUserService.currentUsername(request));

        Article article = articleMapper.mapToArticle(articleDto);

        articleRepository.save(article);
        article.setUserId(userByUsername.getId());

//        articlesUserService.saveArticlesUsersEntity(article);
//        apiUserService.saveUser(userByUsername);

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Object credentials = authentication.getCredentials();
//        article.setUser((ApiUser) credentials);

        if (article.getImage() != null && article.getImage().size() > 0) {
            for (Image file : article.getImage()) {

                Image files = new Image();
                files.setImg(file.getImg());
                files.setCreatedDate(LocalDate.now());
                files.setArticle(article);

                imageRepositoty.save(files);
            }
        }
    }
    }


    public void availabilityOfArticle(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow();
        article.setAvailable(false);
        articleRepository.save(article);
    }
}
