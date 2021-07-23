package com.gdgu.trackit;

import javax.swing.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value="prototype")
public class Tracker {
    
    private JFrame frame; 
    private JPanel timeTablePanel;
    private JPanel topPanel;
    private JButton backwardButton, forwardButton, pageCountButton;
    private JPanel bottomPanel;
    private JButton bottomButton;

    private List<List<JButton>> classButtonList;

    private int rowCount = 6, columnCount = 9, currentPageCount = 1;

    private AttendanceService attendanceService;

    @Autowired
    public Tracker(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;

        frame = new JFrame();
        timeTablePanel = new JPanel();
        topPanel = new JPanel();
        backwardButton = new JButton("<");
        forwardButton = new JButton(">");
        pageCountButton = new JButton("Page: " + currentPageCount);
        bottomPanel = new JPanel();
        bottomButton = new JButton("Summary");

        classButtonList = new ArrayList<>();

        setup();
        updateClassButtons();
    }

    private void setup() {
        frame.setTitle("Track It");
        frame.setLayout(new BorderLayout());
        frame.setSize(1150, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        topPanel.setLayout(new GridLayout(1, 3));
        forwardButton.setBackground(Color.WHITE);
        pageCountButton.setHorizontalAlignment(JLabel.CENTER);
        pageCountButton.setEnabled(false);
        pageCountButton.setBackground(Color.WHITE);
        backwardButton.setBackground(Color.WHITE);

        topPanel.add(backwardButton);
        topPanel.add(pageCountButton);
        topPanel.add(forwardButton); 

        timeTablePanel.setLayout(new GridLayout(rowCount, columnCount));

        for (int i = 0; i < rowCount; i ++) {
            List<JButton> tempList = new ArrayList<>();
            for (int j = 0; j < columnCount; j ++) {
                JButton button = new JButton();
                button.setBackground(Color.WHITE);
                timeTablePanel.add(button);
                tempList.add(button);
            }
            classButtonList.add(tempList);
        }

        for (int i = 0; i < columnCount; i ++) {
            classButtonList.get(0).get(i).setEnabled(false);;
            classButtonList.get(0).get(i).setText(""+i);
        }

        for (int i = 1; i < rowCount; i ++) {
            classButtonList.get(i).get(0).setEnabled(false);;
        }

        bottomPanel.setBackground(Color.white);
        bottomButton.setBackground(Color.white);
        bottomButton.addActionListener((ActionEvent e) -> new Summary(attendanceService));  
        bottomPanel.add(bottomButton);

        updateDates();
        forwardBackwardAction();

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.add(timeTablePanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void updateDates() {
        LocalDate localDate = DateUtil.convert(Settings.getSettings().getStartingDate());
        localDate = localDate.plusWeeks(currentPageCount-1);
        for (int i = 1; i < rowCount; i ++) {
            classButtonList.get(i).get(0).setText(DateUtil.format(localDate.plusDays(i-1)));
        }
    }

    private void updateClassButtons() {
        List<Attendance> attendanceList = attendanceService.getAttendanceBetween((40*(currentPageCount-1) + 1), 40*currentPageCount);
        int k = 0;
        for (int i = 1; i < rowCount; i ++) {
            for (int j = 1; j < columnCount; j ++) {
                JButton button = classButtonList.get(i).get(j);
                button.addActionListener(new Action(attendanceService, button, attendanceList.get(k ++)));
            }
        }
    }

    private void forwardBackwardAction() {
        forwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPageCount < attendanceService.attendanceCount()/40) {
                    currentPageCount ++;
                    removeAction();
                    updateDates();
                    updateClassButtons();
                    pageCountButton.setText("Page: " + currentPageCount);    
                }
            }
        });
        backwardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPageCount > 1) {
                    currentPageCount --;
                    removeAction();
                    updateDates();
                    updateClassButtons();
                    pageCountButton.setText("Page: " + currentPageCount);
                }
            }
        });
    }

    private void removeAction() {
        for (int i = 0; i < rowCount; i ++) {
            for (int j = 0; j < columnCount; j ++) {
                JButton button = classButtonList.get(i).get(j);
                ActionListener[] actionListenerList = button.getActionListeners();
                for (ActionListener actionListener: actionListenerList) {
                    button.removeActionListener(actionListener);
                }
            }
        }
    }

}