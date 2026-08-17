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

        Label logoLabel = new Label("CROSQ");
        logoLabel.setStyle(
                "-fx-font-size: 26px;" +
                        "-fx-font-weight: bold;"
        );

        Label titleLabel = new Label("Create your account");
        titleLabel.setStyle(
                "-fx-font-size: 22px;" +
                        "-fx-font-weight: bold;"
        );

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(280);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(280);

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm password");
        confirmPasswordField.setMaxWidth(280);

        Button registerButton = new Button("Create Account");
        registerButton.setPrefWidth(280);
        registerButton.setPrefHeight(40);

        Button loginButton = new Button("Back to Login");
        loginButton.setPrefWidth(180);

        Label messageLabel = new Label();

        registerButton.setOnAction(event -> {

            String username = usernameField.getText().trim();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

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
                    userDAO.registerUser(username, password);

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

        loginButton.setOnAction(event -> {
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.show(stage);
        });

        VBox layout = new VBox(
                12,
                logoLabel,
                titleLabel,
                usernameField,
                passwordField,
                confirmPasswordField,
                registerButton,
                messageLabel,
                loginButton
        );

        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        Scene scene = new Scene(layout, 500, 500);

        stage.setTitle("Create Account - Crosq");
        stage.setScene(scene);
        stage.show();
    }
}
