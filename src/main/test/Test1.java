package main.test;

import javafx.scene.image.Image;

import java.io.File;
import java.util.Objects;

public class Test1 {
    private static Image[] images;

    public static void main(String[] args) {
        int filesTotal = Objects.requireNonNull(new File("src/main/resources/Images").listFiles()).length;
        for(int i=0; i<filesTotal;i++){

            images[i] = new Image("main/resources/Images/"+i+".jpg");
        }
        System.out.println(images.length);
    }
}
