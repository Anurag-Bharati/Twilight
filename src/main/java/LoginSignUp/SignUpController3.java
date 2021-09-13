package LoginSignUp;

import Dashboard.User;
import Manager.ResizeHelper;
import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;


/**
 *
 * @author Anurag Bharati
 *
 */

public class SignUpController3 implements Initializable {
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
    private Button back;
    @FXML
    private JFXButton register;
    @FXML
    private Button EULA;

    @FXML
    private Label errorLabel;
    @FXML
    private TextField countryField;
    @FXML
    private TextField cityField;

    private String givenName;
    private String familyName;
    private String password;
    private String confirmPass;
    private String authCode;
    private String gmail;
    private String gmailOld;
    private Boolean sent = false;

    private String country;
    private String city;

    int screenWidth = 400;
    Random random = new Random();

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
        if (actionEvent.getSource().equals(register)) {
            onDone(actionEvent);
        }
        if (actionEvent.getSource().equals(EULA)) {
            switchToEULA(actionEvent);
        }

    }

    private void switchToEULA(ActionEvent actionEvent) throws IOException {

        user = new User();
        user.setGivenName(givenName);
        user.setFamilyName(familyName);
        user.setGmail(gmail);
        user.setGmailOld(gmailOld);
        user.setSent(sent);
        user.setPassword(password);
        user.setConfirmPass(confirmPass);
        user.setAuthCode(authCode);
        if (countryField.getText()!=null && countryField.getText().length()>2 && countryField.getText().length()<=30) {
            user.setCountry(countryField.getText());
        }
        if (cityField.getText()!=null && cityField.getText().length()>2 && cityField.getText().length()<=30) {
            user.setCity(cityField.getText());
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/resources/LoginSignUp/LoginSignUp4.fxml"));
        root = fxmlLoader.load();

        SignUpController4 signUpController4 = fxmlLoader.getController();
        signUpController4.initUser(user);

        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setMaximized(false);
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        ResizeHelper.addResizeListener(stage);
        stage.show();

    }

    private boolean check() {
        if (Objects.equals(countryField.getText(), "") || Objects.equals(cityField.getText(), "")){
            errorLabel.setTextFill(Color.web("#f77622"));
            errorLabel.setText("Please provide your country and city name");
            return false;
        }
        if (countryField.getText().length()<3 || cityField.getText().length()<3){
            errorLabel.setTextFill(Color.web("#f77622"));
            errorLabel.setText("Country and City name must be at least 3 char long");
            return false;
        }
        if (countryField.getText().length()>30 || cityField.getText().length()>30){
            errorLabel.setTextFill(Color.web("#f77622"));
            errorLabel.setText("Country and City name must be at most 30 char long");
            return false;
        }

        return true;
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
        user.setCountry(countryField.getText());
        user.setCity(cityField.getText());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/resources/LoginSignUp/LoginSignUp2.fxml"));
        root = fxmlLoader.load();

        SignUpController2 signUpController2 = fxmlLoader.getController();
        signUpController2.initUser(user);

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

        if (user.getCountry() != null) {
            countryField.setText(user.getCountry());

        }
        if (user.getCity()!= null) {
            cityField.setText(user.getCity());
        }
    }

    @FXML
    private void switchToLogin(Event event) throws IOException {
        if (check()) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/resources/LoginSignUp/Login.fxml"));
            root = fxmlLoader.load();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            ResizeHelper.addResizeListener(stage);
            stage.show();
        }
    }

    @FXML
    private void onDone(ActionEvent actionEvent) {
        check();
    }
}