package com.example.gaff.article;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleCreateService {
    final ArticleRepository articleRepository;
}
