package com.example.gaff.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleFileRepository extends JpaRepository<ArticleFiles, Long> {
    List<ArticleFiles> findArticleFilesById(Long articleId);

    @Modifying
    @Query("delete from ArticleFiles as f where f.article.id = ?1 and f.modifiedFilename in(?2)")
    void deleteArticleFilesByArticleIdAndFileName(Long id, List<String> removeImages);

}
