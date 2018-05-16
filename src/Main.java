import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    Rectangle[][] grid; //Field
    static final int NUMBER_OF_CELLS = 100;
    static final int CELL_SIZE = 10;
    Spinner<Integer> widthSpinner = new Spinner<>();
    Spinner<Integer> heightSpinner = new Spinner<>();
    Slider animationSpeed = new Slider(0, 600, 300);

    List<Ant> ants = new ArrayList<Ant>();

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {

        primaryStage.setTitle("Langton's Ant");
        Pane root = new Pane();
        SplitPane splitPane = new SplitPane();
        splitPane.prefHeightProperty().bind(root.heightProperty());
        splitPane.prefWidthProperty().bind(root.widthProperty());
        root.getChildren().add(splitPane);

        ScrollPane scrollHeadPane = new ScrollPane();
        Pane headPane = new Pane();
        scrollHeadPane.setContent(headPane);
        headPane.setOnMouseClicked(event -> {
            if (!SimulatorGlobal.simulateStopped.get()) {
                Ant ant = new Ant((int) (event.getX() / CELL_SIZE), (int) (event.getY() / CELL_SIZE), 0, Color.RED, this.grid);
                ants.add(ant);
                ant.setAnt();
            }
        });

        ScrollPane scrollSettingsPane = new ScrollPane();
        VBox settingsPane = new VBox();
        scrollSettingsPane.setContent(settingsPane);

        HBox startStopPane = new HBox();
        settingsPane.getChildren().add(startStopPane);

        Button startButton = new Button("Start");
        startStopPane.getChildren().add(startButton);
        startButton.prefWidthProperty().bind(scrollSettingsPane.widthProperty().divide(2));
        startButton.disableProperty().bind(SimulatorGlobal.simulateStopped.not());
        startButton.setOnAction((event) -> {
            SimulatorGlobal.simulateStopped.setValue(false);
            createBoard(headPane, widthSpinner.getValue(), heightSpinner.getValue());

        });

        Button stopButton = new Button("Stop");
        startStopPane.getChildren().add(stopButton);
        stopButton.disableProperty().bind(SimulatorGlobal.simulateStopped);
        stopButton.prefWidthProperty().bind(scrollSettingsPane.widthProperty().divide(2));
        stopButton.setOnAction((event) -> {
            SimulatorGlobal.simulateStopped.setValue(true);
        });


        // kontrolki ustawień
        // szerokość
        widthSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 6000, 100));
        widthSpinner.prefWidthProperty().bind(scrollSettingsPane.widthProperty());
        widthSpinner.disableProperty().bind(SimulatorGlobal.simulateStopped.not());
        settingsPane.getChildren().add(generateSettingsControl("Szerokość:", widthSpinner));

        // wysokość
        heightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 6000, 100));
        heightSpinner.prefWidthProperty().bind(scrollSettingsPane.widthProperty());
        heightSpinner.disableProperty().bind(SimulatorGlobal.simulateStopped.not());
        settingsPane.getChildren().add(generateSettingsControl("Wysokość:", heightSpinner));

        // prędkość animacji
        animationSpeed.prefWidthProperty().bind(scrollSettingsPane.widthProperty());
        SimulatorGlobal.simulateSpeed.bind(animationSpeed.valueProperty());
        settingsPane.getChildren().add(generateSettingsControl("Prędkość animacji:", animationSpeed));


        splitPane.getItems().addAll(scrollHeadPane, scrollSettingsPane);
        splitPane.setDividerPositions(0.7f);
        Scene scene = new Scene(root, 600, 600, Color.WHITE); //Width = height = size of cell * number of cells


        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void createBoard(Pane headPane, int width, int height) {
        headPane.getChildren().clear();
        this.grid = new Rectangle[height][width]; //Init field

        int x = 0, y = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = new Rectangle();
                grid[i][j].setFill(Color.WHITE);
                grid[i][j].setStroke(Color.BLACK);
                grid[i][j].setStrokeWidth(.8);
                grid[i][j].setX(x);
                grid[i][j].setY(y);
                grid[i][j].setWidth(CELL_SIZE);
                grid[i][j].setHeight(CELL_SIZE);
                x += CELL_SIZE;
                headPane.getChildren().add(grid[i][j]);
            }
            y += CELL_SIZE;
            x = 0;
        }
    }

    private Pane generateSettingsControl(String name, Control control) {
        VBox settingsGroup = new VBox();
        Text label = new Text(name);
        settingsGroup.getChildren().add(label);
        settingsGroup.getChildren().add(control);

        return settingsGroup;
    }
}