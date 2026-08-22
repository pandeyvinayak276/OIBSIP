package com.oibsip.reservation.ui;

import com.oibsip.reservation.db.UserDAO;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginScreen {

    private final UserDAO userDAO = new UserDAO();

    public void show(Stage stage) {

        // CROSQ logo
        Label logoLabel = new Label("CROSQ");

        logoLabel.setStyle(
                "-fx-font-size: 30px;" +
                        "-fx-font-weight: bold;"
        );

        // Main heading
        Label titleLabel =
                new Label("Train Ticket Booking");

        titleLabel.setStyle(
                "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;"
        );

        // Subtitle
        Label subtitleLabel =
                new Label("Login to continue your journey");

        subtitleLabel.setStyle(
                "-fx-font-size: 14px;"
        );

        // Username
        TextField usernameField =
                new TextField();

        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(300);
        usernameField.setPrefHeight(40);

        // Password
        PasswordField passwordField =
                new PasswordField();

        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(300);
        passwordField.setPrefHeight(40);

        // Login button
        Button loginButton =
                new Button("Login");

        loginButton.setPrefWidth(300);
        loginButton.setPrefHeight(42);

        // Register button
        Button registerButton =
                new Button("Create Account");

        registerButton.setPrefWidth(200);
        registerButton.setPrefHeight(38);

        // Message
        Label messageLabel =
                new Label();

        messageLabel.setStyle(
                "-fx-font-size: 13px;"
        );

        // Login action
        loginButton.setOnAction(event -> {

            String username =
                    usernameField.getText().trim();

            String password =
                    passwordField.getText();

            if (username.isEmpty() ||
                    password.isEmpty()) {

                messageLabel.setText(
                        "Please enter username and password."
                );

                return;
            }

            boolean authenticated =
                    userDAO.authenticateUser(
                            username,
                            password
                    );

            if (authenticated) {

                int userId =
                        userDAO.getUserId(username);

                if (userId == -1) {

                    messageLabel.setText(
                            "Unable to load user information."
                    );

                    return;
                }

                DashboardScreen dashboardScreen =
                        new DashboardScreen();

                dashboardScreen.show(
                        stage,
                        username,
                        userId
                );

            } else {

                messageLabel.setText(
                        "Invalid username or password."
                );
            }
        });

        // Register action
        registerButton.setOnAction(event -> {

            RegisterScreen registerScreen =
                    new RegisterScreen();

            registerScreen.show(stage);
        });

        // Login card
        VBox loginCard =
                new VBox(
                        15,
                        logoLabel,
                        titleLabel,
                        subtitleLabel,
                        usernameField,
                        passwordField,
                        loginButton,
                        registerButton,
                        messageLabel
                );

        loginCard.setAlignment(
                Pos.CENTER
        );

        loginCard.setPadding(
                new Insets(35)
        );

        loginCard.setMaxWidth(380);

        loginCard.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-radius: 12;" +
                        "-fx-border-color: #dddddd;"
        );

        // Main layout
        VBox root =
                new VBox(
                        loginCard
                );

        root.setAlignment(
                Pos.CENTER
        );

        root.setPadding(
                new Insets(30)
        );

        root.setStyle(
                "-fx-background-color: #f4f6f8;"
        );

        Scene scene =
                new Scene(
                        root,
                        600,
                        500
                );

        stage.setTitle(
                "Crosq - Train Ticket Booking"
        );

        stage.setScene(scene);
        stage.show();
    }
}