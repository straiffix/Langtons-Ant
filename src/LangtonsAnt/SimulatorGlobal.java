package LangtonsAnt;

import javafx.beans.property.*;
import javafx.scene.paint.Color;

public class SimulatorGlobal {
    static final public BooleanProperty simulateStopped= new SimpleBooleanProperty(true);
    static final public IntegerProperty simulateSpeed = new SimpleIntegerProperty(300);
    static final public IntegerProperty cellSize = new SimpleIntegerProperty(10);
    static final public IntegerProperty width = new SimpleIntegerProperty(100);
    static final public IntegerProperty height = new SimpleIntegerProperty(100);

    static public Color colorOfNextAnt = Color.RED;

    // ustawienia sterowania zdalnego
    static final public BooleanProperty isRemoteControlEnabled = new SimpleBooleanProperty(false);
    static final public BooleanProperty isClient = new SimpleBooleanProperty(true);
    static final public StringProperty addressOfSecondComputer = new SimpleStringProperty("localhost");
    static final public IntegerProperty clientToServerPort = new SimpleIntegerProperty(6666);
    static final public IntegerProperty serverToClientPort = new SimpleIntegerProperty(6667);
}
