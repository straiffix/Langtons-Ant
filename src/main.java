import javafx.animation.Animation;
import javafx.animation.FillTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Collection;



public class  main extends Application {



          public static void main(String[] args) {

        Application.launch(args);}


    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("Animation");
        Group root = new Group();
        Scene scene = new Scene(root, 800, 800, Color.WHITE);
              final Color WHITE = Color.WHITE;
              final Color BLACK = Color.BLACK;

              final Rectangle [][] grid = new Rectangle[100][100];
              int x = 0, y = 0;
              for (int i=0; i<100;i++) {
                        for (int j=0; j<100; j++) {
                                  grid[i][j] = new Rectangle();
                                  grid[i][j].setFill(WHITE);
                                  grid[i][j].setStroke(BLACK);
                                  grid[i][j].setStrokeWidth(.5);
                                  grid[i][j].setX(x);
                                  grid[i][j].setY(y);
                                  grid[i][j].setWidth(8);
                                  grid[i][j].setHeight(8);
                                  x+=8;
                                  root.getChildren().add(grid[i][j]);
                        }
                        y+=8;
                        x=0;
              }

        primaryStage.setScene(scene);

        primaryStage.show();



        Timeline timeLine = new Timeline();
        Collection<KeyFrame> frames = timeLine.getKeyFrames();
        Duration frameGap = Duration.millis(5);
        Duration frameTime = Duration.ZERO ;

              Automat langton = new Automat();

              int steps=langton.STEPS;
        for(int i=0; i<steps; i++){




                    final int currentX=langton.archive[i].getX();
                final int currentY=langton.archive[i].getY();
                final Color currentColor=langton.archive[i].getColor();
                frameTime = frameTime.add(frameGap);

                frames.add(new KeyFrame(frameTime, e -> {

                                      grid[currentX][currentY].setFill(currentColor);

                  }));



        }




        timeLine.setCycleCount(1);
        timeLine.play();


    }


}