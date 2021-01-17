package com.example.gaff.img;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ImageMapping {

    public Image mapToImage(ImageDto imageDto){
        return Image.builder()
                .id(imageDto.getId())
                .img(imageDto.getImg())
                .article(imageDto.getArticle())
                .user(imageDto.getUser())
                .build();
    }

    public ImageDto mapToImageDto(Image image){
        return ImageDto.builder()
                .id(image.getId())
                .img(image.getImg())
                .article(image.getArticle())
                .user(image.getUser())
                .build();
    }

    public List<Image> mapToImageList(List<ImageDto> imageDtoList){
        return imageDtoList.stream().map(this::mapToImage).collect(Collectors.toList());
    }

    public List<ImageDto> mapToImageDtoList(List<Image> imageList){
        return imageList.stream().map(this::mapToImageDto).collect(Collectors.toList());
    }


    public Optional<ImageDto> mapToImageDtoOptional(Optional<Image> imageList){
        return imageList.map(this::mapToImageDto);
    }

    public Optional<Image> mapToImageOptional(Optional<ImageDto> imageDtoList){
        return imageDtoList.map(this::mapToImage);
    }
}
