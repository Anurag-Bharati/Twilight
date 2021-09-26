package Controller.LoginSignUp;

import Controller.Dashboard.DashboardController;
import Controller.Dashboard.User;
import Model.DatabaseManager;
import Model.FileIO;
import Model.ResizeHelper;
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
import javafx.scene.image.Image;
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
 * @since 2021
 * @version 1.0
 *
 */

public class LoginController implements Initializable {
    private static double xOffset;
    private static double yOffset;
    protected Stage stage;
    protected Scene scene;
    public Parent root;
    private final DatabaseManager databaseManager = new DatabaseManager();

    @FXML protected AnchorPane rootStage;
    @FXML private AnchorPane rootFx;
    @FXML private JFXButton Quit;
    @FXML private JFXButton Minimize;
    @FXML private JFXButton Expand;
    @FXML private JFXButton guestMode;
    @FXML private JFXButton guest;
    @FXML private JFXButton register;

    @FXML private JFXButton login;
    @FXML private JFXButton delete;

    @FXML private Label errorLabel;

    @FXML private TextField gmailField;
    @FXML private PasswordField passField;

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

    /**
     * <h2>Event Handler</h2>
     * <p>Responsible for many events handling</p>
     * @param actionEvent is event related to Button
     * @throws Exception if anything went wrong XD
     */
    @FXML
    private void onAction(ActionEvent actionEvent) throws Exception {
        errorLabel.setTextFill(Color.web("#f77622"));
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
            } else return;
        }
        if (actionEvent.getSource().equals(delete)){
            if (checkFields()&&checkGmail(gmailField.getText())){
                if (checkPass()) {
                    if (!delete.getText().equals("Confirm")) {
                        errorLabel.setTextFill(Color.web("#be4a2f"));
                        errorLabel.setText("Please, Press confirm to proceed");
                        delete.setText("Confirm");

                    } else if (delete.getText().equals("Confirm")){
                        if (removeUser() != 0) {
                            errorLabel.setTextFill(Color.web("#3e8948"));
                            errorLabel.setText("User successfully removed");
                            delete.setText("Delete");
                            gmailField.setText("");
                            passField.setText("");

                        } else errorLabel.setText("Something went wrong, Please try again");
                    }
                } else if (errorLabel.getText().equals("User not found")) {
                    errorLabel.setText("User not found ");
                } else errorLabel.setText("Something went wrong, Please try again");

            }
        }
        if (actionEvent.getSource().equals(guestMode)||actionEvent.getSource().equals(guest)){
            switchAsGuest(actionEvent);
        }
        if (actionEvent.getSource().equals(register)){
            switchToSignUp();
        }
    }

    /**
     * <h2>Switch to Registration</h2>
     * <p>This method is responsible for switching scene to registration</p>
     * @throws IOException if file not found
     */
    @FXML
    private void switchToSignUp() throws IOException {
        errorLabel.setTextFill(Color.WHITE);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/main/resources/View/LoginSignUp/LoginSignUp1.fxml"));
        root = fxmlLoader.load();
        stage.setMaximized(false);
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        ResizeHelper.addResizeListener(stage);
        stage.show();

    }

    /**
     * <h2>Is Gmail?</h2>
     * <p>This method checks if the provided email domain is gmail or not</p>
     * @param gMail is gmail address provided in string
     * @return boolean, True if gmail is provided and false if not.
     */
    public boolean checkGmail(String gMail) {

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

    /**
     * <h2>Basic Check</h2>
     * <p>Checks for null or invalid data provided while logging in</p>
     * @return boolean
     */
    public boolean checkFields() {


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

    /**
     * <h2>Guest Mode</h2>
     * @param actionEvent is Event related with button
     * @throws IOException if path ain't right
     */
    private void switchAsGuest(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        Stage stage = new Stage();
        stage.getIcons().add(new Image("/main/resources/twilight.png"));
        stage.initStyle(StageStyle.TRANSPARENT);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/resources/View/dashboard/Dashboard.fxml"));
        root = fxmlLoader.load();
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        DashboardController dashboardController =  fxmlLoader.getController();
        stage.setScene(scene);
        dashboardController.name.setText("GUEST");

        FileIO fileIO = new FileIO();
        dashboardController.fetchGuest(fileIO.read());
        fileIO.close();

        stageDragable(root,stage);
        stage.show();

    }


    /**
     * <h2>LogIn</h2>
     * <p>This method is responsible for login and switching to dashboard</p>
     * @param actionEvent is event related with button
     * @throws SQLException if sth is wrong w/ database
     * @throws IOException if file not found
     */
    private void login(ActionEvent actionEvent) throws SQLException, IOException{
        User user;
        if (checkPass()) {
            user = fetchUser();
            if (user != null){
                stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.close();
                Stage stage = new Stage();
                stage.getIcons().add(new Image("/main/resources/twilight.png"));
                stage.initStyle(StageStyle.TRANSPARENT);
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/resources/View/dashboard/Dashboard.fxml"));

                root = fxmlLoader.load();
                scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                DashboardController dashboardController =  fxmlLoader.getController();
                dashboardController.fetchUser(user);
                stage.setScene(scene);
                stageDragable(root,stage);
                stage.show();
            }
        }else {
            errorLabel.setText("Incorrect login credential. Please, try again");
            errorLabel.setTextFill(Color.web("#f77622"));
        }

    }

    /**
     * <h2>Checks For Credential Match</h2>
     * <p>This method is responsible for the actual login</p>
     * @return boolean, True if check pass and False if not.
     * @throws SQLException if sth is wrong w/ database
     */
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
        errorLabel.setText("User not found");
        return false;
    }

    /**
     * <h2>Fetch User Data</h2>
     * <p>This method fetches user data before switching stage</p>
     * @return User instance which contains all the user details
     * @throws SQLException if sth is wrong w/ database
     */
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

    /**
     * <h2>Delete User</h2>
     * <p>This method deletes the user data from db</p>
     * @return int, 0 if nothing changed in database > 0 if sth changed
     * @throws SQLException if sth is wrong with the query or db
     */
    private int removeUser() throws SQLException {
        Connection connection = databaseManager.connect();
        PreparedStatement checkGmail = connection.prepareStatement(
                "DELETE FROM person WHERE gmail = ?");
        checkGmail.setString(1, gmailField.getText().toLowerCase(Locale.ROOT).strip());
        int result = checkGmail.executeUpdate();
        checkGmail.close();
        connection.close();
        databaseManager.disconnect();
        return result;
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
