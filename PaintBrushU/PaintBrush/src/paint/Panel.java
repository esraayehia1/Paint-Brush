package paint;

import java.awt.BorderLayout;
import javax.swing.JToolBar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;

import paint.shapes.*;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.util.ArrayList;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.GridLayout;

public class Panel extends JPanel {

    private Shape currentShape;
    private Color currentColor = Color.YELLOW; // Default color
    private Pencil pencil = new Pencil();
    private ArrayList<Shape> shapes = new ArrayList<>();
    private Cursor eraserCursor;

    public Panel() {
        setBackground(Color.white);
        setLayout(new BorderLayout());

        // Top toolbar for shapes and actions
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        add(toolbar, BorderLayout.NORTH);

        // Buttons for shapes
        JButton lineButton = new JButton("Line");
        JButton rectangleButton = new JButton("Rectangle");
        JButton ovalButton = new JButton("Oval");
        JButton freehandButton = createButton("Pen", "Image/pencil.jpg");
        JButton eraserButton = createButton("Erase", "Image/erase.jpg");
        JButton undoButton = createButton("Undo", "Image/undo.jpg");
        JButton clearButton = createButton("Clear", "Image/clear.jpg");

        toolbar.add(lineButton);
        toolbar.add(rectangleButton);
        toolbar.add(ovalButton);
        toolbar.add(freehandButton);
        toolbar.add(eraserButton);
        toolbar.add(undoButton);
        toolbar.add(clearButton);

        // Right-side panel for colors and checkboxes
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.lightGray);
        add(rightPanel, BorderLayout.EAST);

        JLabel colorLabel = new JLabel("Colors");
        colorLabel.setForeground(Color.WHITE);
        colorLabel.setFont(new Font("Arial", Font.BOLD, 18));
        colorLabel.setAlignmentX(CENTER_ALIGNMENT);
        rightPanel.add(colorLabel);

        // Color buttons
        JPanel colorPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        colorPanel.setBackground(Color.DARK_GRAY);
        rightPanel.add(colorPanel);

        JButton redButton = createColorButton(Color.RED);
        JButton blueButton = createColorButton(Color.BLUE);
        JButton greenButton = createColorButton(Color.GREEN);
        JButton yellowButton = createColorButton(Color.YELLOW);
        JButton pinkButton = createColorButton(Color.PINK);
        JButton blackButton = createColorButton(Color.BLACK);

        colorPanel.add(redButton);
        colorPanel.add(blueButton);
        colorPanel.add(greenButton);
        colorPanel.add(yellowButton);
        colorPanel.add(pinkButton);
        colorPanel.add(blackButton);


        // Checkboxes
        Checkbox chk1 = new Checkbox("Solid", false);
        chk1.setForeground(Color.WHITE);
        Checkbox chk2 = new Checkbox("Dotted", false);
        chk2.setForeground(Color.WHITE);

        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new GridLayout(2, 1, 5, 5));
        checkboxPanel.setBackground(Color.DARK_GRAY);
        checkboxPanel.add(chk1);
        checkboxPanel.add(chk2);
        rightPanel.add(checkboxPanel);

        // Custom cursor for the eraser
        Image cursorImage = createCursorImage();
        eraserCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(0, 0), "Eraser");

        // Button actions
        lineButton.addActionListener(e -> setCurrentShape(new Line()));
        rectangleButton.addActionListener(e -> setCurrentShape(new Rectangle()));
        ovalButton.addActionListener(e -> setCurrentShape(new Oval()));
        freehandButton.addActionListener(e -> setCurrentShape(pencil));
        eraserButton.addActionListener(e -> setCurrentShape(new Eraser()));
        undoButton.addActionListener(e -> {
            if (!shapes.isEmpty()) {
                shapes.remove(shapes.size() - 1);
                repaint();
            }
        });
        clearButton.addActionListener(e -> {
            shapes.clear();
            pencil.clearPoints();
            repaint();
        });

        redButton.addActionListener(e -> setCurrentColor(Color.RED));
        blueButton.addActionListener(e -> setCurrentColor(Color.BLUE));
        greenButton.addActionListener(e -> setCurrentColor(Color.GREEN));
        yellowButton.addActionListener(e -> setCurrentColor(Color.YELLOW));
        pinkButton.addActionListener(e -> setCurrentColor(Color.PINK));
        blackButton.addActionListener(e -> setCurrentColor(Color.black));


        chk1.addItemListener(e -> {
            if (currentShape != null) {
                currentShape.setFill(chk1.getState());
                repaint();
            }
        });

        chk2.addItemListener(e -> {
            if (currentShape != null) {
                currentShape.setDott(chk2.getState());
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (currentShape != null) {
                    currentShape.setStartX(e.getX());
                    currentShape.setStartY(e.getY());
                    currentShape.setColor(currentColor);
                    currentShape.setFill(chk1.getState());
                    currentShape.setDott(chk2.getState());
                    if (currentShape instanceof Pencil) {
                        ((Pencil) currentShape).addPoint(e.getX(), e.getY());
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (currentShape != null) {
                    currentShape.setEndX(e.getX());
                    currentShape.setEndY(e.getY());
                    shapes.add(currentShape);

                    // Reset shape for next drawing
                    if (currentShape instanceof Line) currentShape = new Line();
                    else if (currentShape instanceof Rectangle) currentShape = new Rectangle();
                    else if (currentShape instanceof Oval) currentShape = new Oval();
                    else if (currentShape instanceof Pencil) currentShape = new Pencil();
                    else if (currentShape instanceof Eraser) currentShape = new Eraser();

                    repaint();
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (currentShape != null) {
                    if (currentShape instanceof Pencil) {
                        ((Pencil) currentShape).addPoint(e.getX(), e.getY());
                    } else if (currentShape instanceof Eraser) {
                        ((Eraser) currentShape).addPoint(e.getX(), e.getY());
                    } else {
                        currentShape.setEndX(e.getX());
                        currentShape.setEndY(e.getY());
                    }
                    repaint();
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Shape shape : shapes) {
            shape.draw(g);
        }
        if (currentShape != null) {
            currentShape.draw(g);
        }
    }

    public void setCurrentShape(Shape shape) {
        currentShape = shape;
        currentShape.setColor(currentColor);
        if (currentShape instanceof Eraser) {
            setCursor(eraserCursor);
        } else {

            setCursor(Cursor.getDefaultCursor());
        }
    }

    public void setCurrentColor(Color color) {
        currentColor = color;
    }

    private static JButton createButton(String buttonText, String iconFileName) {
        JButton button = new JButton(buttonText);
        ImageIcon icon = new ImageIcon(iconFileName);
        Image scaledImage = icon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledImage));
        return button;
    }

    private JButton createColorButton(Color color) {
        JButton button = new JButton();
        button.setBackground(color);
        button.setOpaque(true);
        button.setBorderPainted(false);
        return button;
    }

    private Image createCursorImage() {
        BufferedImage cursorImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = cursorImage.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 16, 16);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(0, 0, 15, 15);
        g2d.dispose();
        return cursorImage;
    }
}