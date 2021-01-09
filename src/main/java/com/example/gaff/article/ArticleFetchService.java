package com.example.gaff.article;

import com.example.gaff.exceptions.NotFoundException;
import com.example.gaff.image.ArticleFileRepository;
import com.example.gaff.image.ArticleFiles;
import com.example.gaff.image.UploadPathService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleFetchService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final UploadPathService uploadPathService;
    private final ArticleFileRepository articleFileRepository;

    List<Article> getAllArticle() {
        return articleRepository.findAll();
    }

    Article findArticleByTitle(String title) {
        return articleRepository.findByTitle(title);
    }

    Article findArticleById(Long id) {
        return articleRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Not found location: " + id));
    }

    public void saveArticle(ArticleDto articleDto){
        Article article = articleMapper.mapToArticle(articleDto);
//        if (article.getFiles() != null && article.getFiles().size() > 0) {
//            for (MultipartFile file : article.getFiles()) {
//                String fileName = file.getOriginalFilename();
//                String modifiedFileName = FilenameUtils.getBaseName(fileName) + "_" + System.currentTimeMillis() + "." + FilenameUtils.getExtension(fileName);
//                File storeFile = uploadPathService.getFilePath(modifiedFileName, "/images");
//                if (storeFile != null) {
//                    try {
//                        FileUtils.writeByteArrayToFile(storeFile, file.getBytes());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                ArticleFiles files = new ArticleFiles();
//                files.setFileExtension(FilenameUtils.getExtension(fileName));
//                files.setFileName(fileName);
//                files.setModifiedFilename(modifiedFileName);
//                files.setArticle(article);
//
//                articleFileRepository.save(files);

//            }
//        }
        articleRepository.save(article);
    }
    }

}
