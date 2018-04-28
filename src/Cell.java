import javafx.scene.paint.Color;

public class Cell {

          int x;
          int y;
          Color color;

          public Cell(int x, int y, Color color){
                    this.x=x;
                    this.y=y;
                    this.color=color;
          }

          public void setX(int x) {
                    this.x = x;
          }

          public void setY(int y) {
                    this.y = y;
          }

          public void setColor(Color color) {
                    this.color = color;
          }



          public int getX() {
                    return x;
          }

          public int getY() {
                    return y;
          }

          public Color getColor() {
                    return color;
          }
}
