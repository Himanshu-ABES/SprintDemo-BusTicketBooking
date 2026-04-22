package com.cg.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.cg.dto.BookingRequest;
import com.cg.entity.BusBooking;
import com.cg.exception.CustomerNotFoundException;
import com.cg.exception.GlobalExceptionHandler;
import com.cg.exception.NoSeatsAvailableException;
import com.cg.security.JwtAuthFilter;
import com.cg.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BookingController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(GlobalExceptionHandler.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookingService bookingService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @Test
    void bookTicket_returnsCreatedResponse() throws Exception {
        BookingRequest request = buildRequest(1L, 10L, 1);

        BusBooking booking = new BusBooking();
        booking.setId(100L);
        booking.setBookingStatus("CONFIRMED");

        when(bookingService.bookTicket(any(BookingRequest.class))).thenReturn(booking);

        mockMvc.perform(post("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(100))
                .andExpect(jsonPath("$.bookingStatus").value("CONFIRMED"));

        verify(bookingService).bookTicket(any(BookingRequest.class));
    }

    @Test
    void getBookingsByCustomer_returnsOkWithBookings() throws Exception {
        BusBooking first = new BusBooking();
        first.setId(1L);
        first.setBookingStatus("CONFIRMED");

        BusBooking second = new BusBooking();
        second.setId(2L);
        second.setBookingStatus("CONFIRMED");

        when(bookingService.getBookingsByCustomer(5L)).thenReturn(List.of(first, second));

        mockMvc.perform(get("/api/bookings/customer/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));

        verify(bookingService).getBookingsByCustomer(5L);
    }

    @Test
    void getBookingsByCustomer_returnsOkWithEmptyList() throws Exception {
        when(bookingService.getBookingsByCustomer(8L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/bookings/customer/8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));

        verify(bookingService).getBookingsByCustomer(8L);
    }

    @Test
    void bookTicket_whenCustomerNotFound_returnsNotFound() throws Exception {
        BookingRequest request = buildRequest(999L, 10L, 1);

        when(bookingService.bookTicket(any(BookingRequest.class)))
                .thenThrow(new CustomerNotFoundException("Customer not found: 999"));

        mockMvc.perform(post("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Customer not found: 999"));

        verify(bookingService).bookTicket(any(BookingRequest.class));
    }

    @Test
    void bookTicket_whenNoSeatsAvailable_returnsBadRequest() throws Exception {
        BookingRequest request = buildRequest(1L, 10L, 2);

        when(bookingService.bookTicket(any(BookingRequest.class)))
                .thenThrow(new NoSeatsAvailableException("Not enough seats available."));

        mockMvc.perform(post("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Not enough seats available."));

        verify(bookingService).bookTicket(any(BookingRequest.class));
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