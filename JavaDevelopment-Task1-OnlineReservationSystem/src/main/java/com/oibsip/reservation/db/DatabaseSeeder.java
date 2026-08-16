package com.oibsip.reservation.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseSeeder {

    public static void seedTrains() {

        String sql = """
                INSERT OR IGNORE INTO trains (train_number, train_name)
                VALUES (?, ?)
                """;

        Object[][] trains = {
                {12301, "Rajdhani Express"},
                {12951, "Mumbai Rajdhani"},
                {12002, "Bhopal Shatabdi"},
                {12009, "Mumbai Shatabdi"},
                {12627, "Karnataka Express"}
        };

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (Object[] train : trains) {
                statement.setInt(1, (Integer) train[0]);
                statement.setString(2, (String) train[1]);
                statement.executeUpdate();
            }

            System.out.println("Sample trains added successfully.");

        } catch (SQLException e) {
            System.err.println("Failed to add sample trains.");
            e.printStackTrace();
        }
    }
}
