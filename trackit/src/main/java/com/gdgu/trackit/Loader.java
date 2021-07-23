package com.gdgu.trackit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Loader {

    private AttendanceService attendanceService;

    @Autowired
    public Loader(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    public void loadTimeTable() throws IOException {
        File file = new File("TimeTable.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        long attendanceCount = attendanceService.attendanceCount();
        LocalDate localDate; 
        if (attendanceCount == 0) {
            localDate = DateUtil.convert(Settings.getSettings().getStartingDate());
        } else {
            localDate = attendanceService.getLastestDate();
            localDate = localDate.plusDays(3);
        }
        String line = bufferedReader.readLine();
        int period = 1;
        while(true) {
            line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            if (line.equals("")) {
                bufferedReader.readLine();
                line = bufferedReader.readLine();
            }
            if (period == 9) {
                period = 1;
                localDate = localDate.plusDays(1);
            }
            Attendance attendance = new Attendance(line, period, localDate);
            period ++;
            attendanceService.saveAttendance(attendance);
            System.out.println(attendance);
        }
        bufferedReader.close();
    }
}
