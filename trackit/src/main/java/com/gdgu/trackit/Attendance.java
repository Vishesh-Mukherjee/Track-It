package com.gdgu.trackit;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public class Attendance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public Attendance(String name, int period, LocalDate date) {
        this.name = name;
        this.period = period;
        this.date = date;
    }

    private State state = State.UNKNOWN;
    private String name;
    private int period;
    private LocalDate date;

}
