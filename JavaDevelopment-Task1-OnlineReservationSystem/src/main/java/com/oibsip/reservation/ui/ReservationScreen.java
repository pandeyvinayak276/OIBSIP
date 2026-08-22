package com.oibsip.reservation.ui;

import com.oibsip.reservation.db.ReservationDAO;
import com.oibsip.reservation.db.TrainClassDAO;
import com.oibsip.reservation.model.Train;
import com.oibsip.reservation.model.TrainClass;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class ReservationScreen {

    private final TrainClassDAO trainClassDAO = new TrainClassDAO();
    private final ReservationDAO reservationDAO = new ReservationDAO();

    private void showError(String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Crosq");
        alert.setHeaderText("Booking Error");
        alert.setContentText(message);

        alert.showAndWait();
    }

    public void show(
            Stage stage,
            Train train,
            LocalDate journeyDate,
            String username,
            int userId) {

        Label titleLabel = new Label("Book Ticket");

        titleLabel.setStyle(
                "-fx-font-size: 26px;" +
                        "-fx-font-weight: bold;"
        );

        Label trainLabel = new Label(
                train.getTrainNumber() + " - " +
                        train.getTrainName()
        );

        Label routeLabel = new Label(
                train.getSource() + " → " +
                        train.getDestination()
        );

        // Passenger Name
        Label passengerLabel = new Label("Passenger Name");

        TextField passengerField = new TextField();
        passengerField.setPromptText("Enter passenger name");
        passengerField.setMaxWidth(300);

        // Age
        Label ageLabel = new Label("Age");

        TextField ageField = new TextField();
        ageField.setPromptText("Enter age");
        ageField.setMaxWidth(300);

        // Gender
        Label genderLabel = new Label("Gender");

        ComboBox<String> genderBox = new ComboBox<>();

        genderBox.getItems().addAll(
                "Male",
                "Female",
                "Other"
        );

        genderBox.setPromptText("Select gender");
        genderBox.setMaxWidth(300);

        // Journey Date
        Label dateLabel = new Label("Journey Date");

        DatePicker journeyDatePicker =
                new DatePicker(journeyDate);

        journeyDatePicker.setDayCellFactory(
                picker -> new DateCell() {

                    @Override
                    public void updateItem(
                            LocalDate date,
                            boolean empty) {

                        super.updateItem(date, empty);

                        if (!empty &&
                                date.isBefore(LocalDate.now())) {

                            setDisable(true);
                        }
                    }
                }
        );

        journeyDatePicker.setMaxWidth(300);

        // Class
        Label classLabel = new Label("Class");

        ComboBox<TrainClass> classBox =
                new ComboBox<>();

        classBox.setPromptText("Select class");
        classBox.setMaxWidth(300);

        List<TrainClass> trainClasses =
                trainClassDAO.getClassesForTrain(
                        train.getTrainNumber()
                );

        classBox.getItems().addAll(trainClasses);

        classBox.setCellFactory(
                listView -> new ListCell<>() {

                    @Override
                    protected void updateItem(
                            TrainClass trainClass,
                            boolean empty) {

                        super.updateItem(
                                trainClass,
                                empty
                        );

                        if (empty || trainClass == null) {

                            setText(null);

                        } else {

                            setText(
                                    trainClass.getClassType() +
                                            " | ₹" +
                                            trainClass.getFare() +
                                            " | Available: " +
                                            trainClass.getAvailableSeats()
                            );
                        }
                    }
                }
        );

        classBox.setButtonCell(
                new ListCell<>() {

                    @Override
                    protected void updateItem(
                            TrainClass trainClass,
                            boolean empty) {

                        super.updateItem(
                                trainClass,
                                empty
                        );

                        if (empty || trainClass == null) {

                            setText(null);

                        } else {

                            setText(
                                    trainClass.getClassType() +
                                            " | ₹" +
                                            trainClass.getFare()
                            );
                        }
                    }
                }
        );

        // Fare and availability
        Label fareLabel =
                new Label("Fare: -");

        Label availabilityLabel =
                new Label("Availability: -");

        classBox.setOnAction(event -> {

            TrainClass selectedClass =
                    classBox.getSelectionModel()
                            .getSelectedItem();

            LocalDate selectedJourneyDate =
                    journeyDatePicker.getValue();

            if (selectedClass != null) {

                fareLabel.setText(
                        "Fare: ₹" +
                                selectedClass.getFare()
                );

                if (selectedJourneyDate != null) {

                    int availableSeats =
                            trainClassDAO.getAvailableSeats(
                                    train.getTrainNumber(),
                                    selectedClass.getClassType(),
                                    selectedJourneyDate.toString()
                            );

                    availabilityLabel.setText(
                            "Available Seats: " +
                                    availableSeats
                    );

                } else {

                    availabilityLabel.setText(
                            "Availability: Select journey date"
                    );
                }
            }
        });

        journeyDatePicker.setOnAction(event -> {

            TrainClass selectedClass =
                    classBox.getSelectionModel()
                            .getSelectedItem();

            LocalDate selectedJourneyDate =
                    journeyDatePicker.getValue();

            if (selectedClass != null &&
                    selectedJourneyDate != null) {

                int availableSeats =
                        trainClassDAO.getAvailableSeats(
                                train.getTrainNumber(),
                                selectedClass.getClassType(),
                                selectedJourneyDate.toString()
                        );

                availabilityLabel.setText(
                        "Available Seats: " +
                                availableSeats
                );
            }
        });

        // Berth Preference
        Label berthLabel =
                new Label("Berth Preference");

        ComboBox<String> berthBox =
                new ComboBox<>();

        berthBox.getItems().addAll(
                "No Preference",
                "Lower",
                "Middle",
                "Upper",
                "Side Lower",
                "Side Upper"
        );

        berthBox.setValue("No Preference");
        berthBox.setMaxWidth(300);

        // Quota
        Label quotaLabel =
                new Label("Quota");

        ComboBox<String> quotaBox =
                new ComboBox<>();

        quotaBox.getItems().addAll(
                "General",
                "Ladies",
                "Senior Citizen",
                "Defence",
                "Physically Challenged"
        );

        quotaBox.setValue("General");
        quotaBox.setMaxWidth(300);

        // Review button
        Button reviewButton =
                new Button("Review Booking");

        reviewButton.setPrefWidth(220);
        reviewButton.setPrefHeight(40);

        reviewButton.setOnAction(event -> {

            String passengerName =
                    passengerField.getText().trim();

            String ageText =
                    ageField.getText().trim();

            String gender =
                    genderBox.getValue();

            LocalDate selectedJourneyDate =
                    journeyDatePicker.getValue();

            TrainClass selectedClass =
                    classBox.getValue();

            String berthPreference =
                    berthBox.getValue();

            String quota =
                    quotaBox.getValue();

            // Validate passenger name
            if (passengerName.isEmpty()) {

                showError(
                        "Please enter passenger name."
                );

                return;
            }

            if (!passengerName.matches(
                    "[a-zA-Z][a-zA-Z .'-]{1,49}"
            )) {

                showError(
                        "Passenger name must contain only letters, spaces, " +
                                "and common name characters."
                );

                return;
            }

            // Validate age
            if (ageText.isEmpty()) {

                showError(
                        "Please enter passenger age."
                );

                return;
            }

            int age;

            try {

                age = Integer.parseInt(ageText);

            } catch (NumberFormatException e) {

                showError(
                        "Age must be a valid number."
                );

                return;
            }

            if (age < 1 || age > 120) {

                showError(
                        "Please enter a valid age."
                );

                return;
            }

            // Validate gender
            if (gender == null) {

                showError(
                        "Please select gender."
                );

                return;
            }

            // Validate journey date
            if (selectedJourneyDate == null) {

                showError(
                        "Please select journey date."
                );

                return;
            }

            if (selectedJourneyDate.isBefore(LocalDate.now())) {

                showError(
                        "Journey date cannot be in the past."
                );

                return;
            }

            // Validate class
            if (selectedClass == null) {

                showError(
                        "Please select a class."
                );

                return;
            }

            // Check availability before review
            boolean available =
                    reservationDAO.isClassAvailable(
                            train.getTrainNumber(),
                            selectedClass.getClassType(),
                            selectedJourneyDate.toString()
                    );

            if (!available) {

                showError(
                        "Sorry, this class is currently full."
                );

                return;
            }

            /*
             * Create the review screen.
             *
             * IMPORTANT:
             * No reservation is saved here.
             * No PNR is generated here.
             */
            ReviewBookingScreen reviewBookingScreen =
                    new ReviewBookingScreen();

            reviewBookingScreen.show(
                    stage,
                    train,
                    passengerName,
                    age,
                    gender,
                    selectedJourneyDate,
                    selectedClass,
                    berthPreference,
                    quota,
                    username,
                    userId
            );
        });

        VBox layout = new VBox(
                10,

                titleLabel,

                trainLabel,
                routeLabel,

                passengerLabel,
                passengerField,

                ageLabel,
                ageField,

                genderLabel,
                genderBox,

                dateLabel,
                journeyDatePicker,

                classLabel,
                classBox,

                fareLabel,
                availabilityLabel,

                berthLabel,
                berthBox,

                quotaLabel,
                quotaBox,

                reviewButton
        );

        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        Scene scene =
                new Scene(layout, 600, 850);

        stage.setTitle("Crosq - Book Ticket");

        stage.setScene(scene);
        stage.show();
    }
}