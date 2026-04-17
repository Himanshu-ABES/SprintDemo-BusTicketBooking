package com.cg.service;

import com.cg.dto.BookingRequest;
import com.cg.entity.BusBooking;
import com.cg.entity.Customer;
import com.cg.entity.Passenger;
import com.cg.entity.RouteSchedule;
import com.cg.exception.CustomerNotFoundException;
import com.cg.exception.NoSeatsAvailableException;
import com.cg.exception.ScheduleNotFoundException;
import com.cg.repository.BusBookingRepository;
import com.cg.repository.CustomerRepository;
import com.cg.repository.RouteScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private RouteScheduleRepository scheduleRepo;
    @Autowired
    private BusBookingRepository bookingRepo;
    @Autowired
    private CustomerRepository customerRepo;

    @Override
    public BusBooking bookTicket(BookingRequest request) {
        Customer customer = customerRepo.findById(request.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found: " + request.getCustomerId()));

        RouteSchedule schedule = scheduleRepo.findById(request.getScheduleId())
                .orElseThrow(() -> new ScheduleNotFoundException("Schedule not found: " + request.getScheduleId()));

        int seatsNeeded = request.getPassengers().size();

        if (!"ACTIVE".equals(schedule.getSchStatus()) || schedule.getAvlSeats() < seatsNeeded) {
            throw new NoSeatsAvailableException("Not enough seats available. Requested: " + seatsNeeded + ", Available: " + schedule.getAvlSeats());
        }

        BusBooking booking = new BusBooking();
        booking.setCustomer(customer);
        booking.setSchedule(schedule);
        booking.setBookingDt(LocalDate.now());
        booking.setBookingStatus("CONFIRMED");

        List<Passenger> passengers = request.getPassengers().stream().map(pReq -> {
            Passenger p = new Passenger();
            p.setPassengerName(pReq.getPassengerName());
            p.setPassengerAge(pReq.getPassengerAge());
            p.setSeatNo(pReq.getSeatNo());
            p.setBusBooking(booking);
            return p;
        }).collect(Collectors.toList());

        booking.setPassengers(passengers);
        schedule.setAvlSeats(schedule.getAvlSeats() - seatsNeeded);
        scheduleRepo.save(schedule);

        return bookingRepo.save(booking);
    }

    @Override
    public List<BusBooking> getBookingsByCustomer(Long customerId) {
        return bookingRepo.findByCustomerCustId(customerId);
    }
}