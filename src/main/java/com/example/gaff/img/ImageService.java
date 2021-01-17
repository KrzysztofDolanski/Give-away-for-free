package com.example.gaff.img;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepositoty imageRepositoty;
    private final ImageMapping imageMapping;

    public Image save(Image image) {
        return imageRepositoty.save(image);
    }

    public Optional<Image> findById(long l) {
        return imageRepositoty.findById(l);
    }

    public List<ImageDto> findFilesByApiUserId(Long id) {
        return imageMapping.mapToImageDtoList(imageRepositoty.findImagesByUserId(id));
    }

    public ImageDto findByCreationDate(LocalDate savedDate) {
        return imageMapping.mapToImageDto(imageRepositoty.findByCreatedDate(savedDate));
    }

    public List<ImageDto> findAll() {
        return imageMapping.mapToImageDtoList(imageRepositoty.findAll());
    }
}
