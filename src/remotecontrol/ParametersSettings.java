package remotecontrol;

import java.io.Serializable;

public class ParametersSettings implements Serializable {
    public ParametersSettingsType type;
    public int width;
    public int height;
    public int sizeOfCell;
    public int speedOfAnimation;
    public SerializableColor colorOfNewAnt;
    public boolean isRunning;

    // dla nowej mrówki
    public int newAntX;
    public int newAntY;
    public int newAntDirection;

    // dla zwrócenia tablicy
    public SerializableColor[][] returnBoard;
}
