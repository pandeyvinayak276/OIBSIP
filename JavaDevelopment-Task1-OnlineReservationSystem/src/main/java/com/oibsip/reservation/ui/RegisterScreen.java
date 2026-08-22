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

public class RegisterScreen {

    private final UserDAO userDAO = new UserDAO();

    public void show(Stage stage) {

        // CROSQ logo
        Label logoLabel =
                new Label("CROSQ");

        logoLabel.setStyle(
                "-fx-font-size: 30px;" +
                        "-fx-font-weight: bold;"
        );

        // Heading
        Label titleLabel =
                new Label("Create your account");

        titleLabel.setStyle(
                "-fx-font-size: 24px;" +
                        "-fx-font-weight: bold;"
        );

        // Subtitle
        Label subtitleLabel =
                new Label("Join Crosq and start booking your journey");

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

        // Confirm password
        PasswordField confirmPasswordField =
                new PasswordField();

        confirmPasswordField.setPromptText("Confirm password");
        confirmPasswordField.setMaxWidth(300);
        confirmPasswordField.setPrefHeight(40);

        // Register button
        Button registerButton =
                new Button("Create Account");

        registerButton.setPrefWidth(300);
        registerButton.setPrefHeight(42);

        // Login button
        Button loginButton =
                new Button("Back to Login");

        loginButton.setPrefWidth(200);
        loginButton.setPrefHeight(38);

        // Message
        Label messageLabel =
                new Label();

        messageLabel.setStyle(
                "-fx-font-size: 13px;"
        );

        // Register action
        registerButton.setOnAction(event -> {

            String username =
                    usernameField.getText().trim();

            String password =
                    passwordField.getText();

            String confirmPassword =
                    confirmPasswordField.getText();

            if (username.isEmpty()
                    || password.isEmpty()
                    || confirmPassword.isEmpty()) {

                messageLabel.setText(
                        "Please fill in all fields."
                );

                return;
            }

            if (password.length() < 6) {

                messageLabel.setText(
                        "Password must be at least 6 characters."
                );

                return;
            }

            if (!password.equals(confirmPassword)) {

                messageLabel.setText(
                        "Passwords do not match."
                );

                return;
            }

            boolean registered =
                    userDAO.registerUser(
                            username,
                            password
                    );

            if (registered) {

                messageLabel.setText(
                        "Account created successfully!"
                );

                usernameField.clear();
                passwordField.clear();
                confirmPasswordField.clear();

            } else {

                messageLabel.setText(
                        "Username already exists."
                );
            }
        });

        // Back to login
        loginButton.setOnAction(event -> {

            LoginScreen loginScreen =
                    new LoginScreen();

            loginScreen.show(stage);
        });

        // Register card
        VBox registerCard =
                new VBox(
                        15,
                        logoLabel,
                        titleLabel,
                        subtitleLabel,
                        usernameField,
                        passwordField,
                        confirmPasswordField,
                        registerButton,
                        messageLabel,
                        loginButton
                );

        registerCard.setAlignment(
                Pos.CENTER
        );

        registerCard.setPadding(
                new Insets(35)
        );

        registerCard.setMaxWidth(380);

        registerCard.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-radius: 12;" +
                        "-fx-border-color: #dddddd;"
        );

        // Main layout
        VBox root =
                new VBox(
                        registerCard
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
                        600
                );

        stage.setTitle(
                "Crosq - Create Account"
        );

        stage.setScene(scene);
        stage.show();
    }
}