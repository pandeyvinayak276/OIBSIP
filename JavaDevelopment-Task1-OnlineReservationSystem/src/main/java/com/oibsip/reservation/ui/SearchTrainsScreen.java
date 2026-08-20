package com.oibsip.reservation.ui;

import com.oibsip.reservation.db.TrainDAO;
import com.oibsip.reservation.model.Train;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SearchTrainsScreen {

    private final TrainDAO trainDAO = new TrainDAO();

    public void show(
            Stage stage,
            String username,
            int userId) {

        Label titleLabel = new Label("Search Trains");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        Label sourceLabel = new Label("From");
        TextField sourceField = new TextField();
        sourceField.setPromptText("Enter source station");

        Label destinationLabel = new Label("To");
        TextField destinationField = new TextField();
        destinationField.setPromptText("Enter destination station");

        Button searchButton = new Button("Search Trains");

        ListView<Train> trainListView = new ListView<>();
        Label statusLabel = new Label();

        trainListView.getItems().addAll(trainDAO.getAllTrains());

        trainListView.setPrefHeight(300);
        trainListView.setPrefWidth(750);

        Button reserveButton = new Button("Reserve Selected Train");
        reserveButton.setPrefWidth(220);
        reserveButton.setPrefHeight(40);

        reserveButton.setOnAction(event -> {

            Train selectedTrain =
                    trainListView.getSelectionModel().getSelectedItem();

            if (selectedTrain == null) {
                return;
            }

            ReservationScreen reservationScreen =
                    new ReservationScreen();

            reservationScreen.show(
                    stage,
                    selectedTrain,
                    username,
                    userId
            );
        });

        trainListView.setCellFactory(listView -> new ListCell<>() {

            @Override
            protected void updateItem(Train train, boolean empty) {
                super.updateItem(train, empty);

                if (empty || train == null) {
                    setText(null);
                } else {
                    setText(
                            train.getTrainNumber() + " - " +
                                    train.getTrainName() + " | " +
                                    train.getSource() + " → " +
                                    train.getDestination() + " | " +
                                    train.getDepartureTime() + " - " +
                                    train.getArrivalTime()
                    );
                }
            }
        });

        searchButton.setOnAction(event -> {

            String source =
                    sourceField.getText().trim();

            String destination =
                    destinationField.getText().trim();

            trainListView.getItems().clear();
            statusLabel.setText("");

            if (source.isEmpty() || destination.isEmpty()) {

                statusLabel.setText(
                        "Please enter both source and destination."
                );

                return;
            }

            var trains =
                    trainDAO.getTrainsByRoute(
                            source,
                            destination
                    );

            trainListView.getItems().addAll(trains);

            if (trains.isEmpty()) {

                statusLabel.setText(
                        "No trains found for this route."
                );
            } else {

                statusLabel.setText(
                        trains.size() +
                                " train(s) found."
                );
            }
        });

        HBox searchBox = new HBox(15);
        searchBox.setAlignment(Pos.CENTER);

        VBox sourceBox = new VBox(5, sourceLabel, sourceField);
        VBox destinationBox = new VBox(5, destinationLabel, destinationField);

        searchBox.getChildren().addAll(sourceBox, destinationBox, searchButton);

        VBox root = new VBox(25);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(40));

        root.getChildren().addAll(
                titleLabel,
                searchBox,
                statusLabel,
                trainListView,
                reserveButton
        );

        Scene scene = new Scene(root, 900, 600);

        stage.setTitle("Crosq - Search Trains");
        stage.setScene(scene);
        stage.show();
    }
}
