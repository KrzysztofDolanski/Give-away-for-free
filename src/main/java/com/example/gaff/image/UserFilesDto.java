package com.example.gaff.image;

import com.example.gaff.api_user.ApiUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFilesDto {

    private Long id;
    private String fileName;
    private String modifiedFilename;
    private String fileExtension;

    private ApiUser user;
}
