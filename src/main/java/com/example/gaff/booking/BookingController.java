package com.example.gaff.booking;

import com.example.gaff.api_user.ApiUserDto;
import com.example.gaff.api_user.ApiUserService;
import com.example.gaff.article.ArticleDto;
import com.example.gaff.article.ArticleMapper;
import com.example.gaff.article.ArticleService;
import com.example.gaff.exceptions.ApiUserAlreadyExistsException;
import com.example.gaff.image.ArticleFiles;
import com.example.gaff.image.ArticleFilesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor

public class BookingController {

    private final ArticleService articleService;
    private final ApiUserService apiUserService;
    private final BookingService bookingService;
    private final ArticleFilesService articleFilesService;


    @GetMapping("/bookings")
    public String showNewBookingPage(Model model) {
        List<ApiUserDto> users = apiUserService.getAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("booking", new BookingDto());
        model.addAttribute("article", articleService.getAllAvailableArticles());
        model.addAttribute("isAdd", false);
        return "booking/booking";
    }


    @GetMapping("/order")
    public String showBookingPage(Model model, HttpServletRequest request){
        List<ApiUserDto> users = apiUserService.getAllUsers();
        List<ArticleDto> allAvailableArticlesExceptLoggedUser = articleService.getAllAvailableArticlesExceptLoggedUser(request);
        List<ArticleFiles> allFiles = articleFilesService.getAllFiles();

        model.addAttribute("users", users);
        model.addAttribute("booking", new BookingDto());
        model.addAttribute("files", allFiles);
        model.addAttribute("article", allAvailableArticlesExceptLoggedUser);
        model.addAttribute("isAdd", false);
        return "booking/order";
    }

    @GetMapping("/save/booking/{id}")
    public String save(@PathVariable Long id, RedirectAttributes redirectAttributes, HttpServletRequest request)
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
//            model.addAttribute("errormessage", "Booking saving failed");
//            model.addAttribute("booking", bookingDto);
            return "save/booking";
        }

//    @PostMapping("/booking")
//    public String saveBooking(@ModelAttribute("booking") Booking booking, BindingResult bindingResult,
////                              @RequestParam("apiUser") long userId,
////                              @RequestParam("article") long articleId,
//                              @RequestParam("collectionDate") LocalDateTime collectionDT, Model model) {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("errors", bindingResult.getAllErrors());
//            model.addAttribute("booking", new Booking());
//            model.addAttribute("article", articleFetchService.getAllArticle());
//            model.addAttribute("users", apiUserService.getAllUsers());
//            return "booking/booking";
//        }
//        booking.setArticle(articleFetchService.getAllArticle().get(0));
//        booking.setApiUsers(apiUserService.getAllUsers());
//        booking.setCollectionDateTime(collectionDT);
//        bookingService.saveBooking(booking);
//
//        model.addAttribute("bookings", bookingService.getAllBookingPaged(0));
//        model.addAttribute("currentPage", 0);
//        return "booking/booking";
//
    }
//
//    @GetMapping("/save/booking")
//    public String deleteBooking(@PathParam("id") Long id, BookingDto bookingDto, Model model, HttpServletRequest request) {
//        ArticleDto articleById = articleService.findArticleById(id);
//        bookingDto.setArticleId(articleById.getId());
//
//        bookingService.saveBooking(bookingDto, request);
//
//        model.addAttribute("currentPage", 0);
//        return "bookings";
//
//    }

//    @RequestMapping(value = "booking", method = RequestMethod.GET)
////    @GetMapping("/bookings")
//    public String showBookingList(
////            @RequestParam(defaultValue = "0") int pageNo,
//            Model model) {
////        model.addAttribute("bookings", bookingService.getAllBookingPaged(pageNo));
////        model.addAttribute("currentPage", pageNo);
//        List<ApiUserDto> allUsers = apiUserService.getAllUsers();
//        model.addAttribute("users", allUsers);
//        model.addAttribute("isAdd", false);
//        return "booking/booking";
//
//    }

//    @GetMapping("booking/search")
//    public String showSearchBooking(Model model) {
//        model.addAttribute("apiUsers", apiUserService.getAllUsers());
//        model.addAttribute("bookings", null);
//        return "searchBookings";
//
//    }

//    @PostMapping("/booking/search")
//    public String searchBooking (@RequestParam("collectionDateTime") String collectionDT, Model model) {
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate collectionTime = LocalDate.parse(collectionDT, dtf);
//
//        List<Booking> bookings = bookingService.getAllBookingsByApiUserAndCollectionDateTime(collectionTime);
//        if (bookings.isEmpty()) {
//            model.addAttribute("notFound");
//        }else {
//            model.addAttribute("bookings", bookings);
//        }
//
//        model.addAttribute("bookings", apiUserService.getAllUsers());
//        return "search-booking";
//    }
//    @GetMapping("booking")
//    public String showBookingPage(Model model) {
//        model.addAttribute("apiUser", apiUserService.getAllUsers());
//        return "booking";
//    }


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

