package com.cg.service;

import com.cg.entity.RouteSchedule;
import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    RouteSchedule createSchedule(RouteSchedule schedule);
    List<RouteSchedule> searchSchedules(String src, String dest, LocalDate date);
}