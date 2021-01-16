package com.example.gaff.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleFilesService {

    private final ArticleFileRepository articleFileRepository;

    public List<ArticleFiles> findFileByArticleId(Long id){
        return articleFileRepository.findArticleFilesByArticleId(id);
    }

    public List<ArticleFiles> getAllFiles() {
        return articleFileRepository.findAll();
    }
}
