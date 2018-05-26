package LangtonsAnt;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import remotecontrol.ParametersSettings;
import remotecontrol.ParametersSettingsType;
import remotecontrol.RemoteControl;
import remotecontrol.SerializableColor;
import translations.*;

import java.io.Console;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Main extends Application {

    public static SerializableColor[][] grid; //Fields
    Canvas canvasForAnts = new Canvas();
    static final int NUMBER_OF_CELLS = 100;
    Spinner<Integer> widthSpinner = new Spinner<>();
    Spinner<Integer> heightSpinner = new Spinner<>();
    Slider animationSpeed = new Slider(0, 600, 300);
    Slider cellSizeSlider = new Slider(1,13,10);
    ComboBox<TranslationBase> languagesOfInterface = new ComboBox<>();

    RemoteControl remoteControlClient;
    RemoteControl remoteControlServer;

    LangtonsAnt.Console console;

    public static List<Ant> ants = new ArrayList<Ant>();
    TranslateController currentLanguage = new TranslateController();

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(final Stage primaryStage) {
        primaryStage.titleProperty().bind(currentLanguage.appName);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);
        primaryStage.setResizable(false);
        Pane root = new Pane();
        SplitPane splitPane = new SplitPane();
        splitPane.prefHeightProperty().bind(root.heightProperty());
        splitPane.prefWidthProperty().bind(root.widthProperty());
        root.getChildren().add(splitPane);

        ScrollPane scrollHeadPane = new ScrollPane();
        Pane headPane = new Pane();
        headPane.getChildren().add(canvasForAnts);

        scrollHeadPane.setContent(headPane);

        GraphicsContext refreshGraphicsContext = canvasForAnts.getGraphicsContext2D();

        ScrollPane scrollSettingsPane = new ScrollPane();
        VBox settingsPane = new VBox();
        scrollSettingsPane.setContent(settingsPane);
        scrollSettingsPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        generateInfoPanel(settingsPane, scrollSettingsPane, primaryStage);
        generateSettingsControls(settingsPane, scrollSettingsPane, headPane, refreshGraphicsContext);

         /** Logs */
         TextArea logText = new TextArea();
         logText.setPrefSize(200, 100);
        LangtonsAnt.Console console = new LangtonsAnt.Console(logText);
        PrintStream ps = new PrintStream(console, true);
        System.setOut(ps);
        System.setErr(ps);

        settingsPane.getChildren().add(logText);

         /** */


        splitPane.getItems().addAll(scrollHeadPane, scrollSettingsPane);
        splitPane.setDividerPositions(0.7f);


        canvasForAnts.setOnMousePressed(event -> {
            if (!SimulatorGlobal.simulateStopped.get()) {
                int randomDirection = (int) (Math.random()*4);
                if(SimulatorGlobal.isRemoteControlEnabled.getValue() && SimulatorGlobal.isClient.getValue()) {
                    remoteControlClient.sendSettings((int) (event.getX() / SimulatorGlobal.cellSize.get()),
                              (int) (event.getY() / SimulatorGlobal.cellSize.get()),
                              SimulatorGlobal.colorOfNextAnt,
                              0);
                }else {
                    Ant ant = new Ant((int) (event.getX() / SimulatorGlobal.cellSize.get()),
                              (int) (event.getY() / SimulatorGlobal.cellSize.get()),
                              randomDirection,
                              new SerializableColor(SimulatorGlobal.colorOfNextAnt),
                              this.grid,
                              canvasForAnts.getGraphicsContext2D(), console);

                    ants.add(ant);
                    ant.setAnt();
                    String message = "New ant | Color " + SimulatorGlobal.colorOfNextAnt.toString() + " | Behavior  " + randomDirection + " | add at "
                              + event.getX() + ";" + event.getY() + "\n";
                    for (char c : message.toCharArray()){
                        console.write(c);
                    }
                }
            }
        });


        Scene scene = new Scene(root, 600, 600, Color.WHITE); //Width = height = size of cell * number of cells

        primaryStage.setScene(scene);
        primaryStage.show();

        Timer drawingTimer = new Timer();
        drawingTimer.schedule(new TimerTask() {
            @Override
            public void run () {
                Platform.runLater(()->{
                    if(!(SimulatorGlobal.isRemoteControlEnabled.getValue() && SimulatorGlobal.isClient.getValue())){
                        Board.drawBoard(grid,refreshGraphicsContext,widthSpinner.getValue(), heightSpinner.getValue());
                    }else{
                        Board.drawBoard(grid,refreshGraphicsContext,widthSpinner.getValue(), heightSpinner.getValue());
                    }

                });
            }
        },0,50);

        Timer serverSendTimer = new Timer();
        serverSendTimer.schedule(new TimerTask() {
            @Override
            public void run () {
                Platform.runLater(()->{
                  //  try{
                        if(SimulatorGlobal.isRemoteControlEnabled.getValue() && !SimulatorGlobal.isClient.getValue() && grid != null && remoteControlClient!=null){
                            ParametersSettings sett  = new ParametersSettings();
                            sett.returnBoard = grid;

                            sett.type = ParametersSettingsType.RETURN_BOARD;
                            if(!remoteControlClient.isBusy){
                                remoteControlClient.sendSettings(sett);
                            }
                           }
                  //  }catch(Exception ex){
//System.out.println("Board error: " + ex.toString());
  //                  }

                });
            }
        },0,500);

        // dodawanie języków do listy
        languagesOfInterface.getItems().add(new EnglishLanguage());
        languagesOfInterface.getItems().add(new PolishLanguage());
        languagesOfInterface.getItems().add(new RussianLanguage());

        languagesOfInterface.setValue(new PolishLanguage());
        languagesOfInterface.getOnAction().handle(new ActionEvent());

        //connectionTest(refreshGraphicsContext);
        ps.close();
    }

    private void createBoard(Pane headPane, int width, int height) {
        this.grid = new SerializableColor[height][width]; //Init field

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = new SerializableColor(1,1,1);
            }
        }

        canvasForAnts.widthProperty().bind(SimulatorGlobal.cellSize.multiply(width));
        canvasForAnts.heightProperty().bind(SimulatorGlobal.cellSize.multiply(height));
    }

    private  void generateInfoPanel(Pane settingsPane, ScrollPane scrollSettingsPane, Stage primaryStage){
                    VBox infoPane = new VBox();
                    infoPane.setAlignment(Pos.CENTER);
                    infoPane.prefWidthProperty().bind(scrollSettingsPane.widthProperty().divide(2));
                    infoPane.prefHeightProperty().bind(scrollSettingsPane.heightProperty().divide(6));
                    settingsPane.getChildren().add(infoPane);

                    HBox title = new HBox();
                    Image titleImage = new Image("LangtonsAnt/langtons.png");
                    ImageView titleImageView = new ImageView(titleImage);
                    title.setAlignment(Pos.CENTER);
                    title.getChildren().add(titleImageView);
                    infoPane.getChildren().add(title);


                    Button infoButton = new Button();
                    Image buttonImage = new Image("LangtonsAnt/info.png");
                    infoButton.setGraphic(new ImageView(buttonImage));
                    infoButton.setAlignment(Pos.TOP_CENTER);

                    About about = new About(primaryStage, currentLanguage);

                    infoButton.setOnAction(new EventHandler<ActionEvent>() {
                              @Override
                              public void handle(ActionEvent event) {
                                        about.showInfoWindow();
                              }
                    });

                    infoPane.getChildren().add(infoButton);

          }

    private void generateSettingsControls(Pane settingsPane, ScrollPane scrollSettingsPane, Pane headPane, GraphicsContext gc){

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

        Button remoteControlSettingsButton = new Button("Ustawienia zdalnego sterowania...");
        settingsPane.getChildren().add(generateSettingsControl("", remoteControlSettingsButton));
        remoteControlSettingsButton.prefWidthProperty().bind(scrollSettingsPane.widthProperty());
        remoteControlSettingsButton.setOnAction((event) -> {
            RemoteControlSettings.showWindow();
            if(SimulatorGlobal.isRemoteControlEnabled.getValue()){
                if(SimulatorGlobal.isClient.getValue()){
                    remoteControlClient = new RemoteControl(SimulatorGlobal.addressOfSecondComputer.getValue(),
                            SimulatorGlobal.clientToServerPort.getValue(), true, grid, gc, ants, console);

                    remoteControlServer = new RemoteControl(null, SimulatorGlobal.serverToClientPort.getValue(),
                            false,
                            grid,
                            gc,
                            ants, console);
                    remoteControlServer.start();
                }else{
                    remoteControlClient = new RemoteControl(SimulatorGlobal.addressOfSecondComputer.getValue(),
                            SimulatorGlobal.serverToClientPort.getValue(), true, grid, gc, ants, console);

                    remoteControlServer = new RemoteControl(null, SimulatorGlobal.clientToServerPort.getValue(),
                            false,
                            grid,
                            gc,
                            ants, console);
                    remoteControlServer.start();
                }
            }
        });

        Button sendToServer = new Button("Wyślij aktualne ustawienia do serwera");
        settingsPane.getChildren().add(generateSettingsControl("", sendToServer));
        sendToServer.prefWidthProperty().bind(scrollSettingsPane.widthProperty());
        sendToServer.setOnAction((event) -> {

            remoteControlClient.sendSettings(ParametersSettingsType.SIMULATOR_GLOBAL_SETTINGS);
        });

        // kontrolki ustawień
        // język
        settingsPane.getChildren().add(generateSettingsControl(currentLanguage.languageSetting, languagesOfInterface));
        languagesOfInterface.prefWidthProperty().bind(scrollSettingsPane.widthProperty());
        languagesOfInterface.setOnAction((event)->{
            languagesOfInterface.getValue().refreshLanguage(currentLanguage);
        });

        // szerokość
        widthSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 6000, 100));
        widthSpinner.prefWidthProperty().bind(scrollSettingsPane.widthProperty());
        widthSpinner.disableProperty().bind(SimulatorGlobal.simulateStopped.not());
        widthSpinner.setEditable(true);
        settingsPane.getChildren().add(generateSettingsControl(currentLanguage.boardWidthSetting, widthSpinner));

        // wysokość
        heightSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 6000, 100));
        heightSpinner.prefWidthProperty().bind(scrollSettingsPane.widthProperty());
        heightSpinner.disableProperty().bind(SimulatorGlobal.simulateStopped.not());

        heightSpinner.setEditable(true);
        settingsPane.getChildren().add(generateSettingsControl(currentLanguage.boardHeightSetting, heightSpinner));

        // prędkość animacji
        animationSpeed.prefWidthProperty().bind(scrollSettingsPane.widthProperty());
