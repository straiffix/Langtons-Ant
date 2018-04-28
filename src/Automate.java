import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Timer;
import java.util.TimerTask;


public class Automate {
    static final int SIZE = 100;
    static final int STEPS=11000;

    Rectangle[][] grid;
    private int currentX;
    private int currentY;
    private int direction;
    private Color antColor;
    private int testSteps=0;

    public Automate(int currentX, int currentY, int direction, Color antColor, Rectangle[][] grid){

        this.currentX=currentX;
        this.currentY=currentY;
        this.direction = direction;
        this.antColor=antColor;
        this.grid=grid;

    }

    private void repaint(int x, int y, Color color){
        grid[x][y].setFill(color);
    }

    private void antGo(){

        if (direction == 0) {
            currentY++;
        } else if (direction == 1) {
            currentX++;
        } else if (direction == 2) {
            currentY--;
        } else {
            currentX--;
        }

    }

    private void step(){

                if (grid[currentX][currentY].getFill().equals(Color.WHITE)) {
                    if (direction != 3) {
                        direction++;
                    } else { direction = 0;}

                    repaint(currentX, currentY, antColor);
                    grid[currentX][currentY].setStroke(Color.BLACK);

                    antGo();

                } else {
                    if (direction != 0) {
                        direction--;
                    } else { direction = 3;}

                    repaint(currentX, currentY, Color.WHITE);
                    grid[currentX][currentY].setStroke(Color.BLACK);
                    antGo();

                }

                grid[currentX][currentY].setStroke(Color.RED);
                testSteps++;

    }

    public void langtonAnt(){

        Timer timer = new Timer();
        timer.schedule(new TimerTask(){

                    @Override
                    public void run(){
                        step();
                        if(testSteps>=STEPS || currentX<=0 || currentX >= SIZE || currentY <= 0 || currentY >= SIZE){ timer.cancel(); }
                    }

                }, 1000, 5);

        }


}
