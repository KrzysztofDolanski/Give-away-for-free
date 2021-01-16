package com.example.gaff.evaluation;

import com.example.gaff.api_user.ApiUserDto;
import com.example.gaff.api_user.ApiUserService;
import com.example.gaff.article.Article;
import com.example.gaff.booking.BookingDto;
import com.example.gaff.booking.BookingService;
import com.example.gaff.exceptions.LoggedUserIsNoBuyerException;
import com.example.gaff.exceptions.LoggedUserIsNoSellerException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final EvaluationMapping evaluationMapping;
    private final ApiUserService apiUserService;
    private final BookingService bookingService;

    public List<EvaluationDto> getAllBookings() {
        return evaluationMapping.mapToEvaluationDtoList(evaluationRepository.findAll());
    }

    public void saveEvaluation(EvaluationDto evaluationDto, HttpServletRequest request) {
        ApiUserDto userByUsername = apiUserService.getUserByUsername(apiUserService.currentUsername(request));

        Evaluation evaluation = evaluationMapping.mapToEvaluation(evaluationDto);
        evaluationRepository.save(evaluation);

    }

    //todo jestem na etapie znalezienia jakie bookingi należą do usera


    public List<BookingDto> getAllLoggedUserBookingsSeller(HttpServletRequest request) {
        ApiUserDto userByUsername = apiUserService.getUserByUsername(apiUserService.currentUsername(request));

        List<BookingDto> bookingDtos = null;

        try {
            bookingDtos = bookingService.getLoggedUserBookingsSeller(userByUsername);
        } catch (LoggedUserIsNoSellerException e) {
            e.printStackTrace();
        }

        return bookingDtos;
    }


    public List<BookingDto> getAllLoggedUserBookingsBuyer(HttpServletRequest request) {
        ApiUserDto userByUsername = apiUserService.getUserByUsername(apiUserService.currentUsername(request));
        List<BookingDto> bookingDtos = null;
        try {
            bookingDtos = bookingService.getLoggedUserBookingsBuyer(userByUsername);
        } catch (
                LoggedUserIsNoBuyerException e) {
            e.printStackTrace();
        }
        return bookingDtos;
    }


    public Evaluation findEvaluationBySellerOpinion(String sellerOpinion){
        return evaluationRepository.findEvaluationBySellerOpinion(sellerOpinion).get(0);
    }
}
