package com.example.gaff.api_user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class ApiUserController {

    private final ApiUserService apiUserService;
    private final ConfirmationTokenService confirmationTokenService;


    @GetMapping("/sign-in")
    String signUp(){
        return "sign-in";
    }

    @GetMapping("/sign-up")
    String signUpPage(ApiUserDto apiUserDto){

        return "sign-up";
    }

    @PostMapping("/sign-up")
    String signUp(ApiUserDto apiUserDto){
        apiUserService.signUpUser(apiUserDto);
        return "redirect:/sign-in";
    }

    @GetMapping("sign-up/confirm")
    String confirmMail(String token){
        Optional<ConfirmationToken> confirmationTokenByToken = confirmationTokenService.findConfirmationTokenByToken(token);
        confirmationTokenByToken.ifPresent(apiUserService::confirmUser);
        return "redirect:/sign-in";
    }



}
