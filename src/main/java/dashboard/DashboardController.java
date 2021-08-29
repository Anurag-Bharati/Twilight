package dashboard;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    protected Stage stage;

    @FXML
    private AnchorPane rootStage;
    @FXML
    private JFXButton MINIMIZE;
    @FXML
    private JFXButton EXIT;

    @FXML
    private ImageView imageView;

    Image[] images = new Image[10];
    Random random = new Random();

    @FXML
    public void eventHandler(ActionEvent event){
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (event.getSource().equals(EXIT)) {
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(.4), rootStage);
            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(.4), rootStage);

            scaleTransition.setInterpolator(Interpolator.EASE_IN);
            scaleTransition.setByX(.05);

            fadeTransition.setInterpolator(Interpolator.EASE_IN);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0);

            scaleTransition.play();
            fadeTransition.play();

            fadeTransition.setOnFinished(actionEvent1 -> {
                scaleTransition.stop();
                fadeTransition.stop();
                stage.close();
                Platform.exit();
                System.exit(0);
            });
        }
        else if (event.getSource().equals(MINIMIZE)){
            stage.setIconified(!stage.isIconified());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadImages();
        imageView.setImage(images[random.nextInt(images.length)]);
    }
    public void loadImages() throws ArrayIndexOutOfBoundsException, NullPointerException{
        int filesTotal = Objects.requireNonNull(new File("src/main/resources/Images").listFiles()).length;
        for(int i=0; i<filesTotal;i++){
            images[i] = new Image("main/resources/Images/" + i + ".jpg");
        }
    }
}
