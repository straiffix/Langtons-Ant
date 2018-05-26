package LangtonsAnt;

import javafx.scene.canvas.GraphicsContext;
import remotecontrol.SerializableColor;

import java.util.Timer;
import java.util.TimerTask;


public class Ant {


    int SIZE = 100; // Size of field
    int STEPS = 11000;
    int SPEED = 10; // 2 - fast, 10 - medium, 30 - slow, 60 - very slow;


    SerializableColor[][] grid;
    GraphicsContext gc;

    private int antX;
    private int antY;
    private int direction;
    private SerializableColor antColor;
    private int testSteps = 0;
    private Console console;

    public Ant(int antX, int antY, int direction, SerializableColor antColor, SerializableColor[][] grid, GraphicsContext gc, Console console) {
        this.antX = antX;
        this.antY = antY;
        this.direction = direction;
        this.antColor = antColor;
        this.grid = grid;
        this.gc = gc;
        this.console=console;
    }

    private void repaint(int x, int y, SerializableColor color) {
            grid[antX][antY] = color;
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

        if (grid[antX][antY].isWhite()) {
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

            repaint(antX, antY, new SerializableColor(1,1,1));
            antGo();

        }


        testSteps++;

    }

    public void setAnt() {

        String message = "Ant died!\n";

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

                           @Override
                           public void run() {
                                   try {
                                       step();
                                       if (testSteps >= STEPS || antX <= 0 || antX >= SIZE || antY <= 0 || antY >= SIZE) {

                                           timer.cancel();
                                           timer.purge();


                                       }
                                   } catch (ArrayIndexOutOfBoundsException e) {
                                       timer.cancel();
                                       timer.purge();

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
