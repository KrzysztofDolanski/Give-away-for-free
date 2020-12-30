package com.example.gaff.image;

import com.example.gaff.api_user.ApiUser;
import com.example.gaff.api_user.ApiUserDto;
import com.example.gaff.api_user.ApiUserMapping;
import com.example.gaff.api_user.ApiUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/upload")
@RequiredArgsConstructor
public class ImageController {

    private final ImageRepository imageRepository;


//    @GetMapping("/img")
//    public String showUploadForm(ModelMap modelMap) {
//        modelMap.addAttribute("imageForm", new ImageForm());
//        imageRepository.findById(1L).ifPresent(imageEntity -> modelMap.addAttribute("myimg", new String(Base64.getEncoder().encode(
//                imageEntity.getImage()), StandardCharsets.UTF_8)));
//        return "upload";
//    }


    @PostMapping
    public String handleImage(@ModelAttribute("imageForm") ImageForm imageForm) throws IOException {
        Image img = imageRepository.save(new Image(null, imageForm.getImage().getBytes()));
        return "upload";
    }
}
