package com.cg.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cg.dto.BookingRequest;
import com.cg.entity.BusBooking;
import com.cg.entity.Customer;
import com.cg.entity.RouteSchedule;
import com.cg.exception.CustomerNotFoundException;
import com.cg.exception.NoSeatsAvailableException;
import com.cg.exception.ScheduleNotFoundException;
import com.cg.repository.BusBookingRepository;
import com.cg.repository.CustomerRepository;
import com.cg.repository.RouteScheduleRepository;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    private RouteScheduleRepository scheduleRepo;

    @Mock
    private BusBookingRepository bookingRepo;

    @Mock
    private CustomerRepository customerRepo;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Test
    void bookTicket_whenRequestIsValid_savesBookingAndReducesSeats() {
        BookingRequest request = buildRequest(1L, 10L, 2);

        Customer customer = new Customer();
        customer.setCustId(1L);

        RouteSchedule schedule = new RouteSchedule();
        schedule.setId(10L);
        schedule.setSchStatus("ACTIVE");
        schedule.setAvlSeats(10);

        when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));
        when(scheduleRepo.findById(10L)).thenReturn(Optional.of(schedule));
        when(bookingRepo.save(any(BusBooking.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BusBooking result = bookingService.bookTicket(request);

        assertNotNull(result);
        assertSame(customer, result.getCustomer());
        assertSame(schedule, result.getSchedule());
        assertEquals("CONFIRMED", result.getBookingStatus());
        assertEquals(2, result.getPassengers().size());
        assertEquals(8, schedule.getAvlSeats());

        ArgumentCaptor<BusBooking> bookingCaptor = ArgumentCaptor.forClass(BusBooking.class);
        verify(scheduleRepo).save(schedule);
        verify(bookingRepo).save(bookingCaptor.capture());
        assertEquals(2, bookingCaptor.getValue().getPassengers().size());
    }

    @Test
    void bookTicket_whenCustomerDoesNotExist_throwsCustomerNotFoundException() {
        BookingRequest request = buildRequest(99L, 10L, 1);

        when(customerRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> bookingService.bookTicket(request));
        verify(scheduleRepo, never()).findById(any());
        verify(bookingRepo, never()).save(any());
    }

    @Test
    void bookTicket_whenScheduleDoesNotExist_throwsScheduleNotFoundException() {
        BookingRequest request = buildRequest(1L, 999L, 1);

        Customer customer = new Customer();
        customer.setCustId(1L);

        when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));
        when(scheduleRepo.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ScheduleNotFoundException.class, () -> bookingService.bookTicket(request));
        verify(bookingRepo, never()).save(any());
    }

    @Test
    void bookTicket_whenScheduleIsInactive_throwsNoSeatsAvailableException() {
        BookingRequest request = buildRequest(1L, 10L, 1);

        Customer customer = new Customer();
        customer.setCustId(1L);

        RouteSchedule schedule = new RouteSchedule();
        schedule.setId(10L);
        schedule.setSchStatus("INACTIVE");
        schedule.setAvlSeats(10);

        when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));
        when(scheduleRepo.findById(10L)).thenReturn(Optional.of(schedule));

        assertThrows(NoSeatsAvailableException.class, () -> bookingService.bookTicket(request));
        verify(scheduleRepo, never()).save(any());
        verify(bookingRepo, never()).save(any());
    }

    @Test
    void getBookingsByCustomer_returnsBookingsFromRepository() {
        BusBooking first = new BusBooking();
        first.setId(1L);
        BusBooking second = new BusBooking();
        second.setId(2L);

        when(bookingRepo.findByCustomerCustId(42L)).thenReturn(List.of(first, second));

        List<BusBooking> result = bookingService.getBookingsByCustomer(42L);

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
        verify(bookingRepo).findByCustomerCustId(42L);
    }

    private BookingRequest buildRequest(Long customerId, Long scheduleId, int passengerCount) {
        BookingRequest request = new BookingRequest();
        request.setCustomerId(customerId);
        request.setScheduleId(scheduleId);

        List<BookingRequest.PassengerDetail> passengers = java.util.stream.IntStream.rangeClosed(1, passengerCount)
                .mapToObj(i -> {
                    BookingRequest.PassengerDetail detail = new BookingRequest.PassengerDetail();
                    detail.setPassengerName("Passenger " + i);
                    detail.setPassengerAge(20 + i);
                    detail.setSeatNo("S" + i);
                    return detail;
                })
                .toList();

        request.setPassengers(passengers);
        return request;
    }
}
