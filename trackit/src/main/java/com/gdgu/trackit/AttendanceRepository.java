package com.gdgu.trackit;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AttendanceRepository extends CrudRepository<Attendance, Integer> {
    
    List<Attendance> findAttendanceByIdBetween(int a, int b);

    Attendance findFirstByOrderByIdDesc();

    @Query("SELECT DISTINCT attendance.name FROM Attendance attendance")
    List<String> getClasses();

    @Query("SELECT attendance.name, count(attendance) FROM Attendance attendance WHERE attendance.state = 0 GROUP BY attendance.name")
    List<Object[]> getPresent(); 

    @Query("SELECT attendance.name, count(attendance) FROM Attendance attendance WHERE attendance.state = 1 GROUP BY attendance.name")
    List<Object[]> getAbsent();

    @Query("SELECT attendance.name, count(attendance) From Attendance attendance WHERE attendance.state = 0 OR attendance.state = 1 GROUP BY attendance.name")
    List<Object[]> getTotal();

}
