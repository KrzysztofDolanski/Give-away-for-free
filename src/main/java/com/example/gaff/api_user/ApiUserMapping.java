package com.example.gaff.api_user;

import org.springframework.stereotype.Component;

@Component
public class ApiUserMapping {


    public ApiUser mapToApiUser(ApiUserDto apiUserDto) {
        return ApiUser.builder()
                .username(apiUserDto.getUsername())
                .password(apiUserDto.getPassword())
                .city(apiUserDto.getCity())
                .street(apiUserDto.getStreet())
                .streetNo(apiUserDto.getStreetNo())
                .zipCode(apiUserDto.getZipCode())
                .region(apiUserDto.getRegion())
                .email(apiUserDto.getEmail())
                .logotype(apiUserDto.getLogotype())
                .article(apiUserDto.getArticle())
                .booking(apiUserDto.getBooking())
                .dateOfRegistration(apiUserDto.getDateOfRegistration())
                .confirmationToken(apiUserDto.getConfirmationToken())
                .build();
    }

    public ApiUserDto mapToApiUserDto(ApiUser apiUser) {
        return ApiUserDto.builder()
                .username(apiUser.getUsername())
                .password(apiUser.getPassword())
                .city(apiUser.getCity())
                .street(apiUser.getStreet())
                .streetNo(apiUser.getStreetNo())
                .zipCode(apiUser.getZipCode())
                .region(apiUser.getRegion())
                .email(apiUser.getEmail())
                .logotype(apiUser.getLogotype())
                .article(apiUser.getArticle())
                .booking(apiUser.getBooking())
                .dateOfRegistration(apiUser.getDateOfRegistration())
                .confirmationToken(apiUser.getConfirmationToken())
                .build();
    }

}
