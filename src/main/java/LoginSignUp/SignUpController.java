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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;


/**
 *
 * @author Anurag Bharati
 *
 */

public class SignUpController implements Initializable {
    protected Stage stage;
    protected Scene scene;
    public Parent root;
    User user;

    @FXML protected AnchorPane rootStage;
    @FXML private AnchorPane rootFx;
    @FXML private JFXButton Quit;
    @FXML private JFXButton Minimize;
    @FXML private JFXButton Expand;

    @FXML private JFXButton next;

    @FXML private Label errorLabel;

    @FXML private TextField givenNameField;
    @FXML private TextField familyNameField;
    @FXML private TextField gmailField;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Rectangle[] r = new Rectangle[100];
        for (int i = 0; i < 100; i++) {
            r[i] = new Rectangle(1, random.nextInt(20),5,5);
            Color color = Color.rgb(255, 255, 255, random.nextDouble());
            r[i].setFill(color);
            rootFx.getChildren().add(r[i]);
            Raining(r[i]);
        }
    }
    private void Raining(Rectangle r) {
        if ((int)rootFx.getHeight()!=0){
            screenWidth = (int)rootFx.getWidth();
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
    private void onAction(ActionEvent actionEvent){
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
        if (actionEvent.getSource().equals(Minimize)){
            stage.setIconified(!stage.isIconified());
        }
        if (actionEvent.getSource().equals(Expand)){
            stage.setMaximized(!stage.isMaximized());
        }
    }
    @FXML
    public void switchToSignUpVer(ActionEvent event) throws IOException {
        errorLabel.setText("Tip: You can login directly as a guest by clicking the guest button by the minimize button");
        errorLabel.setTextFill(Color.WHITE);
        if (checkGmail(gmailField.getText().toLowerCase(Locale.ROOT))) {
            if (checkFields()) {
                user = new User();
                user.setGivenName(givenNameField.getText());
                user.setFamilyName(familyNameField.getText());
                user.setGmail(gmailField.getText().strip().toLowerCase(Locale.ROOT));
                user.setGmailOld(gmailField.getText().strip().toLowerCase(Locale.ROOT));
                user.setSent(sent);

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                        "../../resource/LoginSignUp/LoginSignUp2.fxml"));
                root = fxmlLoader.load();
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                ResizeHelper.addResizeListener(stage);
                stage.show();
            }
        }

    }
    public boolean checkGmail(String gMail) {

        /*This function takes gmail as string and checks if the domain is gmail or not.
        If not it returns false and true if it is.*/

        StringBuilder checkDomain = new StringBuilder();

        for (int i = 0; i < gMail.length(); i++) {
            char letter = gMail.charAt(i);
            if (letter == '@') {
                for (int j = i; j < gMail.length(); j++) {
                    if (gMail.charAt(j) != ' ') {
                        checkDomain.append(gMail.charAt(j));
                    } else j++;
                    if (checkDomain.toString().equals("@gmail.com")) {
                        return true;
                    }
                }

            }
        }
        return false;
    }
    public boolean checkFields() {

        /*This method check for data validity made totally by anurag :) at 12AM 8/27/2021 */

        if (Objects.requireNonNull(givenNameField.getText()).length() < 3) {
            errorLabel.setText("Given Name must be at least 3 characters long");
            return false;
        }else if (Objects.requireNonNull(givenNameField.getText()).length() > 30) {
            errorLabel.setText("Given Name must be of 30 characters max");
            return false;
        }if (Objects.requireNonNull(familyNameField.getText()).length() < 3) {
            errorLabel.setText("Family Name must be at least 3 characters long");
            return false;
        }else if (Objects.requireNonNull(familyNameField.getText()).length() > 30) {
            errorLabel.setText("Family Name must be of 30 characters max");
            return false;

        }else if (Objects.requireNonNull(gmailField.getText()).length() <= 10) {
            errorLabel.setText("Please, provide a valid gmail address");
            return false;
        } else if (!errorLabel.getText().equals("All the requirements have been satisfied. Press Confirm to proceed" +
                ".")){
            errorLabel.setText("All the requirements have been satisfied. Press Confirm to proceed.");
            errorLabel.setTextFill(Color.web("#3e8948"));
            next.setText("CONFIRM");
            return false;
        } else return true;
    }
    public void initUser(User user) {

        /*This method is used to pass object between scenes.*/

        this.givenName = user.getGivenName();
        this.familyName = user.getFamilyName();
        this.gmail = user.getGmail();
        this.password = user.getPassword();
        this.confirmPass = user.getConfirmPass();
        this.authCode = user.getAuthCode();
        this.sent = user.isSent();
        this.gmailOld = user.getGmailOld();

        givenNameField.setText(givenName);
        familyNameField.setText(familyName);
        gmailField.setText(gmail);
    }
}
