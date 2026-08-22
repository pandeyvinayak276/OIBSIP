package com.oibsip.reservation.ui;

import com.oibsip.reservation.db.ReservationDAO;
import com.oibsip.reservation.model.Train;
import com.oibsip.reservation.model.TrainClass;
import com.oibsip.reservation.util.PNRGenerator;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class ReviewBookingScreen {

    private final ReservationDAO reservationDAO =
            new ReservationDAO();

    private void showError(String message) {

        Alert alert =
                new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Crosq");
        alert.setHeaderText("Booking Error");
        alert.setContentText(message);

        alert.showAndWait();
    }

    public void show(
            Stage stage,
            Train train,
            String passengerName,
            int age,
            String gender,
            LocalDate journeyDate,
            TrainClass selectedClass,
            String berthPreference,
            String quota,
            String username,
            int userId) {

        Label titleLabel =
                new Label("Review Booking");

        titleLabel.setStyle(
                "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;"
        );

        Label subtitleLabel =
                new Label(
                        "Please review your booking details"
                );

        subtitleLabel.setStyle(
                "-fx-font-size: 14px;"
        );

        // Train
        Label trainLabel =
                new Label(
                        "Train: " +
                                train.getTrainNumber() +
                                " - " +
                                train.getTrainName()
                );

        // Route
        Label routeLabel =
                new Label(
                        "Route: " +
                                train.getSource() +
                                " → " +
                                train.getDestination()
                );

        // Passenger
        Label passengerLabel =
                new Label(
                        "Passenger: " +
                                passengerName
                );

        Label ageLabel =
                new Label(
                        "Age: " +
                                age
                );

        Label genderLabel =
                new Label(
                        "Gender: " +
                                gender
                );

        // Journey
        Label journeyDateLabel =
                new Label(
                        "Journey Date: " +
                                journeyDate
                );

        // Class
        Label classLabel =
                new Label(
                        "Class: " +
                                selectedClass.getClassType()
                );

        // Berth
        Label berthLabel =
                new Label(
                        "Berth Preference: " +
                                berthPreference
                );

        // Quota
        Label quotaLabel =
                new Label(
                        "Quota: " +
                                quota
                );

        // Fare
        Label fareLabel =
                new Label(
                        "Total Fare: ₹" +
                                selectedClass.getFare()
                );

        fareLabel.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;"
        );

        // Buttons
        Button backButton =
                new Button("Back");

        backButton.setPrefWidth(150);
        backButton.setPrefHeight(40);

        Button confirmButton =
                new Button("Proceed to Payment");

        confirmButton.setPrefWidth(180);
        confirmButton.setPrefHeight(40);

        /*
         * Back:
         * Return to the existing ReservationScreen.
         *
         * No database operation occurs.
         */
        backButton.setOnAction(event -> {

            ReservationScreen reservationScreen =
                    new ReservationScreen();

            reservationScreen.show(
                    stage,
                    train,
                    journeyDate,
                    username,
                    userId
            );
        });

        /*
         * Confirm Booking:
         *
         * 1. Check availability again.
         * 2. Generate PNR.
         * 3. Save reservation.
         * 4. Show confirmation.
         */
        confirmButton.setOnAction(event -> {

            PaymentScreen paymentScreen =
                    new PaymentScreen();

            paymentScreen.show(
                    stage,
                    train,
                    passengerName,
                    age,
                    gender,
                    journeyDate,
                    selectedClass,
                    berthPreference,
                    quota,
                    username,
                    userId
            );
        });

        HBox buttonLayout =
                new HBox(
                        15,
                        backButton,
                        confirmButton
                );

        buttonLayout.setAlignment(Pos.CENTER);

        VBox layout =
                new VBox(
                        12,

                        titleLabel,
                        subtitleLabel,

                        trainLabel,
                        routeLabel,

                        passengerLabel,
                        ageLabel,
                        genderLabel,

                        journeyDateLabel,

                        classLabel,
                        berthLabel,
                        quotaLabel,

                        fareLabel,

                        buttonLayout
                );

        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        Scene scene =
                new Scene(layout, 600, 700);

        stage.setTitle(
                "Crosq - Review Booking"
        );

        stage.setScene(scene);
        stage.show();
    }

    private void showConfirmation(
            Stage stage,
            Train train,
            String pnr,
            String passengerName,
            int age,
            String gender,
            LocalDate journeyDate,
            TrainClass selectedClass,
            String berthPreference,
            String quota) {

        Label titleLabel =
                new Label("Booking Confirmed ✓");

        titleLabel.setStyle(
                "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;"
        );

        Label pnrLabel =
                new Label(
                        "PNR: " +
                                pnr
                );

        pnrLabel.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;"
        );

        Label trainLabel =
                new Label(
                        train.getTrainNumber() +
                                " - " +
                                train.getTrainName()
                );

        Label routeLabel =
                new Label(
                        train.getSource() +
                                " → " +
                                train.getDestination()
                );

        Label passengerLabel =
                new Label(
                        "Passenger: " +
                                passengerName
                );

        Label ageLabel =
                new Label(
                        "Age: " +
                                age
                );

        Label genderLabel =
                new Label(
                        "Gender: " +
                                gender
                );

        Label dateLabel =
                new Label(
                        "Journey Date: " +
                                journeyDate
                );

        Label classLabel =
                new Label(
                        "Class: " +
                                selectedClass.getClassType()
                );

        Label fareLabel =
                new Label(
                        "Fare: ₹" +
                                selectedClass.getFare()
                );

        Label berthLabel =
                new Label(
                        "Berth Preference: " +
                                berthPreference
                );

        Label quotaLabel =
                new Label(
                        "Quota: " +
                                quota
                );

        Label statusLabel =
                new Label(
                        "Status: CONFIRMED"
                );

        statusLabel.setStyle(
                "-fx-font-weight: bold;"
        );

        Button dashboardButton =
                new Button("Back to Dashboard");

        dashboardButton.setPrefWidth(220);
        dashboardButton.setPrefHeight(40);

        dashboardButton.setOnAction(event -> {

            DashboardScreen dashboardScreen =
                    new DashboardScreen();

            /*
             * Dashboard navigation will be improved
             * later when username/session handling
             * is centralized.
             */
            stage.close();
        });

        VBox layout =
                new VBox(
                        12,

                        titleLabel,
                        pnrLabel,

                        trainLabel,
                        routeLabel,

                        passengerLabel,
                        ageLabel,
                        genderLabel,

                        dateLabel,

                        classLabel,
                        fareLabel,
                        berthLabel,
                        quotaLabel,

                        statusLabel,

                        dashboardButton
                );

        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        Scene scene =
                new Scene(layout, 600, 750);

        stage.setTitle(
                "Crosq - Booking Confirmation"
        );

        stage.setScene(scene);
        stage.show();
    }
}