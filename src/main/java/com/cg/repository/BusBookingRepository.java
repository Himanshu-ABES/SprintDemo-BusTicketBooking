package com.cg.repository;

import com.cg.entity.BusBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BusBookingRepository extends JpaRepository<BusBooking, Long> {

    // Find all bookings by customer ID
    List<BusBooking> findByCustomerCustId(Long custId);
}