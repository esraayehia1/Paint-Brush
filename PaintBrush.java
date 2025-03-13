package paint;


import javax.swing.*;
import java.awt.*;

public class PaintBrush extends JFrame {

    public static void main(String[] args) {

        JFrame frame = new JFrame();

        Panel paintpanel = new Panel();
        frame.setContentPane(paintpanel);
        frame.setTitle("Paint");
        frame.setSize(800, 800);
        frame.setVisible(true);
        paintpanel.setFocusable(true);
        paintpanel.requestFocusInWindow();

    }
}
