package com.example.gaff.booking;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository){
        this.bookingRepository = bookingRepository;
    }

    public Page<Booking> getAllBookingPaged(int pageNum) {
        return bookingRepository.findAll(PageRequest.of(pageNum,5, Sort.by("ApiUser")));
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(long bookingId) {
        return bookingRepository.findById(bookingId).orElse(null);
    }

    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    public void deleteBookingById(long bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    public List<Booking> getAllBookingsByApiUserAndCollectionDateTime(LocalDate collectionDate) {
        return null;
    }

    }


    //   private final ApiUserService apiUserService;

//
//    Booking findBookingById(Long id) {
//        return bookingRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("not found booking" + id));
//    }



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


