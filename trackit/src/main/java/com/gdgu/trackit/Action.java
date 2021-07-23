package com.gdgu.trackit;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Action implements ActionListener {

    private AttendanceService attendanceService;
    private JButton button;
    private Attendance attendance;

    public Action(AttendanceService attendanceService, JButton button, Attendance attenance) {
        this.attendanceService = attendanceService;
        this.button = button;
        this.attendance = attenance;
        button.setText(attendance.getName());
        setColorAndState();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        changeColor();
        attendanceService.saveAttendance(attendance);
    }

    private void setColorAndState() {
        if (attendance.getName().equals("Break")) {
            button.setEnabled(false);
        } 
        else {
            if (attendance.getState() == State.UNKNOWN)
                button.setBackground(Settings.getSettings().getUnknown());
            else if (attendance.getState() == State.ATTENDED)
                button.setBackground(Settings.getSettings().getAttended());
            else 
                button.setBackground(Settings.getSettings().getNotAttended());
        }
    }

    private void changeColor() {
        if (attendance.getState() == State.UNKNOWN) {
            button.setBackground(Settings.getSettings().getAttended());
            attendance.setState(State.ATTENDED);
        } else if (attendance.getState() == State.ATTENDED) {   
            button.setBackground(Settings.getSettings().getNotAttended());
            attendance.setState(State.NOTATTENDED);
        } else {
            button.setBackground(Settings.getSettings().getUnknown());
            attendance.setState(State.UNKNOWN);
        }
    }

}
