import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.*;

public class AnalogClock extends JFrame implements ActionListener {
    //private JLabel clockLabel;
    private JButton hourUpBtn, hourDownBtn, minuteUpBtn, minuteDownBtn; //btns
    private JButton secondUpBtn, secondDownBtn;
    private int hour, minute, second;
    private CirclePanel circlePanel;
    public class InvalidHourValueException extends Exception {
        public InvalidHourValueException() {
            super("Hour value must be from 0 to 23");
        }
    }
    public class InvalidValueException extends Exception {
        public InvalidValueException(String message){
            super(message);
        }
    }

    public AnalogClock() throws InvalidHourValueException, InvalidValueException {
        hour = 0;
        minute = 0;
        second = 0;
        //clockLabel = new JLabel(getTime(), SwingConstants.CENTER); // alignment
        //clockLabel.setFont(new Font("Monospace", Font.BOLD, 32));
        //clockLabel.setFont(new Font("Monospace", Font.BOLD, 32));

        try {
            File file = new File("D:/Java_labs/jlab6-GaliasAndriy/src/time.txt");
            Scanner scanner = new Scanner(file);
            try {
                hour = scanner.nextInt();
                if (hour < 0 || hour > 23) {
                    throw new InvalidHourValueException();
                }
                minute = scanner.nextInt();
                if (second < 0 || second > 59) {
                    throw new InvalidValueException("Minute value must be from 0 to 59");
                }
                second = scanner.nextInt();
                if (minute < 0 || minute > 59) {
                    throw new InvalidValueException("Second value must be from 0 to 59");
                }
            } catch (NumberFormatException error) {
                throw new NumberFormatException();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
        }

        // create all buttons
        hourUpBtn = new JButton("Hour +");
        hourDownBtn = new JButton("Hour -");
        minuteUpBtn = new JButton("Minute +");
        minuteDownBtn = new JButton("Minute -");
        secondUpBtn = new JButton("Second +");
        secondDownBtn = new JButton("Second -");

        // add action listener (onclick)
        hourUpBtn.addActionListener(this);
        hourDownBtn.addActionListener(this);
        minuteUpBtn.addActionListener(this);
        minuteDownBtn.addActionListener(this);
        secondUpBtn.addActionListener(this);
        secondDownBtn.addActionListener(this);

        // Add all existing buttons on JPanel, with some grid layout
        JPanel btnPanel = new JPanel(new GridLayout(3, 2));
        btnPanel.add(hourUpBtn);
        btnPanel.add(hourDownBtn);
        btnPanel.add(minuteUpBtn);
        btnPanel.add(minuteDownBtn);
        btnPanel.add(secondUpBtn);
        btnPanel.add(secondDownBtn);

        // Add CirclePanel
        circlePanel = new CirclePanel(hour, minute, second);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        //contentPane.add(clockLabel, BorderLayout.NORTH);
        contentPane.add(circlePanel, BorderLayout.CENTER);
        contentPane.add(btnPanel, BorderLayout.SOUTH); // btnPanel in bottom of our GUI

        // Set frame parameters
        setSize(450, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit from GUI on close
        setVisible(true);
    }

    private String getTime() {
        String h = (hour < 10) ? "0" + hour : Integer.toString(hour);
        String m = (minute < 10) ? "0" + minute : Integer.toString(minute);
        String s = (second < 10) ? "0" + second : Integer.toString(second);
        return h + ":" + m + ":" + s;
    }

    private void updateClock() {
        //clockLabel.setText(getTime());
        circlePanel.setHour(hour);
        circlePanel.setMinute(minute);
        circlePanel.setSecond(second);
        circlePanel.repaint();
        // repaint
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == hourUpBtn) {
            hour = (hour + 1) % 12;
        } else if (e.getSource() == hourDownBtn) {
            hour = (hour + 11) % 12;
        } else if (e.getSource() == minuteUpBtn) {
            minute = (minute + 1) % 60;
            if (minute == 0) {
                hour = (hour + 1) % 12;
            }
        } else if (e.getSource() == minuteDownBtn) {
            minute = (minute + 59) % 60;
            if (minute == 59) {
                hour = (hour + 11) % 12;
            }
        } else if (e.getSource() == secondUpBtn) {
            second = (second + 1) % 60;
            if (second == 0) {
                minute = (minute + 1) % 60;
            }
        } else if (e.getSource() == secondDownBtn) {
            second = (second + 59) % 60;
            if (second == 59) {
                minute = (minute + 59) % 60;
            }
        }
        updateClock();
    }
}