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
        Scene scene = new Scene(root, 1000, 1000, Color.WHITE);
        final Color WHITE = Color.WHITE;
        final Color BLACK = Color.BLACK;
        grid = new Rectangle[100][100];
        int x = 0, y = 0;
        for (int i=0; i<100;i++) {
            for (int j=0; j<100; j++) {
               grid[i][j] = new Rectangle();
               grid[i][j].setFill(WHITE);
               grid[i][j].setStroke(BLACK);
               grid[i][j].setStrokeWidth(.8);
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
        Automate langton2 = new Automate(70, 25, 0, Color.RED, grid);
        langton2.langtonAnt();
        Automate langton3 = new Automate(25, 70, 1, Color.BLUE, grid);
        langton3.langtonAnt();
        Automate langton4 = new Automate(50, 50, 3, Color.LIGHTCORAL, grid);
        langton4.langtonAnt();








    }


}