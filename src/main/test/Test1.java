package main.test;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.image.Image;

import java.util.Scanner;

public class Test1 {
    private static Image[] images;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() {
                while (true) {
                    String userInput = input.nextLine();

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println(userInput);
                        }
                    });

                }
            }
        };

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }
}
