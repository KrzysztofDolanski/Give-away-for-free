package com.example.gaff.img;

import com.example.gaff.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepositoty extends JpaRepository<Image, Long> {

    List<Image> findImagesByUserId(Long id);

    Image findByCreatedDate(LocalDate localDate);
}
