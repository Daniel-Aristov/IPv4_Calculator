package ru.aristov.ipv4_calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("IPCalculator-view.fxml"));

        Scene scene = new Scene(root.load());
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());

        stage.getIcons().add(new Image(String.valueOf(Objects.requireNonNull(getClass().getResource("images/icon-calculator.png")))));
        stage.setTitle("IPv4 Calculator");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static String arrayToString(int[] arr) {
        String result = "";
        for (int i = 0; i < arr.length; i++) {
            result += arr[i];
            if (i != arr.length-1) result += ".";
        }
        return result;
    }
}