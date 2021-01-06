package com.example.gaff.booking;

import com.example.gaff.article.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
 //   private final ApiUserService apiUserService;

    List<Booking> getAllBooking(){
        return bookingRepository.findAll();
    }

    Booking findBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("not found booking" + id));
    }



    // get reservation for logged user
    //    // get reservation for logged user
//    @Transactional
//    public Optional<Booking> getBookingForLoggedUserById(long bookingId) {
//
//        return bookingRepository.findById(bookingId);
//    }
//
//    //get all reservations for logger user
//    @Transactional
//    public Collection<Booking> getBookingForLoggedUser() {
//        return bookingRepository.findAllById((ApiUserService.g))
//    }

}
