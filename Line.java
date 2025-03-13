package paint.shapes;

import java.awt.*;

public class Line extends Shape {

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        // g.drawLine(startX, startY, endX, endY);

        if (isDott) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(color);

            // Set the stroke to create a dotted line
            float[] dashPattern = {8f, 5f}; // Adjust the values to change the dash pattern
            BasicStroke dashedStroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0, dashPattern, 0);
            g2d.setStroke(dashedStroke);
            g2d.drawLine(startX, startY, endX, endY);

        } else {
            if (isFill == false) {
                g.drawLine(startX, startY, endX, endY);
            } else {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(color);
                // Set the stroke to create a smooth line
                g2d.setStroke(new BasicStroke(5));
                g2d.drawLine(startX, startY, endX, endY);
            }
        }
    }
}
