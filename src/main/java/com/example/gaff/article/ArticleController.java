package com.example.gaff.article;

import com.example.gaff.api_user.ApiUserDto;
import com.example.gaff.api_user.ApiUserService;
import com.example.gaff.image.ArticleFiles;
import com.example.gaff.exceptions.ApiUserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
public class ArticleController {
    private final ArticleFetchService articleFetchService;
    private final ArticleMapper articleMapper;
    private final ArticleFetchService articleService;
    private final ApiUserService apiUserService;

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
//        Article article = articleFetchService.findArticleById(id);
//        return articleMapper.mapToArticleDto(article);
//    }
//
//    @PostMapping
//    void addArticle() {
//    }


    @GetMapping(value = "/save/article")
    public String articles(Model model){
        List<Article> allArticles = articleFetchService.getAllArticle();
        model.addAttribute("articles", allArticles);
        model.addAttribute("article", new Article());
        model.addAttribute("articleFiles", new ArrayList<ArticleFiles>());
        model.addAttribute("isAdd", true);
        return "article/article";
    }


    @PostMapping("/save/saveA")
    public String save(@ModelAttribute ArticleDto articleDto, RedirectAttributes redirectAttributes, Model model) throws MessagingException, IOException, ApiUserAlreadyExistsException {
        articleService.saveArticle(articleDto);
        Article articleByTitle = articleFetchService.findArticleByTitle(articleDto.getTitle());

        if (articleByTitle != null) {
            redirectAttributes.addFlashAttribute("successmessage", "Article successful saved");
            return "redirect:/article/article";
        } else {
            model.addAttribute("errormessage", "Article saving failed");
            model.addAttribute("article", articleDto);
            return "article/article-edit";
        }
    }

    @GetMapping("/article")
    String articlePage(Long id, Model model) {
        Article articleById = articleFetchService.findArticleById(id);
        model.addAttribute("article", articleById);
//        ApiUser user = articleById.getUser();
//        String username = user.getUsername();
//
//        String uriGoogle = apiUserService.createGoogleMapQuery(username);
//
//        model.addAttribute("uriGoogle", uriGoogle);

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
