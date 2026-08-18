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
                    train_name TEXT NOT NULL,
                    source TEXT NOT NULL,
                    destination TEXT NOT NULL,
                    departure_time TEXT NOT NULL,
                    arrival_time TEXT NOT NULL
                );
                """;

        String trainClassesTable = """
                CREATE TABLE IF NOT EXISTS train_classes (
                    train_number INTEGER NOT NULL,
                    class_type TEXT NOT NULL,
                    fare REAL NOT NULL,
                    total_seats INTEGER NOT NULL,
                    PRIMARY KEY (train_number, class_type),
                    FOREIGN KEY (train_number)
                        REFERENCES trains(train_number)
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
                    FOREIGN KEY (train_number)
                        REFERENCES trains(train_number)
                );
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(usersTable);
            statement.execute(trainsTable);
            statement.execute(trainClassesTable);
            statement.execute(reservationsTable);

            // Upgrade existing reservations table
            addReservationColumns(connection);

            System.out.println("Database initialized successfully.");

        } catch (SQLException e) {
            System.err.println("Database initialization failed.");
            e.printStackTrace();
        }
    }

    private static void addReservationColumns(Connection connection)
            throws SQLException {

        try (Statement statement = connection.createStatement()) {

            addColumnIfMissing(
                    statement,
                    "reservations",
                    "passenger_age",
                    "INTEGER"
            );

            addColumnIfMissing(
                    statement,
                    "reservations",
                    "gender",
                    "TEXT"
            );

            addColumnIfMissing(
                    statement,
                    "reservations",
                    "berth_preference",
                    "TEXT"
            );

            addColumnIfMissing(
                    statement,
                    "reservations",
                    "quota",
                    "TEXT"
            );

            addColumnIfMissing(
                    statement,
                    "reservations",
                    "fare",
                    "REAL"
            );

            addColumnIfMissing(
                    statement,
                    "reservations",
                    "status",
                    "TEXT DEFAULT 'CONFIRMED'"
            );
        }
    }

    private static void addColumnIfMissing(
            Statement statement,
            String tableName,
            String columnName,
            String columnDefinition) throws SQLException {

        try (var resultSet = statement.executeQuery(
                "PRAGMA table_info(" + tableName + ")")) {

            while (resultSet.next()) {

                String existingColumn =
                        resultSet.getString("name");

                if (existingColumn.equalsIgnoreCase(columnName)) {
                    return;
                }
            }
        }

        statement.executeUpdate(
                "ALTER TABLE " + tableName +
                        " ADD COLUMN " + columnName +
                        " " + columnDefinition
        );

        System.out.println(
                "Added column: " + columnName
        );
    }
}