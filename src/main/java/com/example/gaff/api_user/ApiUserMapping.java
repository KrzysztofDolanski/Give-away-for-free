package com.example.gaff.api_user;

import org.springframework.stereotype.Component;

@Component
public class ApiUserMapping {



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
                .article(apiUserDto.getArticle())
                .booking(apiUserDto.getBooking())
                .confirmationToken(apiUserDto.getConfirmationToken())
                .build();
    }

    public ApiUserDto mapToApiUserDto(ApiUser apiUser) {
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
                .article(apiUser.getArticle())
                .booking(apiUser.getBooking())
                .confirmationToken(apiUser.getConfirmationToken())
                .build();
    }

}
