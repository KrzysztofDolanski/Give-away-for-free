package com.example.gaff.img;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepositoty extends JpaRepository<Image, Long> {
}
