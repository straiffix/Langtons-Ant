import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class SimulatorGlobal {
    static final public BooleanProperty simulateStopped= new SimpleBooleanProperty(true);
    static final public IntegerProperty simulateSpeed = new SimpleIntegerProperty(300);
}
