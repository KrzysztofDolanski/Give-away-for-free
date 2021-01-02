package com.example.gaff.category;

import com.example.gaff.article.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    private Long id;
    private String categoryName;
    private String description;
    private byte[] logotype;

    @OneToMany
    private List<Article> article;
}
