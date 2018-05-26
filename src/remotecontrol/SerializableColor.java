package remotecontrol;

import javafx.scene.paint.Color;

import java.io.Serializable;

public class SerializableColor implements Serializable {
    private double r;
    private double g;
    private double b;

    public SerializableColor(int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public SerializableColor(Color color){
        this.r = color.getRed();
        this.g = color.getGreen();
        this.b = color.getBlue();
    }
    public Color getColor(){
        return new Color(this.r,this.g,this.b,1.0);
    }
    public boolean isWhite(){
        return (this.r==1.0 && this.g==1.0 && this.b == 1.0);
    }
}
