package com.example.gaff.article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {


    @Modifying
    @Query("select f from Article as f where f.isAvailable = true")
    List<Article> getAllAvailableArticles();



    Article findByTitle(String title);
}
