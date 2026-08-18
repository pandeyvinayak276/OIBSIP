package com.oibsip.reservation.db;

import com.oibsip.reservation.util.PasswordUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public boolean registerUser(String username, String password) {

        String sql = """
                INSERT INTO users (username, password_hash)
                VALUES (?, ?)
                """;

        String passwordHash = PasswordUtil.hashPassword(password);

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, passwordHash);

            statement.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Failed to register user.");
            e.printStackTrace();
            return false;
        }
    }

    public boolean authenticateUser(String username, String password) {

        String sql = """
                SELECT password_hash
                FROM users
                WHERE username = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    String storedHash =
                            resultSet.getString("password_hash");

                    return PasswordUtil.verifyPassword(
                            password,
                            storedHash
                    );
                }

                return false;
            }

        } catch (SQLException e) {
            System.err.println("Failed to authenticate user.");
            e.printStackTrace();
            return false;
        }
    }

    public int getUserId(String username) {

        String sql = """
            SELECT id
            FROM users
            WHERE username = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt("id");
                }

            }

        } catch (SQLException e) {
            System.err.println("Failed to fetch user ID.");
            e.printStackTrace();
        }

        return -1;
    }
}
