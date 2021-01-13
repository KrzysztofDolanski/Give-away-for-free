package com.example.gaff.api_user;

import com.example.gaff.article.Article;
import com.example.gaff.article.ArticleDto;
import com.example.gaff.exceptions.ApiUserAlreadyExistsException;
import com.example.gaff.exceptions.NoUsernameException;
import com.example.gaff.image.UserFiles;
import com.example.gaff.image.UserFilesDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ApiUserController {


    private final ApiUserService apiUserService;
    private final ConfirmationTokenService confirmationTokenService;

    @GetMapping("/login")
    public String signUp() {
        return "login";
    }

    @GetMapping(value = "/register")
    public String users(Model model) {
        List<ApiUserDto> users = apiUserService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("user", new ApiUserDto());
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
        ApiUserDto userByUsername = apiUserService.getUserByUsername(apiUserDto.getUsername());

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
        ApiUserDto apiUser = apiUserService.getUserByUsername(username);
        model.addAttribute("apiUser", apiUser);

        String uriGoogle = apiUserService.createGoogleMapQuery(username);

        model.addAttribute("uriGoogle", uriGoogle);

        return "user-edit";
    }

    @GetMapping(value = "/edituser/{userId}")
    public String editUser(@PathVariable Long userId, Model model) {

        ApiUserDto apiUserDto = apiUserService.findById(userId);

        List<UserFilesDto> userFiles = apiUserService.findFilesByUserId(userId);
        List<ApiUserDto> users = apiUserService.getAllUsers();
        String fileName = userFiles.get(0).getModifiedFilename();

        model.addAttribute("users", users);
        model.addAttribute("user", apiUserDto);
        model.addAttribute("userFiles", userFiles);
        model.addAttribute("isAdd", false);
        model.addAttribute("fileName", fileName);

        return "view/user";
    }


    @PostMapping("/edituser/update")
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

    @GetMapping("user/articles")
    public String showAllArticlesOfLoggedUser(String username, Model model) {
        ApiUserDto userByUsername = null;
        try {
            userByUsername = apiUserService.getUserByUsername(username);
        } catch (NoUsernameException e) {
            e.getMessage();
        }
        assert userByUsername != null;
        List<ArticleDto> article = userByUsername.getArticle();
        model.addAttribute("articles", article);
        return "user-articles";
    }

}

