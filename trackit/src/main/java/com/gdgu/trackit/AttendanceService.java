package com.gdgu.trackit;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {
    
    private AttendanceRepository attendanceRepository; 

    @Autowired
    public AttendanceService(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    public long attendanceCount() {
        return attendanceRepository.count();
    }

    public void saveAttendance(Attendance attendance) {
        attendanceRepository.save(attendance);
    }

    public List<Attendance> getAttendanceBetween(int a, int b) {
        return attendanceRepository.findAttendanceByIdBetween(a, b);
    }

    public LocalDate getLastestDate() {
        return attendanceRepository.findFirstByOrderByIdDesc().getDate();
    }

    public List<String> getClasses() {
        return attendanceRepository.getClasses()
                                   .stream()
                                   .filter(x -> !x.equals("Break"))
                                   .collect(Collectors.toList());
    }

    public Map<String, Long> getPresent(){
        Map<String, Long> temp = generateSkeleton();
        for (Object[] ob : attendanceRepository.getPresent()) {
            temp.replace((String)ob[0], (Long)ob[1]);
        }
        return temp;
    } 

    public Map<String, Long> getAbsent() {
        Map<String, Long> temp = generateSkeleton();
        for (Object[] ob : attendanceRepository.getAbsent()) {
            temp.replace((String)ob[0], (Long)ob[1]);
        }
        return temp;
    }

    public Map<String, Long> getTotal() {
        Map<String, Long> temp = generateSkeleton();
        for (Object[] ob : attendanceRepository.getTotal()) {
            temp.replace((String)ob[0], (Long)ob[1]);
        }
        return temp;
    }   

    public Map<String, Long> generateSkeleton() {
        Map<String, Long> temp = new LinkedHashMap<>();
        for (String i : getClasses()) {
            temp.put(i, 0L);
        }
        return temp;
    }

    public Map<String, Float> getPresentPercentage() {
        Map<String, Long> total = getTotal();
        Map<String, Long> present = getPresent();
        Map<String, Float> temp = new LinkedHashMap<>();
        for (String i : getClasses()) {
            temp.put(i, 0F);
        }
        for (String i : getClasses()) {
            if (total.get(i) != 0F) {
                float result = 100f/total.get(i)*present.get(i);
                temp.put(i, result);
            } else {
                temp.put(i, 0F);
            }
            
        }
        return temp;
    }

    public Map<String, Integer> getDayOff() {
        Map<String, Long> total = getTotal();
        Map<String, Long> present = getPresent();
        Map<String, Integer> temp = new LinkedHashMap<>();
        for (String i : getClasses()) {
            temp.put(i, 0);
        }
        for (String i : getClasses()) {
            float percentage = 100;
            int count = 0;
            while (percentage >= Settings.getSettings().getPercentageLimit()) {
                count ++;
                percentage = 100f/(total.get(i)+count)*(present.get(i));
            }
            temp.put(i, -- count);
        }       
        return temp;
    }
}
