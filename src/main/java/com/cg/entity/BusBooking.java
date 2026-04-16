package com.cg.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class BusBooking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "schedule_id")
	private RouteSchedule routeSchedule;
	
	@ManyToOne
	@JoinColumn(name = "cust_id")
	private Customer customer_id;
	private LocalDate booking_dt;
	private String booking_status;
	

}
