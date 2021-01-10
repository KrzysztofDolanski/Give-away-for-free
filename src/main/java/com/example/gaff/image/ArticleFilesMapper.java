package com.example.gaff.image;

import org.springframework.stereotype.Component;

@Component
public class ArticleFilesMapper {

    public ArticleFiles mapToArticleFiles(ArticleFilesDto articleFilesDto) {
        return ArticleFiles.builder()
                .fileName(articleFilesDto.getFileName())
                .fileExtension(articleFilesDto.getFileExtension())
                .modifiedFilename(articleFilesDto.getModifiedFilename())
                .article(articleFilesDto.getArticle())
                .build();
    }

    public ArticleFilesDto mapToArticleFilesDto(ArticleFiles articleFiles) {
        return ArticleFilesDto.builder()
                .id(articleFiles.getId())
                .fileName(articleFiles.getFileName())
                .fileExtension(articleFiles.getFileExtension())
                .modifiedFilename(articleFiles.getModifiedFilename())
                .article(articleFiles.getArticle())
                .build();
    }
}

