import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class Main extends Application implements Initializable {

    public static Scene scene;
    static double  xOffset, yOffset;
    Parent root;

    @Override
    public void start(Stage stage){


        try{
            //https://stackoverflow.com/questions/61531317/how-do-i-determine-the-correct-path-for-fxml-files-css-files-images-and-other
            root = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getClassLoader().getResource("main/resources/dashboard/Dashboard.fxml")));

            scene = new Scene(root);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stageDragable(root,stage);
            stage.setScene(scene);
            stage.show();

        }

        catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void stageDragable(Parent root, Stage stage){
        root.setOnMousePressed(mouseEvent -> {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        });

        root.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX()-xOffset);
            stage.setY(mouseEvent.getScreenY()-yOffset);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
