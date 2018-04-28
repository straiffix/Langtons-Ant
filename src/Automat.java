import javafx.scene.paint.Color;

import javax.naming.SizeLimitExceededException;
import java.util.Currency;
import java.util.SimpleTimeZone;

public class Automat {
          static final int SIZE = 100;
          static final int STEPS=11000;
          int [][] field = new int[SIZE][SIZE];
          final Cell [] archive = new Cell[STEPS];
          int currentX=50;
          int currentY=50;
          int Duration=2;



          public Automat(){



                    for(int i=0; i<SIZE; i++){
                              for(int j=0; j<SIZE; j++){
                                        field[i][j]=0;
                              }
                    }

                    for(int i=0; i<STEPS; i++){

                              if(field[currentX][currentY]==0){
                                   if(Duration!=3){
                                             Duration++;
                                   } else {Duration=0;}

                                   field[currentX][currentY]=1;

                                        archive[i]=new Cell(currentX, currentY, Color.BLACK);



                                        if(Duration==0){
                                             currentY++;
                                   } else if (Duration==1){
                                             currentX++;
                                        }
                                        else if (Duration==2){
                                             currentY--;
                                   } else {currentX--;}

                              } else {
                                        if(Duration!=0){
                                                  Duration--;
                                        } else {Duration=3;}
                                        field[currentX][currentY]=0;

                                        archive[i]=new Cell(currentX, currentY, Color.WHITE);

                                        if(Duration==0){
                                                  currentY++;
                                        } else if (Duration==1){
                                                  currentX++;
                                        }
                                        else if (Duration==2){
                                                  currentY--;
                                        } else {currentX--;}
                              }
                    }



          }


}
