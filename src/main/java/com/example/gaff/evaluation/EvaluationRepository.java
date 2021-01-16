package com.example.gaff.evaluation;

import com.example.gaff.api_user.ApiUserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {

    public List<Evaluation> findEvaluationBySellerOpinion(String sellerOpinion);

}
