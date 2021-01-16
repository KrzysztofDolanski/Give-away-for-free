package com.example.gaff.evaluation;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EvaluationMapping {

    public Evaluation mapToEvaluation(EvaluationDto evaluationDto){
        return Evaluation.builder()
                .booking(evaluationDto.getBooking())
                .buyerOpinion(evaluationDto.getBuyerOpinion())
                .buyerMarks(evaluationDto.getBuyerMarks())
                .sellerOpinion(evaluationDto.getSellerOpinion())
                .sellerMarks(evaluationDto.getSellerMarks())
                .build();
    }


    public EvaluationDto mapToEvaluationDto(Evaluation evaluation){
        return EvaluationDto.builder()
                .id(evaluation.getId())
                .booking(evaluation.getBooking())
                .buyerOpinion(evaluation.getBuyerOpinion())
                .buyerMarks(evaluation.getBuyerMarks())
                .sellerOpinion(evaluation.getSellerOpinion())
                .sellerMarks(evaluation.getSellerMarks())
                .build();
    }

    public List<Evaluation> mapToEvaluationList(List<EvaluationDto> evaluationDtos){
        return evaluationDtos.stream().map(this::mapToEvaluation).collect(Collectors.toList());
    }

    public List<EvaluationDto> mapToEvaluationDtoList(List<Evaluation> evaluations){
        return evaluations.stream().map(this::mapToEvaluationDto).collect(Collectors.toList());
    }

}
