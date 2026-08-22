package com.oibsip.reservation.ui;

import com.oibsip.reservation.db.TrainDAO;
import com.oibsip.reservation.model.Train;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SearchTrainsScreen {

    private final TrainDAO trainDAO = new TrainDAO();

    public void show(
            Stage stage,
            String username,
            int userId) {

        Label titleLabel = new Label("Search Trains");
        titleLabel.setStyle(
                "-fx-font-size: 28px; -fx-font-weight: bold;"
        );

        Label sourceLabel = new Label("From");

        TextField sourceField = new TextField();
        sourceField.setPromptText("Enter source station");

        Label destinationLabel = new Label("To");

        TextField destinationField = new TextField();
        destinationField.setPromptText("Enter destination station");

        // Journey Date
        Label journeyDateLabel = new Label("Journey Date");

        DatePicker journeyDatePicker = new DatePicker();
        journeyDatePicker.setPromptText("Select journey date");

        // Prevent selecting a past date
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

        Button searchButton =
                new Button("Search Trains");

        ListView<Train> trainListView =
                new ListView<>();

        Label statusLabel =
                new Label();

        // Show all trains initially
        trainListView.getItems().addAll(
                trainDAO.getAllTrains()
        );

        trainListView.setPrefHeight(300);
        trainListView.setPrefWidth(750);

        // Reserve button
        Button bookButton =
                new Button("Book Selected Train");

        bookButton.setPrefWidth(220);
        bookButton.setPrefHeight(40);

        bookButton.setOnAction(event -> {

            Train selectedTrain =
                    trainListView
                            .getSelectionModel()
                            .getSelectedItem();

            LocalDate selectedJourneyDate =
                    journeyDatePicker.getValue();

            if (selectedTrain == null) {
                return;
            }

            if (selectedJourneyDate == null) {
                statusLabel.setText(
                        "Please select a journey date."
                );
                return;
            }

            BookingScreen bookingScreen =
                    new BookingScreen();

            bookingScreen.show(
                    stage,
                    selectedTrain,
                    selectedJourneyDate,
                    username,
                    userId
            );
        });

        // Train list display
        trainListView.setCellFactory(
                listView -> new ListCell<>() {

                    @Override
                    protected void updateItem(
                            Train train,
                            boolean empty) {

                        super.updateItem(train, empty);

                        if (empty || train == null) {

                            setText(null);

                        } else {

                            setText(
                                    train.getTrainNumber() +
                                            " - " +
                                            train.getTrainName() +
                                            " | " +
                                            train.getSource() +
                                            " → " +
                                            train.getDestination() +
                                            " | " +
                                            train.getDepartureTime() +
                                            " - " +
                                            train.getArrivalTime()
                            );
                        }
                    }
                }
        );

        // Search button
        searchButton.setOnAction(event -> {

            String source =
                    sourceField.getText().trim();

            String destination =
                    destinationField.getText().trim();

            LocalDate journeyDate =
                    journeyDatePicker.getValue();

            trainListView.getItems().clear();

            statusLabel.setText("");

            // Validate source and destination
            if (source.isEmpty() ||
                    destination.isEmpty()) {

                statusLabel.setText(
                        "Please enter both source and destination."
                );

                return;
            }

            // Validate journey date
            if (journeyDate == null) {

                statusLabel.setText(
                        "Please select a journey date."
                );

                return;
            }

            // Prevent past date
            if (journeyDate.isBefore(
                    LocalDate.now())) {

                statusLabel.setText(
                        "Journey date cannot be in the past."
                );

                return;
            }

            // Search trains
            var trains =
                    trainDAO.getTrainsByRoute(
                            source,
                            destination
                    );

            trainListView
                    .getItems()
                    .addAll(trains);

            if (trains.isEmpty()) {

                statusLabel.setText(
                        "No trains found for this route."
                );

            } else {

                DateTimeFormatter formatter =
                        DateTimeFormatter.ofPattern(
                                "dd MMM yyyy"
                        );

                statusLabel.setText(
                        trains.size() +
                                " train(s) found for " +
                                journeyDate.format(formatter) +
                                "."
                );
            }
        });

        // Search layout
        HBox searchBox =
                new HBox(15);

        searchBox.setAlignment(
                Pos.CENTER
        );

        VBox sourceBox =
                new VBox(
                        5,
                        sourceLabel,
                        sourceField
                );

        VBox destinationBox =
                new VBox(
                        5,
                        destinationLabel,
                        destinationField
                );

        VBox dateBox =
                new VBox(
                        5,
                        journeyDateLabel,
                        journeyDatePicker
                );

        searchBox.getChildren().addAll(
                sourceBox,
                destinationBox,
                dateBox,
                searchButton
        );

        // Main layout
        VBox root =
                new VBox(25);

        root.setAlignment(
                Pos.TOP_CENTER
        );

        root.setPadding(
                new Insets(40)
        );

        root.getChildren().addAll(
                titleLabel,
                searchBox,
                statusLabel,
                trainListView,
                bookButton
        );

        Scene scene =
                new Scene(
                        root,
                        1100,
                        600
                );

        stage.setTitle(
                "Crosq - Search Trains"
        );

        stage.setScene(scene);
        stage.show();
    }
}