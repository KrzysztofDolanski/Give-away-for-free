package com.example.gaff.image;

import com.example.gaff.api_user.ApiUser;
import com.example.gaff.article.Article;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "article_files")
public class ArticleFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fileName;
    private String modifiedFilename;
    private String fileExtension;

    @ManyToOne
    @JoinColumn(name="article_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Article article;
}
