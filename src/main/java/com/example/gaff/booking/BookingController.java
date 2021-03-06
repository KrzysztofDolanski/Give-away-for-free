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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor

public class BookingController {

    private final ArticleService articleService;
    private final ApiUserService apiUserService;
    private final BookingService bookingService;


    @GetMapping("/order")
    public String showBookingPage(Model model, HttpServletRequest request) {
        List<ApiUserDto> users = apiUserService.getAllUsers();
        List<ArticleDto> allAvailableArticlesExceptLoggedUser
                = articleService.getAllAvailableArticlesExceptLoggedUser(request);

        model.addAttribute("article", allAvailableArticlesExceptLoggedUser);

//        List<String> distanceMapQuery = apiUserService.createDistanceMapQuery(allAvailableArticlesExceptLoggedUser, request);

//        model.addAttribute("distance", distanceMapQuery);

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
            return "redirect:/order";
        } else {
            model.addAttribute("errormessage", "Booking saving failed");
            model.addAttribute("booking", bookingDto);
            return "/order";
        }
    }
}

