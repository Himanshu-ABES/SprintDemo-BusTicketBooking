export interface PassengerDetail {
    passengerName: string;
    passengerAge: number;
    seatNo: string;
}

export interface BookingRequest {
    customerId: number;
    scheduleId: number;
    passengers: PassengerDetail[];
}

export interface Passenger {
    id: number;
    passengerName: string;
    passengerAge: number;
    seatNo: string;
}

export interface BusBooking {
    id: number;
    schedule: any;
    customer: any;
    bookingDt: string;
    bookingStatus: string;
    passengers: Passenger[];
}
