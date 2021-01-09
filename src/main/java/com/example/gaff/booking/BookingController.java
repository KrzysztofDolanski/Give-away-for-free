package com.example.gaff.booking;

import com.example.gaff.api_user.ApiUserService;
import com.example.gaff.article.ArticleFetchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
public class BookingController {

    final ArticleFetchService articleFetchService;
    final ApiUserService apiUserService;
    final BookingServiceImpl bookingService;

    @GetMapping("/booking/new")
    public String showNewBookingPage(Model model) {
        model.addAttribute("booking", new Booking());
        model.addAttribute("article", articleFetchService.getAllArticle());
        model.addAttribute("apiUser", apiUserService.getAllUsers());
        return "newBooking";
    }

    @PostMapping("/booking/new")
    public String saveBooking(@ModelAttribute("booking") Booking booking, BindingResult bindingResult,
//                              @RequestParam("apiUser") long userId,
//                              @RequestParam("article") long articleId,
                              @RequestParam("collectionDate") LocalDateTime collectionDT, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("booking", new Booking());
            model.addAttribute("article", articleFetchService.getAllArticle());
            model.addAttribute("apiUser", apiUserService.getAllUsers());
            return "newBooking";
        }
        booking.setArticle(articleFetchService.getAllArticle().get(0));
        booking.setApiUsers(apiUserService.getAllUsers());
        booking.setCollectionDateTime(collectionDT);
        bookingService.saveBooking(booking);

        model.addAttribute("bookings", bookingService.getAllBookingPaged(0));
        model.addAttribute("currentPage", 0);
        return "bookings";

    }

    @GetMapping("/booking/delete")
    public String deleteBooking(@PathParam("bookingId") long bookingId, Model model) {
        bookingService.deleteBookingById(bookingId);
        model.addAttribute("bookings", bookingService.getAllBookingPaged(0));
        model.addAttribute("currentPage", 0);
        return "bookings";

    }

    @GetMapping("/bookings")
    public String showBookingList(@RequestParam(defaultValue = "0") int pageNo, Model model) {
        model.addAttribute("bookings", bookingService.getAllBookingPaged(pageNo));
        model.addAttribute("currentPage", pageNo);
        return "bookings";

    }

//    @GetMapping("booking/search")
//    public String showSearchBooking(Model model) {
//        model.addAttribute("apiUsers", apiUserService.getAllUsers());
//        model.addAttribute("bookings", null);
//        return "searchBookings";
//
//    }

    @PostMapping("/booking/search")
    public String searchBooking (@RequestParam("collectionDateTime") String collectionDT, Model model) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate collectionTime = LocalDate.parse(collectionDT, dtf);

        List<Booking> bookings = bookingService.getAllBookingsByApiUserAndCollectionDateTime(collectionTime);
        if (bookings.isEmpty()) {
            model.addAttribute("notFound");
        }else {
            model.addAttribute("bookings", bookings);
        }

        model.addAttribute("bookings", apiUserService.getAllUsers());
        return "searchBooking";
    }


//
//    @GetMapping("/bookingList")
//    public List<Booking> BookingList(){
//        return bookingService.getAllBooking();
//    }
//
//    @DeleteMapping("/booking/delete")
//    public void deleteBookingById(@PathVariable Long bookingId) {
//
//        bookingService.findBookingById(bookingId);
//    }

    //    @GetMapping("/booking/new")
//    public String newBooking(Model model) {
//        model.addAttribute("booking", new Booking());
//        return "newBooking";
//    }
//


}