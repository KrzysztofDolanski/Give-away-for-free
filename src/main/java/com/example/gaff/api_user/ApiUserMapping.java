package com.example.gaff.api_user;

import com.example.gaff.article.ArticleMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ApiUserMapping {

    public final ArticleMapper articleMapper;

    public ApiUser mapToApiUser(ApiUserDto apiUserDto) {
        return ApiUser.builder()
                .username(apiUserDto.getUsername())
                .password(apiUserDto.getPassword())
                .email(apiUserDto.getEmail())
                .region(apiUserDto.getRegion())
                .city(apiUserDto.getCity())
                .street(apiUserDto.getStreet())
                .streetNo(apiUserDto.getStreetNo())
                .zipCode(apiUserDto.getZipCode())
                .dateOfRegistration(apiUserDto.getDateOfRegistration())
                .files(apiUserDto.getFiles())
                .removeImages(apiUserDto.getRemoveImages())
                .booking(apiUserDto.getBooking())
                .confirmationToken(apiUserDto.getConfirmationToken())
                .build();
    }

    public ApiUserDto mapToApiUserDto(ApiUser apiUser) {
        return ApiUserDto.builder()
                .id(apiUser.getId())
                .username(apiUser.getUsername())
                .password(apiUser.getPassword())
                .email(apiUser.getEmail())
                .region(apiUser.getRegion())
                .city(apiUser.getCity())
                .street(apiUser.getStreet())
                .streetNo(apiUser.getStreetNo())
                .zipCode(apiUser.getZipCode())
                .dateOfRegistration(apiUser.getDateOfRegistration())
                .files(apiUser.getFiles())
                .removeImages(apiUser.getRemoveImages())
                .booking(apiUser.getBooking())
                .confirmationToken(apiUser.getConfirmationToken())
                .build();
    }


    public ApiUser mapToApiUserWithArticles(ApiUserDto apiUserDto) {
        return ApiUser.builder()
                .username(apiUserDto.getUsername())
                .password(apiUserDto.getPassword())
                .email(apiUserDto.getEmail())
                .region(apiUserDto.getRegion())
                .city(apiUserDto.getCity())
                .street(apiUserDto.getStreet())
                .streetNo(apiUserDto.getStreetNo())
                .zipCode(apiUserDto.getZipCode())
                .dateOfRegistration(apiUserDto.getDateOfRegistration())
                .files(apiUserDto.getFiles())
                .removeImages(apiUserDto.getRemoveImages())
                .article(articleMapper.mapToArticleList(apiUserDto.getArticle()))
                .booking(apiUserDto.getBooking())
                .confirmationToken(apiUserDto.getConfirmationToken())
                .build();
    }

    public ApiUserDto mapToApiUserDtoWithArticles(ApiUser apiUser) {
        return ApiUserDto.builder()
                .username(apiUser.getUsername())
                .password(apiUser.getPassword())
                .email(apiUser.getEmail())
                .region(apiUser.getRegion())
                .city(apiUser.getCity())
                .street(apiUser.getStreet())
                .streetNo(apiUser.getStreetNo())
                .zipCode(apiUser.getZipCode())
                .dateOfRegistration(apiUser.getDateOfRegistration())
                .files(apiUser.getFiles())
                .removeImages(apiUser.getRemoveImages())
                .article(articleMapper.mapToArticleDtoList(apiUser.getArticle()))
                .booking(apiUser.getBooking())
                .confirmationToken(apiUser.getConfirmationToken())
                .build();
    }

    public List<ApiUserDto> mapToApiUserDtoList(List<ApiUser> apiUserList) {
        return apiUserList.stream().map(this::mapToApiUserDto).collect(Collectors.toList());
    }

}
