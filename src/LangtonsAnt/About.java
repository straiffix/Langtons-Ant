package LangtonsAnt;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import translations.*;


public class About {

    TranslateController currentLanguage = new TranslateController();
    Stage primaryStage;

    public About(Stage primaryStage, TranslateController currentLanguage){
        this.primaryStage = primaryStage;
        this.currentLanguage=currentLanguage;
    }


    public void showInfoWindow(){
        Stage infoStage = new Stage();
        infoStage.initModality(Modality.APPLICATION_MODAL);
        infoStage.initOwner(primaryStage);

        VBox vb = new VBox();
        vb.setPrefSize(500, 400);

        Text text2 = new Text(currentLanguage.rules.getValue());
        text2.setTextAlignment(TextAlignment.CENTER);
        text2.setFont(Font.font("Cambria"));
        TextFlow rules = new TextFlow(text2);
        rules.setMaxWidth(300);
        rules.setMaxHeight(300);

        ImageView secondTitleImage = new ImageView("LangtonsAnt/langtons.png");

        vb.getChildren().add(secondTitleImage);
        vb.getChildren().add(rules);
        vb.setAlignment(Pos.CENTER);

        Scene infoScene = new Scene(vb, 500, 600);
        infoStage.setScene(infoScene);
        infoStage.show();


    }
}
