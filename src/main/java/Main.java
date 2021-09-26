import Model.ResizeHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

/**
 *
 * @author Anurag Bharati
 * @since 2021
 * @version 1.0
 *
 * <h2>Main Class</h2>
 * <p>Responsible for everything</p>
 */
public class Main extends Application{

    public static Scene scene;
    static double  xOffset, yOffset;
    Parent root;

    @SuppressWarnings("unused")
    String dashboard = "main/resources/View/dashboard/Dashboard.fxml";
    String ignite = "main/resources/View/LoginSignUp/LoginSignUp0.fxml";

    @Override
    public void start(Stage stage){

        try{
            root = FXMLLoader.load(Objects.requireNonNull(
                    getClass().getClassLoader().getResource(ignite)));
            scene = new Scene(root);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.getIcons().add(new Image("/main/resources/twilight.png"));
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            if (root.getId().equals("rootStage")){
                ResizeHelper.addResizeListener(stage);
            }else stageDragable(root,stage);
            stage.show();
        }

        catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }
    }
    /**
     * <h2>Enables Stage Drag</h2>
     * <p>This method is responsible with dragging of window</p>
     * @param root is Parent which is top level container
     * @param stage is the window
     */
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

    /**
     * <h2>Main Method</h2>
     * <p>Responsible for launching the app</p>
     * @param args idk
     */
    public static void main(String[] args) {
        launch(args);
    }
}
