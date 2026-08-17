package com.oibsip.reservation.ui;

import com.oibsip.reservation.db.TrainDAO;
import com.oibsip.reservation.model.Train;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SearchTrainsScreen {

    private final TrainDAO trainDAO = new TrainDAO();

    public void show(Stage stage) {

        Label titleLabel = new Label("Search Trains");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        Label sourceLabel = new Label("From");
        TextField sourceField = new TextField();
        sourceField.setPromptText("Enter source station");

        Label destinationLabel = new Label("To");
        TextField destinationField = new TextField();
        destinationField.setPromptText("Enter destination station");

        Button searchButton = new Button("Search Trains");

        ListView<String> trainListView = new ListView<>();

        for (Train train : trainDAO.getAllTrains()) {

            String trainInfo =
                    train.getTrainNumber() + " - " +
                            train.getTrainName() + " | " +
                            train.getSource() + " → " +
                            train.getDestination() + " | " +
                            train.getDepartureTime() + " - " +
                            train.getArrivalTime();

            trainListView.getItems().add(trainInfo);
        }

        trainListView.setPrefHeight(300);
        trainListView.setPrefWidth(750);

        searchButton.setOnAction(event -> {

            String source = sourceField.getText().trim();
            String destination = destinationField.getText().trim();

            trainListView.getItems().clear();

            for (Train train : trainDAO.getAllTrains()) {

                boolean sourceMatches =
                        source.isEmpty() ||
                                train.getSource().equalsIgnoreCase(source);

                boolean destinationMatches =
                        destination.isEmpty() ||
                                train.getDestination().equalsIgnoreCase(destination);

                if (sourceMatches && destinationMatches) {

                    String trainInfo =
                            train.getTrainNumber() + " - " +
                                    train.getTrainName() + " | " +
                                    train.getSource() + " → " +
                                    train.getDestination() + " | " +
                                    train.getDepartureTime() + " - " +
                                    train.getArrivalTime();

                    trainListView.getItems().add(trainInfo);
                }
            }

            if (trainListView.getItems().isEmpty()) {
                trainListView.getItems().add("No trains found.");
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
                trainListView
        );

        Scene scene = new Scene(root, 900, 600);

        stage.setTitle("Crosq - Search Trains");
        stage.setScene(scene);
        stage.show();
    }
}
