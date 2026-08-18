package com.oibsip.reservation.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DashboardScreen {

    public void show(Stage stage, String username, int userId){
        Label logoLabel = new Label("CROSQ");
        logoLabel.setStyle(
                "-fx-font-size: 26px;" +
                "-fx-font-weight: bold;"
        );

        Label welcomeLabel = new Label("Welcome, " + username);
        welcomeLabel.setStyle(
                "-fx-font-size: 16px;"
        );

        HBox header = new HBox(20, logoLabel, welcomeLabel);
        header.setAlignment(Pos.CENTER_LEFT);

        Label headingLabel = new Label("Your journey starts here.");
        headingLabel.setStyle(
                "-fx-font-size: 26px;" +
                "-fx-font-weight: bold;"
        );

        Label descriptionLabel = new Label(
                "Search trains, reserve your seat and manage your trips."
        );

        Button searchButton = new Button("Search Trains");
        searchButton.setPrefWidth(180);
        searchButton.setPrefHeight(45);

        searchButton.setOnAction(event -> {

            SearchTrainsScreen searchTrainsScreen =
                    new SearchTrainsScreen();

            searchTrainsScreen.show(
                    stage,
                    username,
                    userId
            );
        });

        Button bookingsButton = new Button("My Bookings");
        bookingsButton.setPrefWidth(180);
        bookingsButton.setPrefHeight(45);

        Button logoutButton = new Button("Logout");
        logoutButton.setPrefWidth(120);

        HBox actionButtons = new HBox(
                15,
                searchButton,
                bookingsButton
        );

        actionButtons.setAlignment(Pos.CENTER);

        VBox layout = new VBox(
                25,
                header,
                headingLabel,
                descriptionLabel,
                actionButtons,
                logoutButton
        );

        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(35));

        Scene scene = new Scene(layout, 650, 450);

        stage.setTitle("Crosq");
        stage.setScene(scene);
        stage.show();

        logoutButton.setOnAction(event -> {
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.show(stage);
        });
    }
}
