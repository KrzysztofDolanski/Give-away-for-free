package com.example.gaff.article;

import com.example.gaff.api_user.ApiUserDto;
import com.example.gaff.api_user.ApiUserService;
import com.example.gaff.booking.BookingDto;
import com.example.gaff.exceptions.ApiUserAlreadyExistsException;
import com.example.gaff.img.ImageForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Log4j2
public class ArticleController {
    private final ArticleService articleService;
    private final ImageForm multipartFile;
    private final ApiUserService apiUserService;


    @GetMapping(value = "/save/article")
    public String articles(Model model) {
        List<ArticleDto> allArticles = articleService.getAllArticle();
        model.addAttribute("articles", allArticles);
        model.addAttribute("article", new ArticleDto());
        model.addAttribute("image", multipartFile);
        model.addAttribute("isAdd", true);
        return "article/article";
    }


    @PostMapping("save/saveA")
    public String save(@ModelAttribute ArticleDto articleDto
            , RedirectAttributes redirectAttributes, Model model
            , HttpServletRequest request
            , @RequestParam("image") MultipartFile multipartFile)
            throws IOException, ApiUserAlreadyExistsException {

        articleService.saveArticle(articleDto, request, multipartFile.getBytes());
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
        return "article/article-edit";
    }

    @PostMapping("/updateA")
    public String update(@ModelAttribute ArticleDto articleDto, Model model) throws ApiUserAlreadyExistsException {
        model.addAttribute("article", articleDto);
        return "view/user";
    }

    @GetMapping(value = "")
    public String startingPage(Model model) {
        List<ArticleDto> allArticles = articleService.getAllAvailableArticles();
        ArticleDto articleDto = allArticles.get(allArticles.size() - 1);
        ArticleDto articleDto1 = allArticles.get(allArticles.size() - 2);
        ArticleDto articleDto2 = allArticles.get(allArticles.size() - 3);
        ArticleDto articleDto3 = allArticles.get(allArticles.size() - 4);
        ArticleDto articleDto4 = allArticles.get(allArticles.size() - 5);
        Long userId = articleDto.getUserId();
        Long userId1 = articleDto1.getUserId();
        Long userId2 = articleDto2.getUserId();
        Long userId3 = articleDto3.getUserId();
        Long userId4 = articleDto4.getUserId();
        ApiUserDto byId = apiUserService.findById(userId);
        ApiUserDto byId1 = apiUserService.findById(userId1);
        ApiUserDto byId2 = apiUserService.findById(userId2);
        ApiUserDto byId3 = apiUserService.findById(userId3);
        ApiUserDto byId4 = apiUserService.findById(userId4);
        model.addAttribute("artFresh", articleDto);
        model.addAttribute("art2", articleDto1);
        model.addAttribute("art3", articleDto2);
        model.addAttribute("art4", articleDto3);
        model.addAttribute("art5", articleDto4);
        model.addAttribute("userFresh", byId);
        model.addAttribute("user2", byId1);
        model.addAttribute("user3", byId2);
        model.addAttribute("user4", byId3);
        model.addAttribute("user5", byId4);
        String uriGoogle = apiUserService.createGoogleMapQuery(byId.getUsername());
        model.addAttribute("uriGoogleFresh", uriGoogle);
        String uriGoogle1 = apiUserService.createGoogleMapQuery(byId1.getUsername());
        model.addAttribute("uriGoogle2", uriGoogle);
        String uriGoogle2 = apiUserService.createGoogleMapQuery(byId2.getUsername());
        model.addAttribute("uriGoogle3", uriGoogle);
        String uriGoogle3 = apiUserService.createGoogleMapQuery(byId3.getUsername());
        model.addAttribute("uriGoogle4", uriGoogle);
        String uriGoogle4 = apiUserService.createGoogleMapQuery(byId4.getUsername());
        model.addAttribute("uriGoogle5", uriGoogle);

        model.addAttribute("isAdd", true);
        return "starting";
    }

    @GetMapping("/myart")
    public String showBookingPage(Model model, HttpServletRequest request) {
        List<ArticleDto> allUserArticles = articleService.getAllUserArtiles(request);

        model.addAttribute("article", allUserArticles);
        model.addAttribute("isAdd", false);
        return "booking/user_articles";
    }


    @GetMapping("/delete/article/{id}")
    public String save(@PathVariable Long id, RedirectAttributes redirectAttributes, HttpServletRequest request, Model model)
            throws ApiUserAlreadyExistsException {
        articleService.deleteById(id);
        return "redirect:/myart";
    }

}
