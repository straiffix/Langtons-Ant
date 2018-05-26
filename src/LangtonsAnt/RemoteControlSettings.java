package LangtonsAnt;

import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class RemoteControlSettings {
    public static void showWindow(){
        Pane root = new Pane();
        Stage stage = new Stage();
        stage.setTitle("Ustawienia zdalnego sterowania");
        stage.setResizable(false);
        stage.setIconified(false);
        stage.setScene(new Scene(root, 360, 360));
        stage.initModality(Modality.APPLICATION_MODAL);

        //kontrolki
        VBox box = new VBox();

        CheckBox isRemoteControlEnabled = new CheckBox();
        isRemoteControlEnabled.selectedProperty().bindBidirectional(SimulatorGlobal.isRemoteControlEnabled);
        box.getChildren().add(generateSettingsControl("Włącz zdalne sterowanie", isRemoteControlEnabled));


        CheckBox isClient = new CheckBox();
        SimulatorGlobal.isClient.bind(isClient.selectedProperty());
        isClient.disableProperty().bind(SimulatorGlobal.isRemoteControlEnabled.not());
        box.getChildren().add(generateSettingsControl("Ta aplikacja będzie służyła do kontroli symulacji:", isClient));

        TextField serverAddress = new TextField();
        SimulatorGlobal.addressOfSecondComputer.bind(serverAddress.textProperty());
        serverAddress.setDisable(!SimulatorGlobal.isRemoteControlEnabled.getValue());
        serverAddress.disableProperty().bind(SimulatorGlobal.isRemoteControlEnabled.not());
        box.getChildren().add(generateSettingsControl("Adres drugiego komputera:", serverAddress));

        TextField serverToClientPort = new TextField();
        serverToClientPort.textProperty().bindBidirectional(SimulatorGlobal.serverToClientPort, new NumberStringConverter());
        serverToClientPort.disableProperty().bind(SimulatorGlobal.isRemoteControlEnabled.not());
        box.getChildren().add(generateSettingsControl("Numer portu komunikacji serwer->klient:", serverToClientPort));

        TextField clientToServerPort = new TextField();
        clientToServerPort.textProperty().bindBidirectional(SimulatorGlobal.clientToServerPort, new NumberStringConverter());
        clientToServerPort.disableProperty().bind(SimulatorGlobal.isRemoteControlEnabled.not());
        box.getChildren().add(generateSettingsControl("Numer portu komunikacji klient->server:", clientToServerPort));

        root.getChildren().add(box);
        stage.showAndWait();

    }

    static private Pane generateSettingsControl(String name, Node control) {
        VBox settingsGroup = new VBox();
        Text label = new Text(name);
        settingsGroup.getChildren().add(label);
        settingsGroup.getChildren().add(control);

        return settingsGroup;
    }

    static private Pane generateSettingsControl(StringProperty nameToBinding, Node control) {
        VBox settingsGroup = new VBox();
        Text label = new Text();
        label.textProperty().bind(nameToBinding);
        settingsGroup.getChildren().add(label);
        settingsGroup.getChildren().add(control);

        return settingsGroup;
    }
}
