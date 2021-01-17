package com.example.gaff.img;

import com.example.gaff.api_user.ApiUserDto;
import com.example.gaff.api_user.ApiUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Controller

@RequiredArgsConstructor
public class UploadController {


    private final ImageService imageService;
    private final ApiUserService apiUserService;

    @GetMapping
    public String showUploadForm(ModelMap modelMap) {

        ImageForm imageForm = new ImageForm();

        modelMap.addAttribute("imageForm", imageForm);


//        imageService.findById(1l).ifPresent(imageEntity ->
//                modelMap.addAttribute("myimg", new String(Base64.getEncoder().encode(
//                imageEntity.getImg()), StandardCharsets.UTF_8)));
        return "register";
    }



    @PostMapping
    public String handleImageActiveUser(@ModelAttribute("imageForm") ImageForm imageForm,
                                        @AuthenticationPrincipal Authentication authentication,
                                        ModelMap modelMap) throws IOException {
        String name = authentication.getName();
        ApiUserDto userByUsername = apiUserService.getUserByUsername(name);
        Long id = userByUsername.getId();
        LocalDate localDate = LocalDate.now();
        LocalDate savedDate = localDate;
        Image img = imageService.save(new Image(null, imageForm.getImage().getBytes(), localDate, null, null));
        List<ImageDto> imageDto = imageService.findAll();
        modelMap.addAttribute("image", imageDto);
        return "register";
    }
}