animationSpeed.valueProperty().bindBidirectional(SimulatorGlobal.simulateSpeed);
        settingsPane.getChildren().add(generateSettingsControl(currentLanguage.speedSetting, animationSpeed));

        // rozmiar komórki
        cellSizeSlider.prefWidthProperty().bind(scrollSettingsPane.widthProperty());
        cellSizeSlider.valueProperty().bindBidirectional(SimulatorGlobal.cellSize);
        settingsPane.getChildren().add(generateSettingsControl(currentLanguage.cellSizeSetting, cellSizeSlider));

        // kolor
        ColorPicker colorOfAntSettings = new ColorPicker();
        colorOfAntSettings.prefWidthProperty().bind(scrollSettingsPane.widthProperty());
        colorOfAntSettings.setValue(Color.BLACK);
        colorOfAntSettings.setOnHidden((event)->{
            SimulatorGlobal.colorOfNextAnt = colorOfAntSettings.getValue();

        });
        settingsPane.getChildren().add(generateSettingsControl(currentLanguage.colorSetting, colorOfAntSettings));

    }

    private Pane generateSettingsControl(String name, Control control) {
        VBox settingsGroup = new VBox();
        Text label = new Text(name);
        settingsGroup.getChildren().add(label);
        settingsGroup.getChildren().add(control);

        return settingsGroup;
    }

    private Pane generateSettingsControl(StringProperty nameToBinding, Control control) {
        VBox settingsGroup = new VBox();
        Text label = new Text();
        label.textProperty().bind(nameToBinding);
        settingsGroup.getChildren().add(label);
        settingsGroup.getChildren().add(control);

        return settingsGroup;
    }

    private void  connectionTest(GraphicsContext gc){
        // tworzymy serwer
        RemoteControl serwer = new RemoteControl("//this is not used", 6666, false, grid, gc, ants, console);
        serwer.start();

        // tworzymy klienta (aplikację któa będzie sterować naszą główną);
        RemoteControl client = new RemoteControl("localhost", 6666, true, grid, gc, ants, console);
        client.start();

        // tworzymy przykładowe ustawienia i je wysyłamy
        ParametersSettings sampleSettings = new ParametersSettings();
        sampleSettings.type = ParametersSettingsType.SIMULATOR_GLOBAL_SETTINGS;
        sampleSettings.height=33;
        sampleSettings.width=22;
        sampleSettings.sizeOfCell=1;
        sampleSettings.speedOfAnimation=500;
        client.sendSettings(sampleSettings);

    }
}