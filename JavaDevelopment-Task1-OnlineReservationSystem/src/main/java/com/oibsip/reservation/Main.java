package com.oibsip.reservation;

import com.oibsip.reservation.ui.LoginScreen;
import com.oibsip.reservation.db.DatabaseInitializer;
import com.oibsip.reservation.db.DatabaseSeeder;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        DatabaseInitializer.initialize();
        DatabaseSeeder.seedTrains();
        DatabaseSeeder.seedTrainClasses();

        LoginScreen loginScreen = new LoginScreen();
        loginScreen.show(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}