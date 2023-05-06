import java.awt.*;
import javax.swing.JPanel;

public class CirclePanel extends JPanel {
    private int hour;
    private int minute;
    private int second;

    int getHour() {
        return hour;
    }
    void setHour(int h) {
        hour = h;
    }

    int getMinute() {
        return minute;
    }
    void setMinute(int m) {
        minute = m;
    }

    int getSecond() {
        return second;
    }
    void setSecond(int s) {
        second = s;
    }

    public CirclePanel(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Cast Graphics object to Graphics2D object
        Graphics2D g2d = (Graphics2D) g;

        // Set rendering hints for better graphics quality
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Get the size of the panel
        Dimension size = getSize();

        // Calculate the radius of the circle
        int radius = (int) ((Math.min(size.width, size.height) / 2) * 0.85);

        // Calculate the center of the circle
        int x = size.width / 2 - radius;
        int y = size.height / 2 - radius;

        // Draw the circle
        g2d.setColor(Color.BLACK);
        g2d.drawOval(x, y, radius * 2, radius * 2);

        // Draw clock hands
        int cx = size.width / 2;
        int cy = size.height / 2;
        int hourHandLength = (int) (radius * 0.55);
        int minuteHandLength = (int) (radius * 0.8);
        int secondHandLength = (int) (radius * 0.9);

        // Draw hour hand
        g2d.setColor(Color.black);
        int hourAngle = (hour % 12) * 30 + (int) (0.5 * minute);
        double hourRad = Math.toRadians(hourAngle - 90);
        int hx = cx + (int) (hourHandLength * Math.cos(hourRad));
        int hy = cy + (int) (hourHandLength * Math.sin(hourRad));
        g2d.setStroke(new BasicStroke(5));
        g2d.drawLine(cx, cy, hx, hy);

        // Draw minute hand
        g2d.setColor(Color.BLUE);
        int minuteAngle = minute * 6;
        double minuteRad = Math.toRadians(minuteAngle - 90);
        int mx = cx + (int) (minuteHandLength * Math.cos(minuteRad));
        int my = cy + (int) (minuteHandLength * Math.sin(minuteRad));
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(cx, cy, mx, my);

        // Draw second hand
        g2d.setColor(Color.GREEN);
        int secondAngle = second * 6;
        double secondRad = Math.toRadians(secondAngle - 90);
        int sx = cx + (int) (secondHandLength * Math.cos(secondRad));
        int sy = cy + (int) (secondHandLength * Math.sin(secondRad));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(cx, cy, sx, sy);
    }
}