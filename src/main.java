import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class  main extends Application {

    Rectangle [][] grid; //Field
    static final int NUMBER_OF_CELLS = 100;
    static final int CELL_SIZE=10;

    public static void main(String[] args) { Application.launch(args);}
        
    @Override
    public void start(final Stage primaryStage) {

        primaryStage.setTitle("Langton's Ant");
        Group root = new Group();
        Scene scene = new Scene(root, 1000, 1000, Color.WHITE); //Width = height = size of cell * number of cells

        grid = new Rectangle[NUMBER_OF_CELLS][NUMBER_OF_CELLS]; //Init field

        int x = 0, y = 0;
        for (int i=0; i<NUMBER_OF_CELLS;i++) {
            for (int j=0; j<NUMBER_OF_CELLS; j++) {
               grid[i][j] = new Rectangle();
               grid[i][j].setFill(Color.WHITE);
               grid[i][j].setStroke(Color.BLACK);
               grid[i][j].setStrokeWidth(.8);
               grid[i][j].setX(x);
               grid[i][j].setY(y);
               grid[i][j].setWidth(CELL_SIZE);
               grid[i][j].setHeight(CELL_SIZE);
               x+=CELL_SIZE;
               root.getChildren().add(grid[i][j]);

               }
            y+=CELL_SIZE;
            x=0;
         }

        primaryStage.setScene(scene);
        primaryStage.show();

        Ant langton = new Ant(25, 25, 2, Color.GREEN, grid);
        langton.setAnt();
        Ant langton2 = new Ant(70, 70, 0, Color.RED, grid);
        langton2.setAnt();
        Ant langton3 = new Ant(50, 50, 1, Color.BLUE, grid);
        langton3.setAnt();

    }

}