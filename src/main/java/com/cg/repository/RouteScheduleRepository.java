package com.cg.repository;

import com.cg.entity.RouteSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface RouteScheduleRepository extends JpaRepository<RouteSchedule, Long> {
    @Query("SELECT s FROM RouteSchedule s WHERE " +
           "LOWER(s.busRoute.src) = LOWER(:src) AND " +
           "LOWER(s.busRoute.dest) = LOWER(:dest) AND " +
           "s.scheduleDt = :date AND " +
           "s.avlSeats > 0 AND " +
           "s.schStatus = 'ACTIVE'")
    List<RouteSchedule> searchSchedules(@Param("src") String src,
                                        @Param("dest") String dest,
                                        @Param("date") LocalDate date);
}