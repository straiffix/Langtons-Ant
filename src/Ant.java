import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Timer;
import java.util.TimerTask;


public class Ant {


    int SIZE = 100; // Size of field
    int STEPS = 11000;
    int SPEED = 10; // 2 - fast, 10 - medium, 30 - slow, 60 - very slow;


    Rectangle[][] grid;

    private int antX;
    private int antY;
    private int direction;
    private Color antColor;
    private int testSteps = 0;

    public Ant(int antX, int antY, int direction, Color antColor, Rectangle[][] grid) {

        this.antX = antX;
        this.antY = antY;
        this.direction = direction;
        this.antColor = antColor;
        this.grid = grid;

    }

    private void repaint(int x, int y, Color color) {
        grid[x][y].setFill(color);
    } // Change color

    private void antGo() { //Change coordinate

        if (direction == 0) {
            antY++;
        } else if (direction == 1) {
            antX++;
        } else if (direction == 2) {
            antY--;
        } else {
            antX--;
        }

    }

    private void step() {

        if (grid[antX][antY].getFill().equals(Color.WHITE)) {
            if (direction != 3) {  //Change direction
                direction++;
            } else {
                direction = 0;
            }

            repaint(antX, antY, antColor); // Change color
            antGo(); //Change place

        } else {
            if (direction != 0) {
                direction--;
            } else {
                direction = 3;
            }

            repaint(antX, antY, Color.WHITE);
            antGo();

        }


        testSteps++;

    }

    public void setAnt() {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

                           @Override
                           public void run() {
                               try {
                                   step();
                                   if (testSteps >= STEPS || antX <= 0 || antX >= SIZE || antY <= 0 || antY >= SIZE) {
                                       timer.cancel();
                                   }
                               } catch (ArrayIndexOutOfBoundsException e) {
                                   timer.cancel();
                               }
                               if (!SimulatorGlobal.simulateStopped.get()) {
                                   setAnt();
                               }
                               timer.cancel();

                           }
                       }

                , 600 - SimulatorGlobal.simulateSpeed.get(), 1);

    }


}
