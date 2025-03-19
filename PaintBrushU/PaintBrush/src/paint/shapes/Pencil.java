package paint.shapes;

import java.awt.*;
import java.util.ArrayList;

public class Pencil extends Shape {

    private ArrayList<Point> points = new ArrayList<>();

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);

        // Set the stroke to create a smooth line
        g2d.setStroke(new BasicStroke(2));

        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }

    // Add a point to the list when the mouse is dragged
    public void addPoint(int x, int y) {
        points.add(new Point(x, y));
    }

    // Clear the points when needed
    public void clearPoints() {
        points.clear();
    }
}
