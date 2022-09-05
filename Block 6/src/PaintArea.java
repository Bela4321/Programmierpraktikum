import javax.swing.*;
import java.awt.*;

public class PaintArea extends JPanel {
    private Turtle turtle;

    public PaintArea() {
        this.turtle = new Turtle(0, 0, 0);
        setSize(1060, 720);
        setBackground(Color.WHITE);
    }

    public Turtle getTurtle() {
        return turtle;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.turtle.paint(g);
    }
}
