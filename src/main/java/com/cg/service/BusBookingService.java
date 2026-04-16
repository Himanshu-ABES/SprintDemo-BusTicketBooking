package com.cg.service;

import com.cg.entity.BusBooking;
import com.cg.entity.RouteSchedule;
import java.util.List;

public interface BusBookingService {
    RouteSchedule createSchedule(RouteSchedule schedule);
    BusBooking bookTicket(Long customerId, Long scheduleId, BusBooking booking);
    List<BusBooking> getBookingsByCustomer(Long customerId);
}