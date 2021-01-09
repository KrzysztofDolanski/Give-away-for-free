package com.example.gaff.booking;

import com.example.gaff.article.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository){
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Page<Booking> getAllBookingPaged(int pageNum) {
        return bookingRepository.findAll(PageRequest.of(pageNum,5, Sort.by("ApiUser")));
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking getBookingById(long bookingId) {
        return bookingRepository.findById(bookingId).orElse(null);
    }

    @Override
    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public void deleteBookingById(long bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    @Override
    public List<Booking> getAllBookingsByApiUserAndCollectionDateTime(LocalDate collectionDate) {
        return null;
    }

    }







    //   private final ApiUserService apiUserService;

//    List<Booking> getAllBooking(){
//        return bookingRepository.findAll();
//    }
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


