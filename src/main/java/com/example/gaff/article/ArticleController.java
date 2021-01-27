package com.example.gaff.article;

import com.example.gaff.exceptions.ApiUserAlreadyExistsException;
import com.example.gaff.img.ImageForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        model.addAttribute("artFresh", articleDto);
        model.addAttribute("art2", articleDto1);
        model.addAttribute("art3", articleDto2);
        model.addAttribute("art4", articleDto3);
        model.addAttribute("art5", articleDto4);
        model.addAttribute("isAdd", true);
        return "starting";
    }

}
