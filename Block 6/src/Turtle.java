import java.awt.*;
import java.util.ArrayList;

public class Turtle {
    private int x;
    private int y;
    private double angle;
    private boolean penDown;
    private Color color;

    private ArrayList<Linie> lines = new ArrayList<>();

    public Turtle(int x, int y, double angle) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.penDown = true;
        this.color = Color.BLACK;
    }

    public void setPenDown() {
        this.penDown = true;
    }
    public void setPenUp() {
        this.penDown = false;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void turn(double delta, boolean right) {
        if (right) {
            this.angle += delta;
        } else {
            this.angle -= delta;
        }
    }

    public void paint(Graphics g) {
        for (Linie line : this.lines) {
            line.paint(g);
        }
    }

    void moveTo(int x, int y) {
        if (this.penDown) {
            this.lines.add(new Linie(this.x, this.y, x, y, this.color));
        }
        this.x = x;
        this.y = y;
    }

    void goForward(int distance) {
        int x = (int) (this.x + distance * Math.cos(this.angle));
        int y = (int) (this.y + distance * Math.sin(this.angle));
        moveTo(x, y);
    }

    public void interpret(String text){
        String[] commands = text.split("\n");
        for (String command : commands) {
            String[] parts = command.split(" ");
            if (parts.length == 2&&parts[0].equals("pen")&&parts[1].equals("up")) {
                setPenUp();
            } else if (parts.length == 2&&parts[0].equals("pen")&&parts[1].equals("down")) {
                setPenDown();
            } else if (parts.length==3&&parts[0].equals("move")) {
                try {
                    int x = Integer.parseInt(parts[1]);
                    int y = Integer.parseInt(parts[2]);
                    moveTo(x, y);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid distance");
                }
            } else if (parts.length == 2 && parts[0].equals("go")){
                try {
                    int distance = Integer.parseInt(parts[1]);
                    goForward(distance);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid distance");
                }
            } else if (parts.length == 3 && parts[0].equals("turn")) {
                if (parts[1].equals("right")) {
                    try {
                        double angle = Double.parseDouble(parts[2]);
                        turn(angle, true);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid angle");
                    }
                } else if (parts[1].equals("left")) {
                    try {
                        double angle = Double.parseDouble(parts[2]);
                        turn(angle, false);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid angle");
                    }
                } else {
                    System.out.println("Invalid direction");
                }
            } else if (parts.length == 2 && parts[0].equals("color")) {
                try {
                    int number = Integer.decode(parts[1]);
                    this.color = new Color(number);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid color: "+parts[1]);
                }
            } else if (parts.length==2 &&parts[0].equals("kreis")) {
                try {
                    int radius = Integer.parseInt(parts[1]);
                    kreis(radius);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid radius");
                }
            } else {
                System.out.println("Invalid command, no match found");
            }
        }
    }

    public void kreis(int radius) {
        int x = this.x;
        int y = this.y;
        double angle = this.angle;
        setPenUp();
        moveTo(x + radius, y);
        setPenDown();
        for (int i = 0; i < 360; i++) {
            angle += Math.PI/180;
            int x1 = (int) (x + radius * Math.cos(angle));
            int y1 = (int) (y + radius * Math.sin(angle));
            moveTo(x1, y1);
        }
        setPenUp();
        moveTo(x, y);
        setPenDown();
    }
}

