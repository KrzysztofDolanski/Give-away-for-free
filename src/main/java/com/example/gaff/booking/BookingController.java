package com.example.gaff.booking;

import com.example.gaff.api_user.ApiUserDto;
import com.example.gaff.api_user.ApiUserService;
import com.example.gaff.article.ArticleDto;
import com.example.gaff.article.ArticleService;
import com.example.gaff.exceptions.ApiUserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor

public class BookingController {

    private final ArticleService articleService;
    private final ApiUserService apiUserService;
    private final BookingService bookingService;

    @GetMapping("/bookings")
    public String showNewBookingPage(Model model, HttpServletRequest httpServletRequest) {
        List<ApiUserDto> users = apiUserService.getAllUsers();
        List<ArticleDto> allAvailableArticles = articleService.getAllAvailableArticles();
//        List<String> collect = articleService.getImagesOfAllAvailableArticles();

//        model.addAttribute("images", collect);
        model.addAttribute("users", users);
        model.addAttribute("booking", new BookingDto());
        model.addAttribute("article", allAvailableArticles);
        model.addAttribute("isAdd", false);
        return "booking/booking";
    }

    @GetMapping("/order")
    public String showBookingPage(Model model, HttpServletRequest request) {
        List<ApiUserDto> users = apiUserService.getAllUsers();
        List<ArticleDto> allAvailableArticlesExceptLoggedUser
                = articleService.getAllAvailableArticlesExceptLoggedUser(request);

//        List<String> collect = allAvailableArticlesExceptLoggedUser
//                .stream()
//                .map(articles ->
//                        new String(Base64.getEncoder().encode(articles.getImg())))
//                .collect(Collectors.toList());


//        model.addAttribute("users", users);
//        model.addAttribute("booking", new BookingDto());
//        model.addAttribute("images", collect);
        model.addAttribute("article", allAvailableArticlesExceptLoggedUser);
        model.addAttribute("isAdd", false);
        return "booking/order";
    }

    @GetMapping("/save/booking/{id}")
    public String save(@PathVariable Long id, RedirectAttributes redirectAttributes, HttpServletRequest request, Model model)
            throws ApiUserAlreadyExistsException {

        //todo zamienić articleId na Article oraz wyciągnąć sellerId
        //todo zrobić maksymalnie jedno bookowanie

        BookingDto bookingDto = new BookingDto();
        bookingDto.setArticleId(id);
        bookingService.saveBooking(bookingDto, request);
        BookingDto bookingDto1 = bookingService.findBookingByArticleId(id);

        if (bookingDto1 != null) {
            redirectAttributes.addFlashAttribute("successmessage", "Booking successful saved");
            return "redirect:/bookings";
        } else {
            model.addAttribute("errormessage", "Booking saving failed");
            model.addAttribute("booking", bookingDto);
            return "save/booking";
        }
    }
}

