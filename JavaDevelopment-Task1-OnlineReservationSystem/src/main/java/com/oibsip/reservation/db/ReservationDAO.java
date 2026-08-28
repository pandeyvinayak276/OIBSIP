package com.oibsip.reservation.db;

import com.oibsip.reservation.model.Reservation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    public boolean createReservation(
            int userId,
            String pnr,
            String passengerName,
            int passengerAge,
            String gender,
            int trainNumber,
            String classType,
            String journeyDate,
            String sourceStation,
            String destinationStation,
            String berthPreference,
            String quota,
            double fare
    ) {

        String sql = """
                INSERT INTO reservations (
                    user_id,
                    pnr,
                    passenger_name,
                    passenger_age,
                    gender,
                    train_number,
                    class_type,
                    journey_date,
                    source_station,
                    destination_station,
                    berth_preference,
                    quota,
                    fare,
                    status
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            statement.setString(2, pnr);
            statement.setString(3, passengerName);
            statement.setInt(4, passengerAge);
            statement.setString(5, gender);
            statement.setInt(6, trainNumber);
            statement.setString(7, classType);
            statement.setString(8, journeyDate);
            statement.setString(9, sourceStation);
            statement.setString(10, destinationStation);
            statement.setString(11, berthPreference);
            statement.setString(12, quota);
            statement.setDouble(13, fare);
            statement.setString(14, "CONFIRMED");

            statement.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.err.println("Failed to create reservation.");
            e.printStackTrace();

            return false;
        }
    }

    public boolean isClassAvailable(
            int trainNumber,
            String classType,
            String journeyDate
    ) {

        String sql = """
                SELECT
                    tc.total_seats -
                    COALESCE(
                        (
                            SELECT COUNT(*)
                            FROM reservations r
                            WHERE r.train_number = tc.train_number
                              AND r.class_type = tc.class_type
                              AND r.journey_date = ?
                              AND r.status = 'CONFIRMED'
                        ),
                        0
                    ) AS available_seats
                FROM train_classes tc
                WHERE tc.train_number = ?
                  AND tc.class_type = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, journeyDate);
            statement.setInt(2, trainNumber);
            statement.setString(3, classType);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    int availableSeats =
                            resultSet.getInt("available_seats");

                    return availableSeats > 0;
                }
            }

        } catch (SQLException e) {

            System.err.println(
                    "Failed to check seat availability."
            );

            e.printStackTrace();
        }

        return false;
    }
    public List<Reservation>
    getReservationsByUser(int userId) {
        List<Reservation> reservations = new ArrayList<>();

        String sql = """
            SELECT
                r.pnr,
                r.passenger_name,
                r.passenger_age,
                r.gender,
                r.train_number,
                t.train_name,
                r.class_type,
                r.journey_date,
                r.source_station,
                r.destination_station,
                r.berth_preference,
                r.quota,
                r.fare,
                r.status
            FROM reservations r
            JOIN trains t
                ON r.train_number = t.train_number
            WHERE r.user_id = ?
            ORDER BY r.rowid DESC
            """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet =
                         statement.executeQuery()) {

                while (resultSet.next()) {

                    Reservation reservation =
                            new Reservation(
                                    resultSet.getString("pnr"),
                                    resultSet.getString("passenger_name"),
                                    resultSet.getInt("passenger_age"),
                                    resultSet.getString("gender"),
                                    resultSet.getInt("train_number"),
                                    resultSet.getString("train_name"),
                                    resultSet.getString("class_type"),
                                    resultSet.getString("journey_date"),
                                    resultSet.getString("source_station"),
                                    resultSet.getString("destination_station"),
                                    resultSet.getString("berth_preference"),
                                    resultSet.getString("quota"),
                                    resultSet.getDouble("fare"),
                                    resultSet.getString("status")
                            );

                    reservations.add(reservation);
                }

            }

        } catch (SQLException e) {

            System.err.println(
                    "Failed to fetch user bookings."
            );

            e.printStackTrace();
        }

        return reservations;
    }

    public boolean cancelReservation(
            String pnr,
            int userId
    ) {

        String sql = """
            UPDATE reservations
            SET status = 'CANCELLED'
            WHERE pnr = ?
              AND user_id = ?
              AND status = 'CONFIRMED'
            """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, pnr);
            statement.setInt(2, userId);

            int rowsUpdated =
                    statement.executeUpdate();

            return rowsUpdated > 0;

        } catch (SQLException e) {

            System.err.println(
                    "Failed to cancel reservation."
            );

            e.printStackTrace();

            return false;
        }
    }
}