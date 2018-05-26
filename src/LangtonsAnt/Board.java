package LangtonsAnt;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import remotecontrol.SerializableColor;

public class Board {
    public static void drawBoard(SerializableColor[][] grid, GraphicsContext refreshGraphicsContext, int width, int height){
        Platform.runLater(()->{
            int copyCellSize = SimulatorGlobal.cellSize.get();
            if(!SimulatorGlobal.simulateStopped.get()){
                for(int x=0;x<width;x++){
                    for(int y=0;y<height;y++){
                        refreshGraphicsContext.setFill(grid[x][y].getColor());
                        refreshGraphicsContext.fillRect(x * copyCellSize, y * copyCellSize, copyCellSize, copyCellSize);
                    }
                }
            }
            }
        );

    }
}
