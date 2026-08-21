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

                // Existing trains
                {12301, "Rajdhani Express", "New Delhi", "Mumbai", "16:55", "08:35"},
                {12951, "Mumbai Rajdhani", "Mumbai", "New Delhi", "17:00", "08:35"},
                {12002, "Bhopal Shatabdi", "New Delhi", "Bhopal", "06:00", "14:00"},
                {12009, "Mumbai Shatabdi", "Mumbai", "Ahmedabad", "06:25", "12:45"},
                {12627, "Karnataka Express", "New Delhi", "Bengaluru", "21:15", "05:30"},

                // Additional trains
                {12952, "Mumbai Rajdhani", "New Delhi", "Mumbai", "17:00", "08:35"},
                {12001, "Bhopal Shatabdi", "Bhopal", "New Delhi", "15:00", "23:00"},
                {12628, "Karnataka Express", "Bengaluru", "New Delhi", "19:20", "05:30"},
                {12302, "Rajdhani Express", "Mumbai", "New Delhi", "17:00", "08:35"},
                {12010, "Mumbai Shatabdi", "Ahmedabad", "Mumbai", "14:40", "21:00"},

                {12314, "Sealdah Rajdhani", "New Delhi", "Kolkata", "16:30", "10:00"},
                {12313, "Sealdah Rajdhani", "Kolkata", "New Delhi", "16:50", "10:20"},

                {12434, "Chennai Rajdhani", "New Delhi", "Chennai", "15:55", "10:30"},
                {12433, "Chennai Rajdhani", "Chennai", "New Delhi", "06:10", "10:30"},

                {12957, "Swarna Jayanti Rajdhani", "Mumbai", "Delhi", "19:55", "09:00"},
                {12958, "Swarna Jayanti Rajdhani", "Delhi", "Mumbai", "19:55", "09:00"},

                {12127, "Intercity Express", "Mumbai", "Pune", "06:40", "10:00"},
                {12128, "Intercity Express", "Pune", "Mumbai", "17:50", "21:10"},

                {12245, "Duronto Express", "Mumbai", "Ahmedabad", "23:15", "05:30"},
                {12246, "Duronto Express", "Ahmedabad", "Mumbai", "23:00", "05:15"}
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

            System.out.println("Train data added successfully.");

        } catch (SQLException e) {
            System.err.println("Failed to add train data.");
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

                // 12301 Rajdhani Express
                {12301, "General", 500.0, 100},
                {12301, "Sleeper", 800.0, 80},
                {12301, "AC 3 Tier", 1500.0, 60},
                {12301, "AC 2 Tier", 2200.0, 40},
                {12301, "First Class", 3500.0, 20},

                // 12951 Mumbai Rajdhani
                {12951, "General", 500.0, 100},
                {12951, "Sleeper", 800.0, 80},
                {12951, "AC 3 Tier", 1500.0, 60},
                {12951, "AC 2 Tier", 2200.0, 40},
                {12951, "First Class", 3500.0, 20},

                // 12002 Bhopal Shatabdi
                {12002, "General", 450.0, 100},
                {12002, "Sleeper", 700.0, 80},
                {12002, "AC 3 Tier", 1300.0, 60},
                {12002, "AC 2 Tier", 1900.0, 40},
                {12002, "First Class", 3000.0, 20},

                // 12009 Mumbai Shatabdi
                {12009, "General", 400.0, 100},
                {12009, "Sleeper", 650.0, 80},
                {12009, "AC 3 Tier", 1200.0, 60},
                {12009, "AC 2 Tier", 1800.0, 40},
                {12009, "First Class", 2800.0, 20},

                // 12627 Karnataka Express
                {12627, "General", 550.0, 100},
                {12627, "Sleeper", 850.0, 80},
                {12627, "AC 3 Tier", 1600.0, 60},
                {12627, "AC 2 Tier", 2300.0, 40},
                {12627, "First Class", 3600.0, 20},

                // 12952 Mumbai Rajdhani
                {12952, "General", 500.0, 100},
                {12952, "Sleeper", 800.0, 80},
                {12952, "AC 3 Tier", 1500.0, 60},
                {12952, "AC 2 Tier", 2200.0, 40},
                {12952, "First Class", 3500.0, 20},

                // 12001 Bhopal Shatabdi
                {12001, "General", 450.0, 100},
                {12001, "Sleeper", 700.0, 80},
                {12001, "AC 3 Tier", 1300.0, 60},
                {12001, "AC 2 Tier", 1900.0, 40},
                {12001, "First Class", 3000.0, 20},

                // 12628 Karnataka Express
                {12628, "General", 550.0, 100},
                {12628, "Sleeper", 850.0, 80},
                {12628, "AC 3 Tier", 1600.0, 60},
                {12628, "AC 2 Tier", 2300.0, 40},
                {12628, "First Class", 3600.0, 20},

                // 12302 Rajdhani Express
                {12302, "General", 500.0, 100},
                {12302, "Sleeper", 800.0, 80},
                {12302, "AC 3 Tier", 1500.0, 60},
                {12302, "AC 2 Tier", 2200.0, 40},
                {12302, "First Class", 3500.0, 20},

                // 12010 Mumbai Shatabdi
                {12010, "General", 400.0, 100},
                {12010, "Sleeper", 650.0, 80},
                {12010, "AC 3 Tier", 1200.0, 60},
                {12010, "AC 2 Tier", 1800.0, 40},
                {12010, "First Class", 2800.0, 20},

                // 12314 Sealdah Rajdhani
                {12314, "General", 500.0, 100},
                {12314, "Sleeper", 800.0, 80},
                {12314, "AC 3 Tier", 1550.0, 60},
                {12314, "AC 2 Tier", 2250.0, 40},
                {12314, "First Class", 3500.0, 20},

                // 12313 Sealdah Rajdhani
                {12313, "General", 500.0, 100},
                {12313, "Sleeper", 800.0, 80},
                {12313, "AC 3 Tier", 1550.0, 60},
                {12313, "AC 2 Tier", 2250.0, 40},
                {12313, "First Class", 3500.0, 20},

                // 12434 Chennai Rajdhani
                {12434, "General", 550.0, 100},
                {12434, "Sleeper", 850.0, 80},
                {12434, "AC 3 Tier", 1650.0, 60},
                {12434, "AC 2 Tier", 2400.0, 40},
                {12434, "First Class", 3700.0, 20},

                // 12433 Chennai Rajdhani
                {12433, "General", 550.0, 100},
                {12433, "Sleeper", 850.0, 80},
                {12433, "AC 3 Tier", 1650.0, 60},
                {12433, "AC 2 Tier", 2400.0, 40},
                {12433, "First Class", 3700.0, 20},

                // 12957 Swarna Jayanti Rajdhani
                {12957, "General", 500.0, 100},
                {12957, "Sleeper", 800.0, 80},
                {12957, "AC 3 Tier", 1500.0, 60},
                {12957, "AC 2 Tier", 2200.0, 40},
                {12957, "First Class", 3500.0, 20},

                // 12958 Swarna Jayanti Rajdhani
                {12958, "General", 500.0, 100},
                {12958, "Sleeper", 800.0, 80},
                {12958, "AC 3 Tier", 1500.0, 60},
                {12958, "AC 2 Tier", 2200.0, 40},
                {12958, "First Class", 3500.0, 20},

                // 12127 Intercity Express
                {12127, "General", 350.0, 100},
                {12127, "Sleeper", 500.0, 80},
                {12127, "AC 3 Tier", 900.0, 60},
                {12127, "AC 2 Tier", 1300.0, 40},
                {12127, "First Class", 2000.0, 20},

                // 12128 Intercity Express
                {12128, "General", 350.0, 100},
                {12128, "Sleeper", 500.0, 80},
                {12128, "AC 3 Tier", 900.0, 60},
                {12128, "AC 2 Tier", 1300.0, 40},
                {12128, "First Class", 2000.0, 20},

                // 12245 Duronto Express
                {12245, "General", 450.0, 100},
                {12245, "Sleeper", 700.0, 80},
                {12245, "AC 3 Tier", 1250.0, 60},
                {12245, "AC 2 Tier", 1850.0, 40},
                {12245, "First Class", 2800.0, 20},

                // 12246 Duronto Express
                {12246, "General", 450.0, 100},
                {12246, "Sleeper", 700.0, 80},
                {12246, "AC 3 Tier", 1250.0, 60},
                {12246, "AC 2 Tier", 1850.0, 40},
                {12246, "First Class", 2800.0, 20}
        };

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            for (Object[] classData : classes) {

                statement.setInt(
                        1,
                        (Integer) classData[0]
                );

                statement.setString(
                        2,
                        (String) classData[1]
                );

                statement.setDouble(
                        3,
                        (Double) classData[2]
                );

                statement.setInt(
                        4,
                        (Integer) classData[3]
                );

                statement.executeUpdate();
            }

            System.out.println(
                    "Train class data added successfully."
            );

        } catch (SQLException e) {

            System.err.println(
                    "Failed to add train class data."
            );

            e.printStackTrace();
        }
    }
}