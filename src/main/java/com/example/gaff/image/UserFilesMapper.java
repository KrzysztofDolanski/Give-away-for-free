package com.example.gaff.image;

import org.springframework.stereotype.Component;

@Component
public class UserFilesMapper {

    public UserFiles mapToUserFiles(UserFilesDto userFilesDto) {
        return UserFiles.builder()
                .fileName(userFilesDto.getFileName())
                .fileExtension(userFilesDto.getFileExtension())
                .modifiedFilename(userFilesDto.getModifiedFilename())
                .user(userFilesDto.getUser())
                .build();
    }

    public UserFilesDto mapToUserFilesDto(UserFiles userFiles) {
        return UserFilesDto.builder()
                .fileName(userFiles.getFileName())
                .fileExtension(userFiles.getFileExtension())
                .modifiedFilename(userFiles.getModifiedFilename())
                .user(userFiles.getUser())
                .build();
    }
}
