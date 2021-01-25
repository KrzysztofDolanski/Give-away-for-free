package com.example.gaff.api_user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String confirmationToken;
    private LocalDate createdDate;

    @OneToOne(targetEntity = ApiUser.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private ApiUser apiUser;


    public ConfirmationToken(ApiUser apiUser) {
        this.apiUser = apiUser;
        this.createdDate = LocalDate.now();
        this.confirmationToken = UUID.randomUUID().toString();;
    }
}
