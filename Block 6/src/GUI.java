import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    public GUI() {
        setTitle("GUI");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1280, 720);
        setLayout(new BorderLayout());


        PaintArea paintArea = new PaintArea();
        paintArea.setPreferredSize(new Dimension(1060, 720));


        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.PAGE_AXIS));
        textPanel.setPreferredSize(new Dimension(200, 720));

        JTextArea textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(200, 680));
        textArea.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton button = new JButton("Send");
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textArea.getText();
                textArea.setText("");
                paintArea.getTurtle().interpret(text);
                paintArea.repaint();
            }
        };
        button.addActionListener(actionListener);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setSize(200, 40);


        textPanel.add(textArea);
        textPanel.add(button);

        this.add(paintArea, BorderLayout.WEST);
        this.add(textPanel, BorderLayout.EAST);
        this.setVisible(true);
    }


    public static void main(String[] args) {
        new GUI();
    }
}
