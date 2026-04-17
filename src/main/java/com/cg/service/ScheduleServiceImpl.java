package com.cg.service;

import com.cg.entity.RouteSchedule;
import com.cg.repository.RouteScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private RouteScheduleRepository scheduleRepo;

    @Override
    public RouteSchedule createSchedule(RouteSchedule schedule) {
        schedule.setSchStatus("ACTIVE");
        schedule.setAvlSeats(schedule.getTotSeats());
        return scheduleRepo.save(schedule);
    }

    @Override
    public List<RouteSchedule> searchSchedules(String src, String dest, LocalDate date) {
        return scheduleRepo.searchSchedules(src, dest, date);
    }
}