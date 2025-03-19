package paint.shapes;

import java.awt.*;

public  abstract class Shape {

    protected int startX, startY, endX, endY;
    protected Color color = Color.black;
    boolean isFill = false;
    boolean isDott = false;

    public abstract void draw(Graphics g);

    public void setStartX(int startX) {
        this.startX = startX;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setFill(boolean isFill) {
        this.isFill = isFill;
    }

    public void setDott(boolean isDott) {
        this.isDott = isDott;
    }
}
