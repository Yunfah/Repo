package client;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;


public class PlaySound extends Application {

    public static void main(String[] args) {
        launch(args);
       // https://stackoverflow.com/questions/22490064/how-to-use-javafx-mediaplayer-correctly

    }

    @Override
    public void start(Stage primaryStage) {
           Media hit = new Media(new File("files/elevator.mp3").toURI().toString());
           MediaPlayer mediaPlayer = new MediaPlayer(hit);
           mediaPlayer.play();


    }
}
