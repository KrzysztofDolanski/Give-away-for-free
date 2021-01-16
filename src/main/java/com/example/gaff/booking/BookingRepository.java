package com.example.gaff.booking;

import com.example.gaff.api_user.ApiUserDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> getBookingsBySellerId(Long sellerId);

    List<Booking> getBookingsByBuyerId(Long id);

    Booking findBookingByArticleId(Long id);
}
