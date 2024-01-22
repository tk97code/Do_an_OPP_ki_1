package Test;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StopwatchExample {
    private JFrame frame;
    private JLabel timerLabel;
    private Timer timer;
    private int hours, minutes, seconds;

    public StopwatchExample() {
        frame = new JFrame("Stopwatch Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);

        timerLabel = new JLabel("Time: 00:00:00");
        timerLabel.setHorizontalAlignment(JLabel.CENTER);
        frame.add(timerLabel);

        // Khởi tạo bộ đếm thời gian với khoảng thời gian là 1000 milliseconds (1 giây)
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimer();
            }
        });

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startTimer();
            }
        });

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopTimer();
            }
        });

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetTimer();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(resetButton);

        frame.add(buttonPanel);

        frame.setVisible(true);
    }

    private void startTimer() {
        timer.start();
    }

    private void stopTimer() {
        timer.stop();
    }

    private void resetTimer() {
        timer.stop();
        hours = minutes = seconds = 0;
        updateTimerLabel();
    }

    private void updateTimer() {
        seconds++;
        if (seconds == 60) {
            seconds = 0;
            minutes++;
            if (minutes == 60) {
                minutes = 0;
                hours++;
            }
        }
        updateTimerLabel();
    }

    private void updateTimerLabel() {
        String formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        System.out.println(hours + " " + minutes + " " + seconds);
        timerLabel.setText("Time: " + formattedTime);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StopwatchExample();
            }
        });
    }
}
