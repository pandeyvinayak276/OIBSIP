package com.oibsip.reservation;

import com.oibsip.reservation.db.DatabaseInitializer;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        DatabaseInitializer.initialize();

        stage.setTitle("OIBSIP - Online Reservation System");
        stage.setWidth(500);
        stage.setHeight(300);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
