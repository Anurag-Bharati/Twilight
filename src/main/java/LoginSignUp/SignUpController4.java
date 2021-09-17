package LoginSignUp;

import Dashboard.DashboardController;
import Dashboard.User;
import Manager.ResizeHelper;
import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;


/**
 *
 * @author Anurag Bharati
 * @since 2021
 * @version 1.0
 *
 */

public class SignUpController4 implements Initializable {
    private static double xOffset;
    private static double yOffset;
    protected Stage stage;
    protected Scene scene;
    public Parent root;
    User user;

    @FXML
    protected AnchorPane rootStage;
    @FXML
    private AnchorPane rootFx;
    @FXML
    private JFXButton Quit;
    @FXML
    private JFXButton Minimize;
    @FXML
    private JFXButton Expand;
    @FXML
    private JFXButton Guest;

    @FXML
    private Button back;

    private String givenName;
    private String familyName;
    private String password;
    private String confirmPass;
    private String authCode;
    private String gmail;
    private String gmailOld;
    private Boolean sent = false;

    int screenWidth = 400;
    Random random = new Random();
    private String country;
    private String city;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Rectangle[] r = new Rectangle[100];
        for (int i = 0; i < 100; i++) {
            r[i] = new Rectangle(1, random.nextInt(20), 5, 5);
            Color color = Color.rgb(255, 255, 255, random.nextDouble());
            r[i].setFill(color);
            rootFx.getChildren().add(r[i]);
            Raining(r[i]);
        }
    }

    private void Raining(Rectangle r) {
        if ((int) rootFx.getHeight() != 0) {
            screenWidth = (int) rootFx.getWidth();
        }
        r.setX(random.nextInt(screenWidth));
        int time = 2 + random.nextInt(2);
        TranslateTransition walk = new TranslateTransition(Duration.seconds(time), r);
        walk.setDelay(Duration.seconds(random.nextDouble()));
        walk.setFromY(-10);
        walk.setToY(160);
        walk.setOnFinished(t -> Raining(r));
        walk.play();
    }

    @FXML
    private void onAction(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        if (actionEvent.getSource().equals(Quit)) {
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(.4), rootStage);
            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(.4), rootStage);

            scaleTransition.setInterpolator(Interpolator.EASE_IN);
            scaleTransition.setByX(-.05);
            scaleTransition.setByY(-.9);

            fadeTransition.setInterpolator(Interpolator.EASE_IN);
            fadeTransition.setFromValue(1.0);
            fadeTransition.setToValue(0);

            scaleTransition.play();
            fadeTransition.play();

            fadeTransition.setOnFinished(actionEvent1 -> {
                scaleTransition.stop();
                fadeTransition.stop();
                actionEvent.consume();
                stage.close();
                Platform.exit();
                System.exit(0);
            });
        }
        if (actionEvent.getSource().equals(Minimize)) {
            stage.setIconified(!stage.isIconified());
        }
        if (actionEvent.getSource().equals(Expand)) {
            stage.setMaximized(!stage.isMaximized());
        }
        if (actionEvent.getSource().equals(back)) {
            switchToSignUp(actionEvent);
        }
        if (actionEvent.getSource().equals(Guest)) {
            switchAsGuest();
        }

    }

    @FXML
    private void switchToSignUp(ActionEvent event) throws IOException {
        user = new User();
        user.setGivenName(givenName);
        user.setFamilyName(familyName);
        user.setGmail(gmail);
        user.setGmailOld(gmailOld);
        user.setSent(sent);
        user.setPassword(password);
        user.setConfirmPass(confirmPass);
        user.setAuthCode(authCode);
        user.setCountry(country);
        user.setCity(city);


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/resources/LoginSignUp/LoginSignUp3.fxml"));
        root = fxmlLoader.load();

        SignUpController3 signUpController3 = fxmlLoader.getController();
        signUpController3.initUser(user);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setMaximized(false);
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        ResizeHelper.addResizeListener(stage);
        stage.show();

    }

    public void initUser(User user) {

        /*This method is used to pass object between scenes.*/
        this.user = user;
        this.givenName = user.getGivenName();
        this.familyName = user.getFamilyName();
        this.gmail = user.getGmail();
        this.password = user.getPassword();
        this.confirmPass = user.getConfirmPass();
        this.authCode = user.getAuthCode();
        this.sent = user.isSent();
        this.gmailOld = user.getGmailOld();
        this.country = user.getCountry();
        this.city = user.getCity();
    }
    /**
     * <h2>Guest Mode</h2>
     * @throws IOException if path ain't right
     */
    private void switchAsGuest() throws IOException {
        stage.close();
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().add(new Image("/main/resources/twilight.png"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/resources/dashboard/Dashboard.fxml"));
        root = fxmlLoader.load();
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        DashboardController dashboardController =  fxmlLoader.getController();
        dashboardController.name.setText("GUEST");
        stage.setScene(scene);
        stageDragable(root,stage);
        stage.show();
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
}