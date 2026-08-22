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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class PaymentScreen {

    private final ReservationDAO reservationDAO =
            new ReservationDAO();

    private void showError(String message) {

        Alert alert =
                new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Crosq");
        alert.setHeaderText("Payment Error");
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
                new Label("Payment");

        titleLabel.setStyle(
                "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;"
        );

        Label subtitleLabel =
                new Label(
                        "Choose a payment method"
                );

        Label amountLabel =
                new Label(
                        "Amount to Pay: ₹" +
                                selectedClass.getFare()
                );

        amountLabel.setStyle(
                "-fx-font-size: 20px;" +
                        "-fx-font-weight: bold;"
        );

        // Payment method
        Label methodLabel =
                new Label("Payment Method");

        ComboBox<String> paymentMethodBox =
                new ComboBox<>();

        paymentMethodBox.getItems().addAll(
                "UPI",
                "Debit/Credit Card",
                "Net Banking",
                "Crosq Wallet"
        );

        paymentMethodBox.setPromptText(
                "Select payment method"
        );

        paymentMethodBox.setMaxWidth(300);

        // Payment details
        Label detailsLabel =
                new Label("Payment Details");

        TextField detailsField =
                new TextField();

        detailsField.setPromptText(
                "Enter payment details"
        );

        detailsField.setMaxWidth(300);

        /*
         * We are NOT collecting or processing
         * real financial information.
         *
         * This is only a simulated payment
         * for the project.
         */

        paymentMethodBox.setOnAction(event -> {

            String method =
                    paymentMethodBox.getValue();

            if ("UPI".equals(method)) {

                detailsLabel.setText("UPI ID");

                detailsField.setPromptText(
                        "Enter demo UPI ID"
                );

            } else if (
                    "Debit/Credit Card".equals(method)
            ) {

                detailsLabel.setText("Card Number");

                detailsField.setPromptText(
                        "Enter demo card number"
                );

            } else if (
                    "Net Banking".equals(method)
            ) {

                detailsLabel.setText(
                        "Bank / Customer ID"
                );

                detailsField.setPromptText(
                        "Enter demo banking ID"
                );

            } else if (
                    "Crosq Wallet".equals(method)
            ) {

                detailsLabel.setText(
                        "Wallet ID"
                );

                detailsField.setPromptText(
                        "Enter demo wallet ID"
                );
            }
        });

        Button backButton =
                new Button("Back");

        backButton.setPrefWidth(150);
        backButton.setPrefHeight(40);

        Button payButton =
                new Button("Pay ₹" +
                        selectedClass.getFare());

        payButton.setPrefWidth(180);
        payButton.setPrefHeight(40);

        backButton.setOnAction(event -> {

            ReviewBookingScreen reviewBookingScreen =
                    new ReviewBookingScreen();

            reviewBookingScreen.show(
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

        payButton.setOnAction(event -> {

            String paymentMethod =
                    paymentMethodBox.getValue();

            String paymentDetails =
                    detailsField.getText().trim();

            if (paymentMethod == null) {

                showError(
                        "Please select a payment method."
                );

                return;
            }

            if (paymentDetails.isEmpty()) {

                showError(
                        "Please enter the required payment details."
                );

                return;
            }

            /*
             * Simulated payment:
             *
             * No real payment gateway is used.
             * No financial transaction occurs.
             */

            showPaymentSuccess(
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
                    userId,
                    paymentMethod
            );
        });

        VBox layout =
                new VBox(
                        15,

                        titleLabel,
                        subtitleLabel,

                        amountLabel,

                        methodLabel,
                        paymentMethodBox,

                        detailsLabel,
                        detailsField,

                        backButton,
                        payButton
                );

        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        Scene scene =
                new Scene(layout, 600, 650);

        stage.setTitle(
                "Crosq - Payment"
        );

        stage.setScene(scene);
        stage.show();
    }

    private void showPaymentSuccess(
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
            int userId,
            String paymentMethod) {

        Label titleLabel =
                new Label("Payment Successful ✓");

        titleLabel.setStyle(
                "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;"
        );

        Label messageLabel =
                new Label(
                        "Payment completed successfully."
                );

        Label methodLabel =
                new Label(
                        "Payment Method: " +
                                paymentMethod
                );

        Label amountLabel =
                new Label(
                        "Amount Paid: ₹" +
                                selectedClass.getFare()
                );

        amountLabel.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;"
        );

        Label infoLabel =
                new Label(
                        "Your booking will now be confirmed."
                );

        Button confirmButton =
                new Button("Confirm Booking");

        confirmButton.setPrefWidth(220);
        confirmButton.setPrefHeight(40);

        confirmButton.setOnAction(event -> {

            boolean available =
                    reservationDAO.isClassAvailable(
                            train.getTrainNumber(),
                            selectedClass.getClassType(),
                            journeyDate.toString()
                    );

            if (!available) {

                showError(
                        "Sorry, this class is no longer available."
                );

                return;
            }

            String pnr =
                    PNRGenerator.generatePNR();

            boolean booked =
                    reservationDAO.createReservation(
                            userId,
                            pnr,
                            passengerName,
                            age,
                            gender,
                            train.getTrainNumber(),
                            selectedClass.getClassType(),
                            journeyDate.toString(),
                            train.getSource(),
                            train.getDestination(),
                            berthPreference,
                            quota,
                            selectedClass.getFare()
                    );

            if (booked) {

                showConfirmation(
                        stage,
                        train,
                        pnr,
                        passengerName,
                        age,
                        gender,
                        journeyDate,
                        selectedClass,
                        berthPreference,
                        quota
                );

            } else {

                showError(
                        "Booking failed. Please try again."
                );
            }
        });

        VBox layout =
                new VBox(
                        15,

                        titleLabel,
                        messageLabel,
                        methodLabel,
                        amountLabel,
                        infoLabel,
                        confirmButton
                );

        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        Scene scene =
                new Scene(layout, 600, 500);

        stage.setTitle(
                "Crosq - Payment Successful"
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

        dashboardButton.setOnAction(event ->
                stage.close()
        );

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
