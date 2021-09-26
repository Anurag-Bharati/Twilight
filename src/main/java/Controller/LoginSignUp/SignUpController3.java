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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

public class SignUpController3 implements Initializable {
    private static double xOffset;
    private static double yOffset;
    protected Stage stage;
    protected Scene scene;
    public Parent root;
    User user;
    DatabaseManager databaseManager = new DatabaseManager();

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
    private void onAction(ActionEvent actionEvent) throws IOException, SQLException {
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
            switchToLogin(actionEvent);
        }
        if (actionEvent.getSource().equals(EULA)) {
            switchToEULA(actionEvent);
        }
        if (actionEvent.getSource().equals(Guest)) {
            switchAsGuest();
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

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/resources/View/LoginSignUp/LoginSignUp4.fxml"));
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
        country = countryField.getText();
        city = cityField.getText();
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

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/resources/View/LoginSignUp/LoginSignUp2.fxml"));
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
    private void switchToLogin(ActionEvent event) throws SQLException {
        if (check()) {
            if (NoExistence()) {
                addNewUser();
                countryField.setText("");
                cityField.setText("");
                countryField.setDisable(true);
                cityField.setDisable(true);
                back.setDisable(true);
                EULA.setDisable(true);
                Guest.setDisable(true);
                Expand.setDisable(true);
                Minimize.setDisable(true);
                Quit.setDisable(true);
                register.setDisable(true);
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(.3), errorLabel);
                fadeTransition.setFromValue(1.0);
                fadeTransition.setToValue(0.0);
                fadeTransition.setCycleCount(6);
                fadeTransition.setAutoReverse(true);
                fadeTransition.setCycleCount(10);
                fadeTransition.play();
                errorLabel.setText("User Added Successfully! Now Redirecting...");
                errorLabel.setTextFill(Color.web("#3e8948"));
                fadeTransition.setOnFinished(e->{
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/resources/View/LoginSignUp/LoginSignUp0.fxml"));
                    try {
                        root = fxmlLoader.load();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    scene.setFill(Color.TRANSPARENT);
                    stage.setScene(scene);
                    ResizeHelper.addResizeListener(stage);
                    stage.show();
                });

            } else {
                errorLabel.setText("User Already Exists! Please, Provide a new Gmail");
                errorLabel.setTextFill(Color.web("#f77622"));
            }
        }
    }

    private boolean NoExistence() throws SQLException {
        Connection connection = databaseManager.connect();

        PreparedStatement checkGmail = connection.prepareStatement(
                "SELECT gmail FROM person WHERE gmail = ?");
        checkGmail.setString(1, gmail);

        ResultSet result = checkGmail.executeQuery();
        if(result.next()) {
            if (gmail.strip().equals(result.getString("gmail"))) {
                result.close();
                checkGmail.close();
                databaseManager.disconnect();
                return false;
            }
        }
        checkGmail.close();
        connection.close();
        databaseManager.disconnect();
        return true;

    }
    private void addNewUser() throws SQLException {
        Connection connection = databaseManager.connect();
        PreparedStatement addUser = connection.prepareStatement(
                "insert into person (gmail, given_name, family_name, pass, country, city) values (?,?,?,?,?,?)");
        addUser.setString(1,gmail);
        addUser.setString(2,givenName);
        addUser.setString(3,familyName);
        addUser.setString(4,password);
        addUser.setString(5,country);
        addUser.setString(6,city);
        addUser.executeUpdate();
        addUser.close();
        connection.close();
        databaseManager.disconnect();
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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/resources/View/dashboard/Dashboard.fxml"));
        root = fxmlLoader.load();
        scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        DashboardController dashboardController =  fxmlLoader.getController();
        dashboardController.name.setText("GUEST");
        FileIO fileIO = new FileIO();
        dashboardController.fetchGuest(fileIO.read());
        fileIO.close();
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