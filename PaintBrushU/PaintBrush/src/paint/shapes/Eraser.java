package paint.shapes;

import java.awt.*;
import java.util.ArrayList;

public class Eraser extends Shape {

    private final ArrayList<Point> points = new ArrayList<>();

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE); // Set the color to white to match the canvas background

        // Set the stroke to create a very thick line for erasing
        float strokeWidth = 20.0f; // Adjust the eraser size as needed
        g2d.setStroke(new BasicStroke(strokeWidth));

        // Draw lines connecting the recorded points to create a continuous eraser stroke
        for (int i = 0; i < points.size() - 1; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);
            g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
        }

        // Reset the stroke to the default after drawing
        g2d.setStroke(new BasicStroke());
    }

    // Add a point to the list when the mouse is dragged
    public void addPoint(int x, int y) {
        points.add(new Point(x, y));
    }

    // Clear the points when needed (e.g., after mouse release)
    public void clearPoints() {
        points.clear();
    }
}
