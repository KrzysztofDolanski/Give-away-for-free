package com.example.gaff.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingDto {

    Long id;
    private LocalDate bookingDate;
    private LocalDate collectionDate;
    private LocalDateTime collectionTime;

    private Long articleId;

    private Long sellerId;

    private Long buyerId;
}
