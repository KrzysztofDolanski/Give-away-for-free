package com.example.gaff.booking;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookingMapping {


    public Booking mapToBooking(BookingDto bookingDto){
        return Booking.builder()
                .id(bookingDto.getId())
                .articleId(bookingDto.getArticleId())
                .buyerId(bookingDto.getBuyerId())
                .sellerId(bookingDto.getSellerId())
                .build();
    }

    public BookingDto mapToBookingDto(Booking booking){
        return BookingDto.builder()
                .id(booking.getId())
                .articleId(booking.getArticleId())
                .buyerId(booking.getBuyerId())
                .sellerId(booking.getSellerId()).build();
    }

    public List<BookingDto> mapToBookingDtoList(List<Booking> bookingsByUsername) {
        return bookingsByUsername.stream().map(this::mapToBookingDto).collect(Collectors.toList());
    }
}
