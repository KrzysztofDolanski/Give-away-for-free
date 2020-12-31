package com.example.gaff.api_user;

import com.example.gaff.exceptions.ApiUserAlreadyExistsException;
import com.example.gaff.image.UserFiles;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
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
    public String signUp() {
        return "login";
    }

    @GetMapping(value = "/register")
    public String users(Model model){
        List<ApiUser> users = apiUserService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("user", new ApiUser());
        model.addAttribute("userFiles", new ArrayList<UserFiles>());
        model.addAttribute("isAdd", true);
        return "register";
    }


    @GetMapping("register/confirm")
    String confirmMail(String token) {
        Optional<ConfirmationToken> confirmationTokenByToken = confirmationTokenService.findConfirmationTokenByToken(token);
        confirmationTokenByToken.ifPresent(apiUserService::confirmUser);
        return "redirect:/login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }


    @PostMapping("/save")
    public String save(ApiUserDto apiUserDto, RedirectAttributes redirectAttributes, Model model) throws MessagingException, IOException, ApiUserAlreadyExistsException {
        apiUserService.signUpUser(apiUserDto);

        if (apiUserDto != null) {
            redirectAttributes.addFlashAttribute("successmessage", "User successful register");
            return "redirect:/register";
        } else {
            model.addAttribute("errormessage", "User register faild");
            model.addAttribute("user", apiUserDto);

            return "redirect:/login";
        }
    }
}
