package com.oibsip.reservation.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {

        String usersTable = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT NOT NULL UNIQUE,
                    password_hash TEXT NOT NULL
                );
                """;

        String trainsTable = """
                CREATE TABLE IF NOT EXISTS trains (
                    train_number INTEGER PRIMARY KEY,
                    train_name TEXT NOT NULL
                );
                """;

        String reservationsTable = """
                CREATE TABLE IF NOT EXISTS reservations (
                    pnr TEXT PRIMARY KEY,
                    passenger_name TEXT NOT NULL,
                    train_number INTEGER NOT NULL,
                    class_type TEXT NOT NULL,
                    journey_date TEXT NOT NULL,
                    source_station TEXT NOT NULL,
                    destination_station TEXT NOT NULL,
                    FOREIGN KEY (train_number) REFERENCES trains(train_number)
                );
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(usersTable);
            statement.execute(trainsTable);
            statement.execute(reservationsTable);

            System.out.println("Database initialized successfully.");

        } catch (SQLException e) {
            System.err.println("Database initialization failed.");
            e.printStackTrace();
        }
    }
}
