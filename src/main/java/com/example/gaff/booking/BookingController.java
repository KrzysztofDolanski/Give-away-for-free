package com.example.gaff.booking;

import com.example.gaff.api_user.ApiUserService;
import com.example.gaff.article.ArticleFetchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
public class BookingController {

    final ArticleFetchService articleFetchService;
    final ApiUserService apiUserService;
    final BookingService bookingService;

    @GetMapping("/booking/new")
    public String newBooking(Model model) {
        model.addAttribute("booking", new Booking());
        return "newBooking";
    }

    @PostMapping("/booking/new")
    public String saveBooking(@ModelAttribute("booking") Booking booking, BindingResult bindingResult,
                              @RequestParam("apiUser") long userId,
                                //@RequestParam("collectionDateTime") LocalDateTime  collectionDateTime
                              @RequestParam("article") long articleId, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("booking", new Booking());
            model.addAttribute("article", articleFetchService.getAllArticle());
            model.addAttribute("apiUser", apiUserService.getAllUsers());
            return "newBooking";
        }
        apiUserService.findById(userId);
        articleFetchService.findArticleById(articleId);

        // daty rezerwacji, odbipru, czas odbioru/

        return "booking";
    }

    @GetMapping("/bookingList")
    public List<Booking> BookingList(){
        return bookingService.getAllBooking();
    }

    @DeleteMapping("/booking/delete")
    public void deleteBookingById(@PathVariable Long bookingId) {

        bookingService.findBookingById(bookingId);
    }


}
