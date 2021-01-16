package com.example.gaff.evaluation;


import com.example.gaff.booking.Booking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.OneToOne;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationDto {


    private Long id;

    private String sellerOpinion;
    private MarksToEvaluateBooking sellerMarks;

    private String buyerOpinion;
    private MarksToEvaluateBooking buyerMarks;

    private Booking booking;
}
