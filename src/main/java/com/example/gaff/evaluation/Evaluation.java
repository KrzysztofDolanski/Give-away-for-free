package com.example.gaff.evaluation;

import com.example.gaff.api_user.ApiUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Evaluation {

    @Id
    private Long id;


    private String opinionOfUserWitchGiveAway;
    private MarksToEvaluateBooking markOfUserWitchGiveAway;

    private String opinionOfUserWitchTakeForFree;
    private MarksToEvaluateBooking markOfUserWitchTakeForFree;

    @OneToMany
    private List<ApiUser> user;

}
