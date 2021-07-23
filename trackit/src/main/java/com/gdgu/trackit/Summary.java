package com.gdgu.trackit;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Summary {

    private JFrame frame;
    private JPanel firstPanel, secondPanel;
    private List<JTextArea> textAreaList;
    private List<Map<String, Long>> firstLayerMapList;

    private AttendanceService attendanceService;

    public Summary(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;

        frame = new JFrame();
        firstPanel = new JPanel();
        secondPanel = new JPanel();
        textAreaList = new ArrayList<>();
        firstLayerMapList = new ArrayList<>();

        for (int i = 0; i < 3; i ++) {
            Map<String, Long> temp = new LinkedHashMap<>();
            for (String j : attendanceService.getClasses()) {
                temp.put(j, 0L);
            }
            firstLayerMapList.add(temp);
        }

        setup();
    }

    private void setup() {
        frame.setTitle("Summary");
        frame.setLayout(new GridLayout(2, 1));
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);

        firstPanel.setLayout(new GridLayout(1, 3));
        secondPanel.setLayout(new GridLayout(1, 2));

        for (int i = 0; i < 6; i ++) {
            JTextArea textArea = new JTextArea();
            textArea.setEditable(false);
            textArea.setBorder(new LineBorder(Color.black, 1));
            textAreaList.add(textArea);
            textArea.setFont(Settings.getSettings().getSumaryFont());
        }

        calculateDate();

        firstPanel.add(textAreaList.get(0));
        firstPanel.add(textAreaList.get(1));
        firstPanel.add(textAreaList.get(2));
        secondPanel.add(textAreaList.get(3));
        secondPanel.add(textAreaList.get(4));

        frame.add(firstPanel);
        frame.add(secondPanel);
        frame.setVisible(true);
    }

    private void calculateDate() {
        textAreaList.get(0).append("Total:\n");
        for (Map.Entry<String, Long> entry : attendanceService.getTotal().entrySet()) {
            textAreaList.get(0).append(entry.getKey() + " : " + entry.getValue() + "\n");
        }
        textAreaList.get(1).append("Present:\n");
        for (Map.Entry<String, Long> entry : attendanceService.getPresent().entrySet()) {
            textAreaList.get(1).append(entry.getKey() + " : " + entry.getValue() + "\n");
        }
        textAreaList.get(2).append("Absent:\n");
        for (Map.Entry<String, Long> entry : attendanceService.getAbsent().entrySet()) {
            textAreaList.get(2).append(entry.getKey() + " : " + entry.getValue() + "\n");
        }
        textAreaList.get(3).append("Attendance Percentage:\n");
        for (Map.Entry<String, Float> entry : attendanceService.getPresentPercentage().entrySet()) {
            textAreaList.get(3).append(entry.getKey() + " : " + String.format("%.2f", entry.getValue()) + "%" + "\n");
        }
        textAreaList.get(4).append("Skip Count:\n");
        for (Map.Entry<String, Integer> entry : attendanceService.getDayOff().entrySet()) {
            textAreaList.get(4).append(entry.getKey() + " : " + entry.getValue() + "\n");
        }
    }
}
