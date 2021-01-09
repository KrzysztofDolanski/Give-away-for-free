package com.example.gaff.image;

import com.example.gaff.api_user.ApiUser;
import com.example.gaff.article.Article;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table (name = "article_files")
public class ArticleFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String modifiedFilename;
    private String fileExtension;
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;
}
