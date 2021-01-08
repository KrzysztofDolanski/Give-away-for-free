package com.example.gaff.booking;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
//    List<Booking> findAllByApiUserAndCollectionDate(LocalDateTime collectionDate);
}
