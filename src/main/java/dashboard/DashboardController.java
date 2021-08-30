package dashboard;

import com.jfoenix.controls.JFXButton;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class DashboardController implements Initializable {

    protected Stage stage;

    // FXML CONTAINERS
    @FXML private AnchorPane rootStage;
    @FXML private ImageView imageView;

    // FXML ELEMENTS
    @FXML private JFXButton MINIMIZE;
    @FXML private JFXButton EXIT;
    @FXML private JFXButton SEARCH;

    @FXML private TextField searchBar;

    @FXML private Label name;
    @FXML private Label time;
    @FXML private Label date;
    @FXML private Label day;

    @FXML private Circle circle1;
    @FXML private Circle circle2;
    @FXML private Circle circle3;
    @FXML private Circle circle4;
    @FXML private Circle circle5;

    SimpleDateFormat timeFormat, dateFormat, dayFormat;
    String DAY, MONTH;

    Image[] images = new Image[10]; // Predefined array of Images
    Random random = new Random();   // Making obj of Random

    Timer timer = new Timer();// This is a timer, used to time things.
    TimerTask task = new TimerTask() { // This is where the task is
        @Override
        public void run() {
            Platform.runLater(() -> { // To update the UI element
                time.setText(timeFormat.format(Calendar.getInstance().getTime()));
            });
        }
    };

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
    public void initialize(URL url, ResourceBundle resourceBundle) { // This method loads when this controller is
        // called from main.
        try {
            loadImages();
            imageView.setImage(images[random.nextInt(images.length)]);
        } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
            e.printStackTrace();
        }
        initDateObjects();
        initDateDay();
        timer.scheduleAtFixedRate(task,0,1000); // Here we called the timer.
        animateCircles();
        rootStage.requestFocus();
    }

    public void loadImages() throws ArrayIndexOutOfBoundsException, NullPointerException{ // Made by Anurag This will
        // check how many files are there in the given dir and loads them one by one on an array.
        int filesTotal = Objects.requireNonNull(new File("src/main/resources/Images").listFiles()).length;
        for(int i=0; i<filesTotal;i++){
            images[i] = new Image("main/resources/Images/" + i + ".jpg");
        }
    }
    private void initDateObjects(){
        timeFormat = new SimpleDateFormat("hh:mm:ss a");
        dateFormat = new SimpleDateFormat("MM dd");
        dayFormat = new SimpleDateFormat("u");
    }
    private void initDateDay(){

        String monthDate = dateFormat.format(Calendar.getInstance().getTime());
        String dayCache = dayFormat.format(Calendar.getInstance().getTime());
        StringBuilder month = new StringBuilder();
        StringBuilder dateCache = new StringBuilder();

        for (int i = 0; i<monthDate.length()-1;i++){
            if (monthDate.charAt(i)==' '){
                ++i;
                for (int j = i; j<monthDate.length(); j++ ){
                    dateCache.append(monthDate.charAt(j));
                }
            }else month.append(monthDate.charAt(i));
        }
        switch (month.toString()) {
            case "01" -> MONTH = "JAN";
            case "02" -> MONTH = "FEB";
            case "03" -> MONTH = "MAR";
            case "04" -> MONTH = "APR";
            case "05" -> MONTH = "MAY";
            case "06" -> MONTH = "JUN";
            case "07" -> MONTH = "JUL";
            case "08" -> MONTH = "AUG";
            case "09" -> MONTH = "SEP";
            case "10" -> MONTH = "OCT";
            case "11" -> MONTH = "NOV";
            case "12" -> MONTH = "DEC";
            default -> MONTH = "N/A";
        }
        date.setText(MONTH +" "+dateCache);

        switch (dayCache){
            case "1" -> DAY = "MON";
            case "2" -> DAY = "TUE";
            case "3" -> DAY = "WED";
            case "4" -> DAY = "THU";
            case "5" -> DAY = "FRI";
            case "6" -> DAY = "SAT";
            case "7" -> DAY = "SUN";
            default -> DAY = "N/A";
        }
        day.setText(DAY);
    }

    @FXML
    public void enableSearch(){
        SEARCH.setDisable(searchBar.getText() == null || (searchBar.getText().strip().length() < 2));

    }


    private void animateCircles(){
        ScaleTransition scaleTransition1 = new ScaleTransition(Duration.seconds(.4),circle1);
        ScaleTransition scaleTransition2 = new ScaleTransition(Duration.seconds(.3),circle2);
        ScaleTransition scaleTransition3 = new ScaleTransition(Duration.seconds(.6),circle3);
        ScaleTransition scaleTransition4 = new ScaleTransition(Duration.seconds(.3),circle4);
        ScaleTransition scaleTransition5 = new ScaleTransition(Duration.seconds(.4),circle5);
        FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(.4), circle1);
        FadeTransition fadeTransition2 = new FadeTransition(Duration.seconds(.3), circle2);
        FadeTransition fadeTransition3 = new FadeTransition(Duration.seconds(.6), circle3);
        FadeTransition fadeTransition4 = new FadeTransition(Duration.seconds(.3), circle4);
        FadeTransition fadeTransition5 = new FadeTransition(Duration.seconds(.4), circle5);

        fadeTransition1.setFromValue(1);
        fadeTransition1.setToValue(0);
        scaleTransition1.setByX(.25);
        scaleTransition1.setByY(.25);

        fadeTransition2.setFromValue(1);
        fadeTransition2.setToValue(0);
        scaleTransition2.setByX(.3);
        scaleTransition2.setByY(.3);

        fadeTransition3.setFromValue(1);
        fadeTransition3.setToValue(0);
        scaleTransition3.setByX(.8);
        scaleTransition3.setByY(.8);

        fadeTransition4.setFromValue(1);
        fadeTransition4.setToValue(0);
        scaleTransition4.setByX(.3);
        scaleTransition4.setByY(.3);

        fadeTransition5.setFromValue(1);
        fadeTransition5.setToValue(0);
        scaleTransition5.setByX(.25);
        scaleTransition5.setByY(.25);

        ParallelTransition parallelTransition1 = new ParallelTransition(scaleTransition1,fadeTransition1);
        ParallelTransition parallelTransition2 = new ParallelTransition(scaleTransition2,fadeTransition2);
        ParallelTransition parallelTransition3 = new ParallelTransition(scaleTransition3,fadeTransition3);
        ParallelTransition parallelTransition4 = new ParallelTransition(scaleTransition4,fadeTransition4);
        ParallelTransition parallelTransition5 = new ParallelTransition(scaleTransition5,fadeTransition5);

        parallelTransition1.setAutoReverse(true);
        parallelTransition2.setAutoReverse(true);
        parallelTransition3.setAutoReverse(true);
        parallelTransition4.setAutoReverse(true);
        parallelTransition5.setAutoReverse(true);


        SequentialTransition sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(parallelTransition1,parallelTransition2);


        SequentialTransition sequentialTransition1 = new SequentialTransition();
        sequentialTransition1.getChildren().addAll(parallelTransition5,parallelTransition4);

        ParallelTransition parallelTransitionSeq = new ParallelTransition(sequentialTransition,sequentialTransition1);

        SequentialTransition transition = new SequentialTransition(parallelTransitionSeq,parallelTransition3);

        transition.setInterpolator(Interpolator.EASE_BOTH);
        transition.setAutoReverse(true);
        transition.setRate(3);
        transition.setCycleCount(2);
        transition.play();


        TimerTask animate = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(transition::play);
            }
        };
        timer.scheduleAtFixedRate(animate,0,5000); // Here we called the timer.

    }
}
