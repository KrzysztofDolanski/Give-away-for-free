package com.example.gaff.api_user;

import com.example.gaff.exceptions.ApiUserAlreadyExistsException;
import com.example.gaff.image.UserFiles;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ApiUserController {


    private final ApiUserService apiUserService;
    private final ConfirmationTokenService confirmationTokenService;


//
//    @Value("${uploadDir}")
//    private String uploadFolder;


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
    public String save(@ModelAttribute ApiUserDto apiUserDto, RedirectAttributes redirectAttributes, Model model) throws MessagingException, IOException, ApiUserAlreadyExistsException {
        apiUserService.signUpUser(apiUserDto);
        ApiUser userByUsername = apiUserService.getUserByUsername(apiUserDto.getUsername());

        if (userByUsername != null) {
            redirectAttributes.addFlashAttribute("successmessage", "User successful register");
            return "redirect:/register";
        } else {
            model.addAttribute("errormessage", "User register failed");
            model.addAttribute("user", apiUserDto);
            return "user-edit";
        }
    }

    @GetMapping("/user")
    String userPage(String username, Model model) {
        ApiUser apiUser = apiUserService.getUserByUsername(username);
        model.addAttribute("apiUser", apiUser);
        return "user-edit";
    }

    @GetMapping(value = "/edituser/{userId}")
    public String editUser(@PathVariable Long userId, Model model){
        ApiUserDto apiUserDto = apiUserService.findById(userId);

        List<UserFiles> userFiles = apiUserService.findFilesByUserId(userId);
        List<ApiUser> users = apiUserService.getAllUsers();
        String fileName = userFiles.get(0).getModifiedFilename();

        model.addAttribute("users", users);
        model.addAttribute("user", apiUserDto);
        model.addAttribute("userFiles", userFiles);
        model.addAttribute("isAdd", false);
        model.addAttribute("fileName", fileName);

        return "view/user";
    }


    @PostMapping("/update")
    public String update(@ModelAttribute ApiUserDto apiUserDto, RedirectAttributes redirectAttributes, Model model) throws MessagingException, IOException, ApiUserAlreadyExistsException {
        ApiUserDto apiUserDto1 = apiUserService.update(apiUserDto);

        if (apiUserDto1 != null) {
            redirectAttributes.addFlashAttribute("successmessage", "User updated with success");
            return "redirect:/register";
        } else {
            model.addAttribute("errormessage", "User update failed");
            model.addAttribute("user", apiUserDto);
            return "view/user";
        }
    }

}
