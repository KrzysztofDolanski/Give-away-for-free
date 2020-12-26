package com.example.gaff.api_user;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.mail.MessagingException;
import java.sql.Blob;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class ApiUserController {

    private final ApiUserService apiUserService;
    private final ConfirmationTokenService confirmationTokenService;


    @GetMapping("/login")
    String signUp(){
        return "login";
    }

    @GetMapping("/register")
    String signUpPage( ApiUserDto apiUserDto, Model model) {
        model.addAttribute("apiUserDto", apiUserDto);
        return "register";
    }


    @PostMapping("/register")
    String signUp(ApiUserDto apiUserDto) throws MessagingException {
        apiUserService.signUpUser(apiUserDto);
        return "redirect:/login";
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

}
