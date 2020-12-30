package com.example.gaff.image;

import com.example.gaff.api_user.ApiUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "imgs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {


    @Id
    @GeneratedValue
    private Long id;

    @Lob
    private byte[] image;

}
