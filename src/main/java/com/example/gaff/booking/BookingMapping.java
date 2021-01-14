package com.example.gaff.booking;

import org.springframework.stereotype.Component;

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
}
