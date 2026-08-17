package com.oibsip.reservation.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseSeeder {

    public static void seedTrains() {

        String sql = """
                INSERT OR IGNORE INTO trains
                (train_number, train_name, source, destination, departure_time, arrival_time)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        Object[][] trains = {
                {12301, "Rajdhani Express", "New Delhi", "Mumbai", "16:55", "08:35"},
                {12951, "Mumbai Rajdhani", "Mumbai", "New Delhi", "17:00", "08:35"},
                {12002, "Bhopal Shatabdi", "New Delhi", "Bhopal", "06:00", "14:00"},
                {12009, "Mumbai Shatabdi", "Mumbai", "Ahmedabad", "06:25", "12:45"},
                {12627, "Karnataka Express", "New Delhi", "Bengaluru", "21:15", "05:30"}
        };

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (Object[] train : trains) {

                statement.setInt(1, (Integer) train[0]);
                statement.setString(2, (String) train[1]);
                statement.setString(3, (String) train[2]);
                statement.setString(4, (String) train[3]);
                statement.setString(5, (String) train[4]);
                statement.setString(6, (String) train[5]);

                statement.executeUpdate();
            }

            System.out.println("Sample trains added successfully.");

        } catch (SQLException e) {
            System.err.println("Failed to add sample trains.");
            e.printStackTrace();
        }
    }
}
