package com.example.gaff.booking;

import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingService {

    Page<Booking> getAllBookingPaged(int pageNum);

    List<Booking> getAllBookings();

    Booking getBookingById(long bookingId);

    Booking saveBooking(Booking booking);

    void deleteBookingById(long bookingId);

    List<Booking> getAllBookingsByApiUserAndCollectionDateTime(LocalDate collectionDate);
}
