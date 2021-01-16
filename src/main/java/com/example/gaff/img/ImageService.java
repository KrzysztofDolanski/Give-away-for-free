package com.example.gaff.img;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepositoty imageRepositoty;

    public Image save(Image image) {
        return imageRepositoty.save(image);
    }

    public Optional<Image> findById(long l) {
        return imageRepositoty.findById(l);
    }
}
