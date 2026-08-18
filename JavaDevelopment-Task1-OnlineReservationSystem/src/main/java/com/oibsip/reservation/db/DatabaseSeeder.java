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

    public static void seedTrainClasses() {

        String sql = """
                INSERT OR IGNORE INTO train_classes
                (train_number, class_type, fare, total_seats)
                VALUES (?, ?, ?, ?)
                """;

        Object[][] classes = {

                // Rajdhani Express
                {12301, "General", 500.0, 100},
                {12301, "Sleeper", 800.0, 80},
                {12301, "AC 3 Tier", 1500.0, 60},
                {12301, "AC 2 Tier", 2200.0, 40},
                {12301, "First Class", 3500.0, 20},

                // Mumbai Rajdhani
                {12951, "General", 500.0, 100},
                {12951, "Sleeper", 800.0, 80},
                {12951, "AC 3 Tier", 1500.0, 60},
                {12951, "AC 2 Tier", 2200.0, 40},
                {12951, "First Class", 3500.0, 20},

                // Bhopal Shatabdi
                {12002, "General", 450.0, 100},
                {12002, "Sleeper", 700.0, 80},
                {12002, "AC 3 Tier", 1300.0, 60},
                {12002, "AC 2 Tier", 1900.0, 40},
                {12002, "First Class", 3000.0, 20},

                // Mumbai Shatabdi
                {12009, "General", 400.0, 100},
                {12009, "Sleeper", 650.0, 80},
                {12009, "AC 3 Tier", 1200.0, 60},
                {12009, "AC 2 Tier", 1800.0, 40},
                {12009, "First Class", 2800.0, 20},

                // Karnataka Express
                {12627, "General", 550.0, 100},
                {12627, "Sleeper", 850.0, 80},
                {12627, "AC 3 Tier", 1600.0, 60},
                {12627, "AC 2 Tier", 2300.0, 40},
                {12627, "First Class", 3600.0, 20}
        };

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (Object[] classData : classes) {

                statement.setInt(1, (Integer) classData[0]);
                statement.setString(2, (String) classData[1]);
                statement.setDouble(3, (Double) classData[2]);
                statement.setInt(4, (Integer) classData[3]);

                statement.executeUpdate();
            }

            System.out.println("Train class data added successfully.");

        } catch (SQLException e) {
            System.err.println("Failed to add train class data.");
            e.printStackTrace();
        }
    }
}