package LoginSignUp;

import Dashboard.DashboardController;
import Dashboard.User;
import Manager.DatabaseManager;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;


/**
 *
 * @author Anurag Bharati
 *
 */

public class LoginController implements Initializable {
    private static double xOffset;
    private static double yOffset;
    protected Stage stage;
    protected Scene scene;
    public Parent root;
    private final DatabaseManager databaseManager = new DatabaseManager();;

    @FXML protected AnchorPane rootStage;
    @FXML private AnchorPane rootFx;
    @FXML private JFXButton Quit;
    @FXML private JFXButton Minimize;
    @FXML private JFXButton Expand;
    @FXML private JFXButton guestMode;
    @FXML private JFXButton guest;
    @FXML private JFXButton register;

    @FXML private JFXButton login;

    @FXML private Label errorLabel;

    @FXML private TextField gmailField;
    @FXML private PasswordField passField;

    private String givenName, familyName, gmail, country, city;

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
    private void onAction(ActionEvent actionEvent) throws Exception {
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
        if (actionEvent.getSource().equals(login)){
            if (checkFields()&&checkGmail(gmailField.getText())){
                login(actionEvent);
            }
        }
        if (actionEvent.getSource().equals(guestMode)||actionEvent.getSource().equals(guest)){
            switchAsGuest(actionEvent);
        }
        if (actionEvent.getSource().equals(register)){
            switchToSignUp(actionEvent);
        }
    }
    @FXML
    private void switchToSignUp(ActionEvent event) throws Exception {
        errorLabel.setTextFill(Color.WHITE);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/main/resources/LoginSignUp/LoginSignUp1.fxml"));
        root = fxmlLoader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setMaximized(false);
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        ResizeHelper.addResizeListener(stage);
        stage.show();

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
        if (Objects.requireNonNull(gmailField.getText()).length() <= 10) {
            errorLabel.setText("Please, provide a valid gmail address");
            errorLabel.setTextFill(Color.web("#f77622"));
            return false;
        }else if (Objects.equals(passField.getText(), "")){
            errorLabel.setText("Please, provide your password");
            errorLabel.setTextFill(Color.web("#f77622"));
            return false;
        }else if (passField.getText().length()<8){
            errorLabel.setText("Invalid Password, Please try again");
            errorLabel.setTextFill(Color.web("#f77622"));
            return false;
        } else return true;
    }

    private void switchAsGuest(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        Stage stage = new Stage();
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

    private void login(ActionEvent actionEvent) throws SQLException, IOException{
        User user;
        if (checkPass()) {
            user = fetchUser();
            if (user != null){
                stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();
                Stage stage = new Stage();
                stage.initStyle(StageStyle.TRANSPARENT);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/resources/dashboard/Dashboard.fxml"));

                root = fxmlLoader.load();
                scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                DashboardController dashboardController =  fxmlLoader.getController();
                dashboardController.fetchUser(user);
                stage.setScene(scene);
                stageDragable(root,stage);
                stage.show();
            }
        } else {
            errorLabel.setText("Incorrect login credential. Please, try again");
            errorLabel.setTextFill(Color.web("#f77622"));
        }

    }
    private boolean checkPass() throws SQLException {
        Connection connection = databaseManager.connect();

        PreparedStatement checkGmail = connection.prepareStatement(
                "SELECT gmail FROM person WHERE gmail = ?");
        checkGmail.setString(1, gmailField.getText().toLowerCase(Locale.ROOT).strip());
        ResultSet result = checkGmail.executeQuery();
        if (result.next()){
            if(gmailField.getText().strip().toLowerCase(Locale.ROOT).equals(
                    result.getString("gmail").strip())){
                PreparedStatement checkPass = connection.prepareStatement("SELECT pass FROM person " +
                        "WHERE gmail = ?");
                checkPass.setString(1, gmailField.getText().toLowerCase(Locale.ROOT).strip());
                ResultSet result0 = checkPass.executeQuery();
                if(result0.next()){
                    if(passField.getText().equals(result0.getString("pass"))){
                        result0.close();
                        result.close();
                        checkPass.close();
                        checkGmail.close();
                        connection.close();
                        databaseManager.disconnect();
                        return true;
                    }
                    else {
                        result0.close();
                        result.close();
                        checkPass.close();
                        checkGmail.close();
                        connection.close();
                        databaseManager.disconnect();
                        return false;
                    }

                }
                checkGmail.close();
                checkPass.close();
                result0.close();
                result.close();
                connection.close();
                databaseManager.disconnect();
                return false;
            }

        }
        checkGmail.close();
        result.close();
        connection.close();
        databaseManager.disconnect();
        return false;
    }
    private User fetchUser() throws SQLException {
        User user = new User();
        Connection connection = databaseManager.connect();
        PreparedStatement checkGmail = connection.prepareStatement(
                "SELECT * FROM person WHERE gmail = ?");
        checkGmail.setString(1, gmailField.getText().toLowerCase(Locale.ROOT).strip());
        ResultSet resultSet = checkGmail.executeQuery();
        if (resultSet.next()) {
            user.setGivenName(resultSet.getString("given_name"));
            user.setFamilyName(resultSet.getString("family_name"));
            user.setGmail(resultSet.getString("gmail"));
            user.setCountry(resultSet.getString("country"));
            user.setCity(resultSet.getString("city"));

            System.out.println(resultSet.getString("country"));
            resultSet.close();
            checkGmail.close();
            connection.close();
            databaseManager.disconnect();
            return user;
        }
        resultSet.close();
        checkGmail.close();
        connection.close();
        databaseManager.disconnect();
        return null;

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

}
