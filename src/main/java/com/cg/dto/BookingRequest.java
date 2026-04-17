package com.cg.dto;

import java.util.List;

public class BookingRequest {
    private Long customerId;
    private Long scheduleId;
    private List<PassengerDetail> passengers;

    public static class PassengerDetail {
        private String passengerName;
        private int passengerAge;
        private String seatNo;

        public String getPassengerName() { return passengerName; }
        public void setPassengerName(String passengerName) { this.passengerName = passengerName; }
        public int getPassengerAge() { return passengerAge; }
        public void setPassengerAge(int passengerAge) { this.passengerAge = passengerAge; }
        public String getSeatNo() { return seatNo; }
        public void setSeatNo(String seatNo) { this.seatNo = seatNo; }
    }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public Long getScheduleId() { return scheduleId; }
    public void setScheduleId(Long scheduleId) { this.scheduleId = scheduleId; }
    public List<PassengerDetail> getPassengers() { return passengers; }
    public void setPassengers(List<PassengerDetail> passengers) { this.passengers = passengers; }
}