package com.example.gaff.evaluation;

import com.example.gaff.article.ArticleDto;
import com.example.gaff.booking.BookingDto;
import com.example.gaff.exceptions.ApiUserAlreadyExistsException;
import com.example.gaff.image.ArticleFilesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class EvaluationController {

    private final EvaluationService evaluationService;

    @GetMapping(value = "/evaluation/booking")
    public String saveEvaluationBooking(Model model, HttpServletRequest request){

        //todo jestem na etapie zrobienia if seller or if buyer

        List<BookingDto> allLoggedUserBookingsSeller = evaluationService.getAllLoggedUserBookingsSeller(request);
        List<BookingDto> allLoggedUserBookingsBuyer = evaluationService.getAllLoggedUserBookingsBuyer(request);

        model.addAttribute("bookingsSeller", allLoggedUserBookingsSeller);

        model.addAttribute("bookingsBuyer", allLoggedUserBookingsBuyer);

        model.addAttribute("evaluation", new EvaluationDto());

//        model.addAttribute("evaluationBuyer", new EvaluationDto());

        model.addAttribute("isAdd", true);
        return "evaluation/evaluation";
    }


    @PostMapping("evaluation/saveE")
    public String save(@ModelAttribute EvaluationDto evaluationDto, RedirectAttributes redirectAttributes, Model model, HttpServletRequest request) throws MessagingException, IOException {

        evaluationService.saveEvaluation(evaluationDto, request);
        Evaluation evaluationBySellerOpinion = evaluationService.findEvaluationBySellerOpinion(evaluationDto.getSellerOpinion());


        if (evaluationBySellerOpinion != null) {
            redirectAttributes.addFlashAttribute("successmessage", "Article successful saved");
            return "redirect:/evaluation/saveE";
        } else {
            model.addAttribute("errormessage", "Article saving failed");
            model.addAttribute("evaluation", evaluationDto);
            return "save/booking";
        }
    }



}
