package paint.shapes;

import java.awt.*;

public class Oval extends Shape {

    @Override
    public void draw(Graphics g) {
        int x = Math.min(startX, endX);
        int y = Math.min(startY, endY);
        int width = Math.abs(endX - startX);
        int height = Math.abs(endY - startY);

        g.setColor(color);
        if (isDott) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(color);

            // Set the stroke to create a dotted line
            float[] dashPattern = {8f, 5f}; // Adjust the values to change the dash pattern
            BasicStroke dashedStroke = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0, dashPattern, 0);
            g2d.setStroke(dashedStroke);
            if (isFill == false) {
                g2d.drawOval(x, y, width, height);
            } else {
                g2d.fillOval(x, y, width, height);
            }

            g2d.setStroke(new BasicStroke());
        } else {
            g.setColor(color);
            if (isFill == false) {
                g.drawOval(x, y, width, height);
            } else {
                g.fillOval(x, y, width, height);
            }
        }
    }
}
