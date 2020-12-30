package com.example.gaff.api_user;

import com.example.gaff.exceptions.ApiUserAlreadyExistsException;
import com.example.gaff.image.Image;
import com.example.gaff.image.ImageForm;
import com.example.gaff.image.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ApiUserController {


    private final ApiUserService apiUserService;

    private final ConfirmationTokenService confirmationTokenService;
    private final ApiUserRepository apiUserRepository;

    @Value("${uploadDir}")
    private String uploadFolder;


    @GetMapping("/login")
    String signUp() {
        return "login";
    }

    @GetMapping("/register")
    String signUpPage(ApiUserDto apiUserDto, Model model) {
        model.addAttribute("apiUserDto", apiUserDto);
        return "register";
    }


    @GetMapping("register/confirm")
    String confirmMail(String token) {
        Optional<ConfirmationToken> confirmationTokenByToken = confirmationTokenService.findConfirmationTokenByToken(token);
        confirmationTokenByToken.ifPresent(apiUserService::confirmUser);
        return "redirect:/login";
    }

    @GetMapping("/home")
    String home() {
        return "home";
    }

    @GetMapping("/user")
    String userPage(String username, Model model, ModelMap modelMap) throws IOException {
        ApiUser apiUser = apiUserService.getUserByUsername(username);
        model.addAttribute("apiUser", apiUser);
        modelMap.addAttribute("logotype", new ImageForm());
        apiUserRepository.findById(1L).ifPresent(apiUserEntity -> modelMap.addAttribute("myimg", new String(Base64.getEncoder().encode(
                apiUserEntity.getLogotype()), StandardCharsets.UTF_8)));
        return "user-edit";
    }

    @RequestMapping(value = "apiUser", method = RequestMethod.GET)
    public String user(String username, Model model, ModelMap modelMap) {
        model.addAttribute("apiUser", apiUserService.getUserByUsername(username));

        modelMap.addAttribute("logotype", new ImageForm());
        apiUserRepository.findById(1L).ifPresent(apiUserEntity -> modelMap.addAttribute("myimg", new String(Base64.getEncoder().encode(
                apiUserEntity.getLogotype()), StandardCharsets.UTF_8)));

        return "user-edit";
    }


    @PostMapping("/register")
    String signUp(ApiUserDto apiUserDto) throws MessagingException, IOException, ApiUserAlreadyExistsException {
        apiUserService.signUpUser(apiUserDto);
        return "redirect:/login";
    }

}
