export interface BusRoute {
    id: number;
    src: string;
    dest: string;
}

export interface RouteSchedule {
    id: number;
    busRoute: BusRoute;
    departureTime: string;
    scheduleDt: string;
    avlSeats: number;
    totSeats: number;
    schStatus: string;
}
