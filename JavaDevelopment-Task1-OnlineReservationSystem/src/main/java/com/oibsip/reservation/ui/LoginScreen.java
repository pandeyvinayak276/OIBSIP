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
    public void show(Stage stage){
        Label titleLabel = new Label("Online Reservation System");
        titleLabel.setStyle(
                "-fx-font-sixe: 24px;" +
                "-fx-font-weight: bold;"
        );

        Label subtitleLabel = new Label("Login to continue");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(280);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(280);

        Button loginButton = new Button("Login");
        loginButton.setPrefWidth(280);

        Button registerButton = new Button("Create Account");
        registerButton.setPrefWidth(180);

        Label messageLabel = new Label();

        loginButton.setOnAction(event -> {

            String username = usernameField.getText().trim();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Please enter username and password.");
                return;
            }

            boolean authenticated =
                    userDAO.authenticateUser(username, password);

            if (authenticated) {
                DashboardScreen dashboardScreen = new DashboardScreen();
                dashboardScreen.show(stage, username);
            } else {
                messageLabel.setText("Invalid username or password.");
            }
        });

        registerButton.setOnAction(event -> {

            RegisterScreen registerScreen = new RegisterScreen();
            registerScreen.show(stage);
        });

        VBox layout = new VBox(
                12,
                titleLabel,
                subtitleLabel,
                usernameField,
                passwordField,
                loginButton,
                registerButton,
                messageLabel
        );

        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        Scene scene = new Scene(layout, 500, 400);

        stage.setTitle("Login - Online Reservation System");
        stage.setScene(scene);
        stage.show();
    }
}
