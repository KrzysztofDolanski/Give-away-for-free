package com.example.gaff.article;

import com.example.gaff.api_user.ApiUserMapping;
import com.example.gaff.api_user.ApiUserService;
import com.example.gaff.exceptions.ApiUserAlreadyExistsException;
import com.example.gaff.image.ArticleFilesDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
public class ArticleController {
    private final ArticleService articleService;
    private final ArticleMapper articleMapper;

    private final ApiUserService apiUserService;
    private final ApiUserMapping apiUserMapping;

//    @GetMapping("/article")
//    List<Article> getAllArticle() {
//        return articleFetchService.getAllArticle();
//    }
//
//    @GetMapping("/article/{title}")
//    ArticleDto findArticleByTitle(String title) {
//        Article article = articleFetchService.findArticleByTitle(title);
//        return articleMapper.mapToArticleDto(article);
//    }
//
//    @GetMapping("/article/{id}")
//    ArticleDto findByArticleById(@PathVariable Long id ){
//          Article article = articleFetchService.findArticleById(id);
//        return articleMapper.mapToArticleDto(article);
//    }
//
//    @PostMapping
//    void addArticle() {
//    }


    @GetMapping(value = "/save/article")
    public String articles(Model model){
        List<ArticleDto> allArticles = articleService.getAllArticle();

        model.addAttribute("articles", allArticles);
        model.addAttribute("article", new ArticleDto());
        model.addAttribute("articleFiles", new ArrayList<ArticleFilesDto>());
        model.addAttribute("isAdd", true);
        return "article/article";
    }


    @PostMapping("save/saveA")
    public String save(@ModelAttribute ArticleDto articleDto, RedirectAttributes redirectAttributes, Model model, HttpServletRequest request) throws MessagingException, IOException, ApiUserAlreadyExistsException {

        articleService.saveArticle(articleDto, request);
        ArticleDto articleByTitle = articleService.findArticleByTitle(articleDto.getTitle());

        if (articleByTitle != null) {
            redirectAttributes.addFlashAttribute("successmessage", "Article successful saved");
            return "redirect:/save/article";
        } else {
            model.addAttribute("errormessage", "Article saving failed");
            model.addAttribute("article", articleDto);
            return "save/article";
        }
    }


    @GetMapping("/article")
    String articlePage(Long id, Model model) {
        ArticleDto articleById = articleService.findArticleById(id);
        model.addAttribute("article", articleById);
        String username = articleById.getUser().getUsername();

        String uriGoogle = apiUserService.createGoogleMapQuery(username);

        model.addAttribute("uriGoogle", uriGoogle);

        return "article/article-edit";
    }

    @PostMapping("/updateA")
    public String update(@ModelAttribute ArticleDto articleDto, RedirectAttributes redirectAttributes, Model model) throws MessagingException, IOException, ApiUserAlreadyExistsException {
//        ApiUserDto apiUserDto1 = apiUserService.update(articleDto);
//
//        if (apiUserDto1 != null) {
//            redirectAttributes.addFlashAttribute("successmessage", "User updated with success");
//            return "redirect:/register";
//        } else {
//            model.addAttribute("errormessage", "User update failed");
            model.addAttribute("article", articleDto);
            return "view/user";
//        }
    }

}
