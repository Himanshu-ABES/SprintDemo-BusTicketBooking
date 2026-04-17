package com.cg.service;

import com.cg.dto.BookingRequest;
import com.cg.entity.BusBooking;
import java.util.List;

public interface BookingService {
    BusBooking bookTicket(BookingRequest request);
    List<BusBooking> getBookingsByCustomer(Long customerId);
}