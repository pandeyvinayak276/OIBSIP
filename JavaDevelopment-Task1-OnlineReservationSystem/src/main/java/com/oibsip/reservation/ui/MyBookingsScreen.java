package com.oibsip.reservation.ui;

import com.oibsip.reservation.db.ReservationDAO;
import com.oibsip.reservation.model.Reservation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class MyBookingsScreen {

    private final ReservationDAO reservationDAO =
            new ReservationDAO();

    public void show(
            Stage stage,
            String username,
            int userId
    ) {

        Label titleLabel =
                new Label("My Bookings");

        titleLabel.setStyle(
                "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;"
        );

        Label welcomeLabel =
                new Label("Bookings for " + username);

        ListView<Reservation> bookingListView =
                new ListView<>();

        List<Reservation> reservations =
                reservationDAO.getReservationsByUser(userId);

        bookingListView.getItems().addAll(reservations);

        bookingListView.setPrefHeight(450);
        bookingListView.setPrefWidth(800);

        bookingListView.setCellFactory(
                listView -> new ListCell<>() {

                    @Override
                    protected void updateItem(
                            Reservation reservation,
                            boolean empty
                    ) {

                        super.updateItem(
                                reservation,
                                empty
                        );

                        if (empty || reservation == null) {

                            setText(null);
                            setGraphic(null);

                        } else {

                            Label bookingLabel =
                                    new Label(
                                            "PNR: " +
                                                    reservation.getPnr() +
                                                    "\n" +

                                                    reservation.getTrainNumber() +
                                                    " - " +
                                                    reservation.getTrainName() +
                                                    "\n" +

                                                    reservation.getSourceStation() +
                                                    " → " +
                                                    reservation.getDestinationStation() +
                                                    "\n" +

                                                    "Passenger: " +
                                                    reservation.getPassengerName() +
                                                    " | Age: " +
                                                    reservation.getPassengerAge() +
                                                    " | " +
                                                    reservation.getGender() +
                                                    "\n" +

                                                    "Date: " +
                                                    reservation.getJourneyDate() +
                                                    " | Class: " +
                                                    reservation.getClassType() +
                                                    "\n" +

                                                    "Berth: " +
                                                    reservation.getBerthPreference() +
                                                    " | Quota: " +
                                                    reservation.getQuota() +
                                                    "\n" +

                                                    "Fare: ₹" +
                                                    reservation.getFare() +
                                                    " | Status: " +
                                                    reservation.getStatus()
                                    );

                            bookingLabel.setWrapText(true);

                            VBox bookingLayout =
                                    new VBox(8);

                            bookingLayout.setPadding(
                                    new Insets(10)
                            );

                            bookingLayout.getChildren().add(
                                    bookingLabel
                            );

                            if ("CONFIRMED".equalsIgnoreCase(
                                    reservation.getStatus()
                            )) {

                                Button cancelButton =
                                        new Button("Cancel Ticket");

                                cancelButton.setPrefWidth(150);
                                cancelButton.setPrefHeight(35);

                                cancelButton.setOnAction(event -> {

                                    Alert confirmation =
                                            new Alert(
                                                    Alert.AlertType.CONFIRMATION
                                            );

                                    confirmation.setTitle(
                                            "Cancel Ticket"
                                    );

                                    confirmation.setHeaderText(
                                            "Cancel this ticket?"
                                    );

                                    confirmation.setContentText(
                                            "PNR: " +
                                                    reservation.getPnr() +
                                                    "\n\n" +
                                                    "This action will cancel your ticket."
                                    );

                                    confirmation.showAndWait()
                                            .ifPresent(response -> {

                                                if (response ==
                                                        javafx.scene.control.ButtonType.OK) {

                                                    boolean cancelled =
                                                            reservationDAO.cancelReservation(
                                                                    reservation.getPnr(),
                                                                    userId
                                                            );

                                                    if (cancelled) {

                                                        Alert success =
                                                                new Alert(
                                                                        Alert.AlertType.INFORMATION
                                                                );

                                                        success.setTitle(
                                                                "Crosq"
                                                        );

                                                        success.setHeaderText(
                                                                "Ticket Cancelled"
                                                        );

                                                        success.setContentText(
                                                                "Your ticket with PNR " +
                                                                        reservation.getPnr() +
                                                                        " has been cancelled successfully."
                                                        );

                                                        success.showAndWait();

                                                        // Refresh My Bookings
                                                        show(
                                                                stage,
                                                                username,
                                                                userId
                                                        );

                                                    } else {

                                                        Alert error =
                                                                new Alert(
                                                                        Alert.AlertType.ERROR
                                                                );

                                                        error.setTitle(
                                                                "Crosq"
                                                        );

                                                        error.setHeaderText(
                                                                "Cancellation Failed"
                                                        );

                                                        error.setContentText(
                                                                "Unable to cancel this ticket. " +
                                                                        "It may already be cancelled."
                                                        );

                                                        error.showAndWait();
                                                    }
                                                }
                                            });
                                });

                                bookingLayout.getChildren().add(
                                        cancelButton
                                );
                            }

                            setGraphic(bookingLayout);
                            setText(null);

                            setPrefHeight(190);
                        }
                    }
                });

        Label emptyLabel =
                new Label();

        if (reservations.isEmpty()) {

            emptyLabel.setText(
                    "You don't have any bookings yet."
            );
        }

        Button backButton =
                new Button("Back to Dashboard");

        backButton.setPrefWidth(200);
        backButton.setPrefHeight(40);

        backButton.setOnAction(event -> {

            DashboardScreen dashboardScreen =
                    new DashboardScreen();

            dashboardScreen.show(
                    stage,
                    username,
                    userId
            );
        });

        VBox layout =
                new VBox(
                        15,
                        titleLabel,
                        welcomeLabel,
                        emptyLabel,
                        bookingListView,
                        backButton
                );

        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(30));

        Scene scene =
                new Scene(layout, 900, 700);

        stage.setTitle("Crosq - My Bookings");
        stage.setScene(scene);
        stage.show();
    }
}