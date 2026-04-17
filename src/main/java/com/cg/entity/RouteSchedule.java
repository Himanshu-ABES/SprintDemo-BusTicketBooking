package com.cg.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "route_schedule")
public class RouteSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private BusRoute busRoute;

    private LocalTime departureTime;
    private LocalDate scheduleDt;
    private int avlSeats;
    private int totSeats;
    private String schStatus;

    public RouteSchedule() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public BusRoute getBusRoute() { return busRoute; }
    public void setBusRoute(BusRoute busRoute) { this.busRoute = busRoute; }
    public LocalTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalTime departureTime) { this.departureTime = departureTime; }
    public LocalDate getScheduleDt() { return scheduleDt; }
    public void setScheduleDt(LocalDate scheduleDt) { this.scheduleDt = scheduleDt; }
    public int getAvlSeats() { return avlSeats; }
    public void setAvlSeats(int avlSeats) { this.avlSeats = avlSeats; }
    public int getTotSeats() { return totSeats; }
    public void setTotSeats(int totSeats) { this.totSeats = totSeats; }
    public String getSchStatus() { return schStatus; }
    public void setSchStatus(String schStatus) { this.schStatus = schStatus; }
}