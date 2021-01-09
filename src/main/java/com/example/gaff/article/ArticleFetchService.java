package com.example.gaff.article;

import com.example.gaff.api_user.ApiUser;
import com.example.gaff.exceptions.NotFoundException;
import com.example.gaff.image.ArticleFileRepository;
import com.example.gaff.image.ArticleFiles;
import com.example.gaff.image.UploadPathService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleFetchService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final UploadPathService uploadPathService;
    private final ArticleFileRepository articleFileRepository;

    public List<Article> getAllArticle() {
        return articleRepository.findAll();
    }

    public Article findArticleByTitle(String title) {
        return articleRepository.findByTitle(title);
    }

    public Article findArticleById(Long id) {
        return articleRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Not found location: " + id));
    }

    public void saveArticle(ArticleDto articleDto){
        Article article = articleMapper.mapToArticle(articleDto);

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Object credentials = authentication.getCredentials();
//        article.setUser((ApiUser) credentials);

        articleRepository.save(article);
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
