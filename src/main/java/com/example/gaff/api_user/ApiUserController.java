package com.example.gaff.api_user;

import com.example.gaff.exceptions.ApiUserAlreadyExistsException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class ApiUserController {

    private final ApiUserService apiUserService;
    private final ConfirmationTokenService confirmationTokenService;
    private final ApiUserMapping apiUserMapping;


    @GetMapping("/login")
    String signUp(){
        return "login";
    }

    @GetMapping("/register")
    String signUpPage( ApiUserDto apiUserDto, Model model) {
        model.addAttribute("apiUserDto", apiUserDto);
        return "register";
    }


    @GetMapping("register/confirm")
    String confirmMail(String token){
        Optional<ConfirmationToken> confirmationTokenByToken = confirmationTokenService.findConfirmationTokenByToken(token);
        confirmationTokenByToken.ifPresent(apiUserService::confirmUser);
        return "redirect:/login";
    }

    @GetMapping("/home")
    String home(){
        return "home";
    }


    /////////////////////////////////////
    @GetMapping("/user")
    String userPage(String username, Model model) {
        ApiUser apiUser = apiUserService.getUserByUsername(username);
        model.addAttribute("apiUser", apiUser);
        return "user-edit";
    }
//
//    @GetMapping("/user/article")
//    String showUserArticles(String username){
//        apiUserService.showUserArticles(username);
//        return "redirect:/home";
//    }

    @RequestMapping(value = "apiUser", method = RequestMethod.GET)
    public String user(String username, Model model) {
        model.addAttribute("apiUser", apiUserService.getUserByUsername(username));
        return "user-edit";
    }


    @PostMapping("/register")
    String signUp(ApiUserDto apiUserDto) throws MessagingException, IOException, ApiUserAlreadyExistsException {
        apiUserService.signUpUser(apiUserDto);
        return "redirect:/login";
    }
}
