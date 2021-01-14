package com.example.gaff.booking;

import com.example.gaff.api_user.ApiUserDto;
import com.example.gaff.api_user.ApiUserMapping;
import com.example.gaff.api_user.ApiUserService;
import com.example.gaff.exceptions.BookingNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.UUID;

@Service
@Data
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    private final BookingMapping bookingMapping;
    private final ApiUserService apiUserService;
    private final ApiUserMapping apiUserMapping;



    public Booking saveBooking(BookingDto bookingDto, HttpServletRequest request) {
        ApiUserDto userByUsername = apiUserService.getUserByUsername(apiUserService.currentUsername(request));

        BookingDto build = BookingDto.builder()
                .id(generateUniqueId())
                .articleId(bookingDto.getArticleId())
                .sellerId(bookingDto.getSellerId())
                .buyerId(userByUsername.getId()).build();

        bookingDto.setId(generateUniqueId());
        
        bookingDto.setBuyerId(userByUsername.getId());

        Booking booking = bookingMapping.mapToBooking(build);
        return bookingRepository.save(booking);
    }


    Booking findBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("not found booking" + id));
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


    private Long generateUniqueId()
    {
        long val = -1;
        do
        {
            final UUID uid = UUID.randomUUID();
            final ByteBuffer buffer = ByteBuffer.wrap(new byte[16]);
            buffer.putLong(uid.getLeastSignificantBits());
            buffer.putLong(uid.getMostSignificantBits());
            final BigInteger bi = new BigInteger(buffer.array());
            val = bi.longValue();
        }

        while (val < 0);
        return val;
    }
}
