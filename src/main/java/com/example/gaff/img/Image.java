package com.example.gaff.img;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {


    @Id
    @GeneratedValue
    private Long id;

    @Lob
    private byte[] img;

    private Long userId;

}

