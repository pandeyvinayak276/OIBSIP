package com.oibsip.reservation.db;

import com.oibsip.reservation.model.TrainClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TrainClassDAO {

    public List<TrainClass> getClassesForTrain(int trainNumber) {

        List<TrainClass> classes = new ArrayList<>();

        String sql = """
                SELECT
                    tc.train_number,
                    tc.class_type,
                    tc.fare,
                    tc.total_seats,
                    tc.total_seats -
                    COALESCE(
                        (
                            SELECT COUNT(*)
                            FROM reservations r
                            WHERE r.train_number = tc.train_number
                            AND r.class_type = tc.class_type
                        ),
                        0
                    ) AS available_seats
                FROM train_classes tc
                WHERE tc.train_number = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, trainNumber);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                TrainClass trainClass = new TrainClass(
                        resultSet.getInt("train_number"),
                        resultSet.getString("class_type"),
                        resultSet.getDouble("fare"),
                        resultSet.getInt("total_seats"),
                        resultSet.getInt("available_seats")
                );

                classes.add(trainClass);
            }

        } catch (Exception e) {
            System.err.println("Failed to fetch train classes.");
            e.printStackTrace();
        }

        return classes;
    }
}
