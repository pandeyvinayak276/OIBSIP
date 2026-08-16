package com.oibsip.reservation;
import com.oibsip.reservation.db.DatabaseInitializer;
import com.oibsip.reservation.db.DatabaseSeeder;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        DatabaseInitializer.initialize();
        DatabaseSeeder.seedTrains();

        stage.setTitle("OIBSIP - Online Reservation System");
        stage.setWidth(500);
        stage.setHeight(300);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}