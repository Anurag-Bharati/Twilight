package LoginSignUp;

import Dashboard.DashboardController;
import Dashboard.User;
import Manager.MailVerify;
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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;


/**
 *
 * @author Anurag Bharati
 * @since 2021
 * @version 1.0
 *
 */

public class SignUpController2 implements Initializable {
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
    @FXML
    private JFXButton next;

    @FXML
    private Label errorLabel;

    @FXML
    private PasswordField passField;
    @FXML
    private PasswordField confirmPassField;
    @FXML
    private TextField authField;

    private String givenName;
    private String familyName;
    private String password;
    private String confirmPass;
    private String gmail;
    private String gmailOld;
    private Boolean sent = false;

    @SuppressWarnings("FieldCanBeLocal")
    private String authCode;

    String authCodeSys = String.valueOf(MailVerify.OTP);

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
        if (actionEvent.getSource().equals(next)) {
            onDone(actionEvent);
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
        if (passField.getText().equals(confirmPassField.getText())) {
            user.setPassword(passField.getText());
            user.setConfirmPass(confirmPassField.getText());
        }
        user.setAuthCode(authField.getText());
        user.setCountry(country);
        user.setCity(city);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/resources/LoginSignUp/LoginSignUp1.fxml"));
        root = fxmlLoader.load();

        SignUpController signUpController = fxmlLoader.getController();
        signUpController.initUser(user);

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
        if (user.getCountry() != null) {
            this.country = user.getCountry();
        }
        if (user.getCity()!= null) {
            this.city = user.getCity();
        }

        if (password != null && password.equals(confirmPass)) {
            passField.setText(password);
            confirmPassField.setText(confirmPass);
        }
        if (authCode != null && authCode.strip().length() == authCodeSys.length()) {
            authField.setText(authCode);
        }
    }
    private boolean checkFieldsTwo() {

        /*This checks for the password validity made totally by anurag :) at 12AM 8/27/2021 */

        if ((Objects.requireNonNull(passField.getText()).length() >= 8)) {
            if (passField.getText().equals(confirmPassField.getText())) {
                if (checkPasswordStrength(passField.getText())) {
                    return true;
                } else errorLabel.setText("Password must contain at least one letter and number");errorLabel.setTextFill(Color.web("#f77622"));

                return false;
            } else errorLabel.setText("Password does not match on both fields");errorLabel.setTextFill(Color.web("#f77622"));

            return false;

        } else errorLabel.setText("Password must be at least 8 character long");errorLabel.setTextFill(Color.web("#f77622"));

        return false;
    }

    public boolean checkPasswordStrength(String password) {

        /* This is the password strength checker*/

        boolean hasLetter = false;
        boolean hasDigit = false;
        for (int i = 0; i < password.length(); i++) {
            char x = password.charAt(i);
            if (Character.isLetter(x)) {
                hasLetter = true;
            }
            if (Character.isDigit(x)) {
                hasDigit = true;
            }
            if (hasDigit && hasLetter) {
                return true;
            }
        }
        return false;
    }

    @FXML
    private void onDone(ActionEvent actionEvent) throws IOException {
        if (checkFieldsTwo()) {
            user.setPassword(passField.getText());
            password = passField.getText();
            confirmPass = confirmPassField.getText();
            if (authField.getText().strip().equals(authCodeSys)) {
                switchToLast(actionEvent);
            }else {
                errorLabel.setTextFill(Color.web("#f77622"));
                errorLabel.setText("Auth code does not match");
            }
        }
    }
    private void switchToLast(ActionEvent event) throws IOException {
        user = new User();
        user.setGivenName(givenName);
        user.setFamilyName(familyName);
        user.setGmail(gmail);
        user.setGmailOld(gmailOld);
        user.setSent(sent);
        user.setPassword(passField.getText());
        user.setConfirmPass(confirmPassField.getText());
        user.setAuthCode(authField.getText());
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
    /**
     * <h2>Guest Mode</h2>
     * @throws IOException if path ain't right
     */
    private void switchAsGuest() throws IOException {
        stage.close();
        Stage stage = new Stage();
        stage.getIcons().add(new Image("/main/resources/twilight.png"));
        stage.initStyle(StageStyle.TRANSPARENT);
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