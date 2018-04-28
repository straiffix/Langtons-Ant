import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class  main extends Application {

    Rectangle [][] grid;

    public static void main(String[] args) { Application.launch(args);}
        
    @Override
    public void start(final Stage primaryStage) {
        primaryStage.setTitle("Animation");
        Group root = new Group();
        Scene scene = new Scene(root, 500, 500, Color.WHITE);
        final Color WHITE = Color.WHITE;
        final Color BLACK = Color.BLACK;
        grid = new Rectangle[50][50];
        int x = 0, y = 0;
        for (int i=0; i<50;i++) {
            for (int j=0; j<50; j++) {
               grid[i][j] = new Rectangle();
               grid[i][j].setFill(WHITE);
               grid[i][j].setStroke(BLACK);
               grid[i][j].setStrokeWidth(.5);
               grid[i][j].setX(x);
               grid[i][j].setY(y);
               grid[i][j].setWidth(10);
               grid[i][j].setHeight(10);
               x+=10;
               root.getChildren().add(grid[i][j]);

               }
            y+=10;
            x=0;
         }

        primaryStage.setScene(scene);

        primaryStage.show();


        Automate langton = new Automate(25, 25, 2, Color.BLACK, grid);
        langton.langtonAnt();









    }


}