package com.oibsip.reservation.db;

import com.oibsip.reservation.model.Train;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TrainDAO {

    public List<Train> getAllTrains(){
        List<Train> trains = new ArrayList<>();

        String sql = """
                SELECT train_number, train_name, source,
                       destination, departure_time, arrival_time
                FROM trains
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()){

            while(resultSet.next()){
                Train train = new Train(
                        resultSet.getInt("train_number"),
                        resultSet.getString("train_name"),
                        resultSet.getString("source"),
                        resultSet.getString("destination"),
                        resultSet.getString("departure_time"),
                        resultSet.getString("arrival_time")
                );

                trains.add(train);
            }

        } catch (Exception e){
            System.err.println("Failed to fetch trains.");
            e.printStackTrace();
        }

        return trains;
    }

    public List<Train> getTrainsByRoute(
            String source,
            String destination
    ) {

        List<Train> trains = new ArrayList<>();

        String sql = """
            SELECT train_number,
                   train_name,
                   source,
                   destination,
                   departure_time,
                   arrival_time
            FROM trains
            WHERE LOWER(source) = LOWER(?)
              AND LOWER(destination) = LOWER(?)
            ORDER BY departure_time
            """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, source.trim());
            statement.setString(2, destination.trim());

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    Train train = new Train(
                            resultSet.getInt("train_number"),
                            resultSet.getString("train_name"),
                            resultSet.getString("source"),
                            resultSet.getString("destination"),
                            resultSet.getString("departure_time"),
                            resultSet.getString("arrival_time")
                    );

                    trains.add(train);
                }
            }

        } catch (Exception e) {

            System.err.println(
                    "Failed to search trains by route."
            );

            e.printStackTrace();
        }

        return trains;
    }
}
