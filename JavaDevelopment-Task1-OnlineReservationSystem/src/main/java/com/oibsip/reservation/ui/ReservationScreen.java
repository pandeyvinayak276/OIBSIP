package com.oibsip.reservation.ui;

import com.oibsip.reservation.db.TrainClassDAO;
import com.oibsip.reservation.model.Train;
import com.oibsip.reservation.model.TrainClass;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class ReservationScreen {

    private final TrainClassDAO trainClassDAO = new TrainClassDAO();

    public void show(Stage stage, Train train) {

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

        DatePicker journeyDatePicker = new DatePicker();

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

        ComboBox<TrainClass> classBox = new ComboBox<>();

        classBox.setPromptText("Select class");
        classBox.setMaxWidth(300);

        List<TrainClass> trainClasses =
                trainClassDAO.getClassesForTrain(
                        train.getTrainNumber()
                );

        classBox.getItems().addAll(trainClasses);

        classBox.setCellFactory(
                listView -> new javafx.scene.control.ListCell<>() {

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
                new javafx.scene.control.ListCell<>() {

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
        Label fareLabel = new Label("Fare: -");

        Label availabilityLabel =
                new Label("Availability: -");

        classBox.setOnAction(event -> {

            TrainClass selectedClass =
                    classBox.getSelectionModel()
                            .getSelectedItem();

            if (selectedClass != null) {

                fareLabel.setText(
                        "Fare: ₹" +
                                selectedClass.getFare()
                );

                availabilityLabel.setText(
                        "Available Seats: " +
                                selectedClass.getAvailableSeats()
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

        // Book button
        Button bookButton =
                new Button("Book Ticket");

        bookButton.setPrefWidth(220);
        bookButton.setPrefHeight(40);

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

                bookButton
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