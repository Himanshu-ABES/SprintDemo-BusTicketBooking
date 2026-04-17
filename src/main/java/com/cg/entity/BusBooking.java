package com.cg.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "bus_booking")
public class BusBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private RouteSchedule schedule;

    @ManyToOne
    @JoinColumn(name = "cust_id")
    private Customer customer;

    private LocalDate bookingDt;
    private String bookingStatus;

    @OneToMany(mappedBy = "busBooking", cascade = CascadeType.ALL)
    private List<Passenger> passengers;

    public BusBooking() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public RouteSchedule getSchedule() { return schedule; }
    public void setSchedule(RouteSchedule schedule) { this.schedule = schedule; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public LocalDate getBookingDt() { return bookingDt; }
    public void setBookingDt(LocalDate bookingDt) { this.bookingDt = bookingDt; }
    public String getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(String bookingStatus) { this.bookingStatus = bookingStatus; }
    public List<Passenger> getPassengers() { return passengers; }
    public void setPassengers(List<Passenger> passengers) { this.passengers = passengers; }
}