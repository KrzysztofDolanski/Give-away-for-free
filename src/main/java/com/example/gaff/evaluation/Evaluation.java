package com.example.gaff.evaluation;

import com.example.gaff.booking.Booking;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String sellerOpinion;
    private MarksToEvaluateBooking sellerMarks;

    private String buyerOpinion;
    private MarksToEvaluateBooking buyerMarks;

    @OneToOne
    private Booking booking;

}
