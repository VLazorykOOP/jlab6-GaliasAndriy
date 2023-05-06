import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AnalogClock extends JFrame implements ActionListener {
    private JLabel clockLabel;
    private JButton hourUpBtn, hourDownBtn, minuteUpBtn, minuteDownBtn; //btns
    private JButton secondUpBtn, secondDownBtn;
    private int hour, minute, second;
    private CirclePanel circlePanel;
    public AnalogClock() {
        hour = 12;
        minute = 55;
        second = 28;
        clockLabel = new JLabel(getTime(), SwingConstants.CENTER);
        clockLabel.setFont(new Font("Arial", Font.PLAIN, 36));

        // create all buttons
        hourUpBtn = new JButton("+ Hour");
        hourDownBtn = new JButton("- Hour");
        minuteUpBtn = new JButton("+ Minute");
        minuteDownBtn = new JButton("- Minute");
        secondUpBtn = new JButton("+ Second");
        secondDownBtn = new JButton("- Second");

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
        contentPane.add(clockLabel, BorderLayout.NORTH);
        contentPane.add(circlePanel, BorderLayout.CENTER);
        contentPane.add(btnPanel, BorderLayout.SOUTH); // btnPanel in bottom of our GUI

        setSize(450, 450); // size of frame
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
        clockLabel.setText(getTime());
        circlePanel.setHour(hour);
        circlePanel.setMinute(minute);
        circlePanel.setSecond(second);
        circlePanel.repaint();
        // repaint
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == hourUpBtn) {
            hour = (hour + 1) % 24;
        } else if (e.getSource() == hourDownBtn) {
            hour = (hour + 23) % 24;
        } else if (e.getSource() == minuteUpBtn) {
            minute = (minute + 1) % 60;
            if (minute == 0) {
                hour = (hour + 1) % 24;
            }
        } else if (e.getSource() == minuteDownBtn) {
            minute = (minute + 59) % 60;
            if (minute == 59) {
                hour = (hour + 23) % 24;
            }
        } else if (e.getSource() == secondUpBtn) {
            second = (second + 1) % 60;
            if (second == 0) {
                minute = (minute + 1) % 60;
            }
        } else if (e.getSource() == secondDownBtn) {
            second = (second + 59) % 60;
            if (second == 59) {
                minute = (minute + 23) % 24;
            }
        }
        updateClock();
    }
}