package com.example.gaff.image;

import com.example.gaff.article.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleFilesDto {

    private Long id;
    private String fileName;
    private String modifiedFilename;
    private String fileExtension;

    private Article article;
}
