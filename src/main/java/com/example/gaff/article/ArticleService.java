package com.example.gaff.article;

import com.example.gaff.api_user.ApiUserDto;
import com.example.gaff.api_user.ApiUserMapping;
import com.example.gaff.api_user.ApiUserService;
import com.example.gaff.exceptions.NotFoundException;
import com.example.gaff.image.ArticleFileRepository;
import com.example.gaff.image.ArticleFiles;
import com.example.gaff.image.UploadPathService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final UploadPathService uploadPathService;
    private final ArticleFileRepository articleFileRepository;
    private final ApiUserMapping apiUserMapping;
    private final ApiUserService apiUserService;



    public List<ArticleDto> getAllArticle() {
        return articleMapper.mapToArticleDtoList(articleRepository.findAll());
    }

    public ArticleDto findArticleByTitle(String title) {
        return articleMapper.mapToArticleDto(articleRepository.findByTitle(title));
    }

    public ArticleDto findArticleById(Long id) {
        return articleMapper.mapToArticleDto(articleRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Not found location: " + id)));
    }

    public void saveArticle(ArticleDto articleDto, HttpServletRequest request){

        ApiUserDto userByUsername = apiUserService.getUserByUsername(apiUserService.currentUsername(request));

        Article article = articleMapper.mapToArticle(articleDto);

        articleRepository.save(article);
        article.setUserId(userByUsername.getId());

//        articlesUserService.saveArticlesUsersEntity(article);
//        apiUserService.saveUser(userByUsername);

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Object credentials = authentication.getCredentials();
//        article.setUser((ApiUser) credentials);

        if (article.getFiles() != null && article.getFiles().size() > 0) {
            for (MultipartFile file : article.getFiles()) {
                String fileName = file.getOriginalFilename();
                String modifiedFileName = FilenameUtils.getBaseName(fileName) + "_" + System.currentTimeMillis() + "." + FilenameUtils.getExtension(fileName);
                File storeFile = uploadPathService.getFilePath(modifiedFileName, "/images");
                if (storeFile != null) {
                    try {
                        FileUtils.writeByteArrayToFile(storeFile, file.getBytes());
                    } catch (Exception e) {
                        e.printStackTrace();
                   }
                }
                ArticleFiles files = new ArticleFiles();
                files.setFileExtension(FilenameUtils.getExtension(fileName));
                files.setFileName(fileName);
                files.setModifiedFilename(modifiedFileName);
                files.setArticle(article);

                articleFileRepository.save(files);
            }
        }
    }

}
