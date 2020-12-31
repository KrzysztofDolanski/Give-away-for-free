package com.example.gaff.image;

import com.example.gaff.api_user.ApiUser;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table (name = "user_files")
public class UserFiles {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fileName;
    private String modifiedFilename;
    private String fileExtension;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private ApiUser user;
}
