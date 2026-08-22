package com.oibsip.reservation.ui;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileOutputStream;

import com.oibsip.reservation.db.ReservationDAO;
import com.oibsip.reservation.model.Train;
import com.oibsip.reservation.model.TrainClass;
import com.oibsip.reservation.util.PNRGenerator;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class PaymentScreen {

    private final ReservationDAO reservationDAO =
            new ReservationDAO();

    private void showError(String message) {

        Alert alert =
                new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Crosq");
        alert.setHeaderText("Payment Error");
        alert.setContentText(message);

        alert.showAndWait();
    }

    public void show(
            Stage stage,
            Train train,
            String passengerName,
            int age,
            String gender,
            LocalDate journeyDate,
            TrainClass selectedClass,
            String berthPreference,
            String quota,
            String username,
            int userId) {

        Label titleLabel =
                new Label("Payment");

        titleLabel.setStyle(
                "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;"
        );

        Label subtitleLabel =
                new Label(
                        "Choose a payment method"
                );

        Label amountLabel =
                new Label(
                        "Amount to Pay: ₹" +
                                selectedClass.getFare()
                );

        amountLabel.setStyle(
                "-fx-font-size: 20px;" +
                        "-fx-font-weight: bold;"
        );

        // Payment method
        Label methodLabel =
                new Label("Payment Method");

        ComboBox<String> paymentMethodBox =
                new ComboBox<>();

        paymentMethodBox.getItems().addAll(
                "UPI",
                "Debit/Credit Card",
                "Net Banking",
                "Crosq Wallet"
        );

        paymentMethodBox.setPromptText(
                "Select payment method"
        );

        paymentMethodBox.setMaxWidth(300);

        // Payment details
        Label detailsLabel =
                new Label("Payment Details");

        TextField detailsField =
                new TextField();

        detailsField.setPromptText(
                "Enter payment details"
        );

        detailsField.setMaxWidth(300);

        /*
         * We are NOT collecting or processing
         * real financial information.
         *
         * This is only a simulated payment
         * for the project.
         */

        paymentMethodBox.setOnAction(event -> {

            String method =
                    paymentMethodBox.getValue();

            if ("UPI".equals(method)) {

                detailsLabel.setText("UPI ID");

                detailsField.setPromptText(
                        "Enter demo UPI ID"
                );

            } else if (
                    "Debit/Credit Card".equals(method)
            ) {

                detailsLabel.setText("Card Number");

                detailsField.setPromptText(
                        "Enter demo card number"
                );

            } else if (
                    "Net Banking".equals(method)
            ) {

                detailsLabel.setText(
                        "Bank / Customer ID"
                );

                detailsField.setPromptText(
                        "Enter demo banking ID"
                );

            } else if (
                    "Crosq Wallet".equals(method)
            ) {

                detailsLabel.setText(
                        "Wallet ID"
                );

                detailsField.setPromptText(
                        "Enter demo wallet ID"
                );
            }
        });

        Button backButton =
                new Button("Back");

        backButton.setPrefWidth(150);
        backButton.setPrefHeight(40);

        Button payButton =
                new Button("Pay ₹" +
                        selectedClass.getFare());

        payButton.setPrefWidth(180);
        payButton.setPrefHeight(40);

        backButton.setOnAction(event -> {

            ReviewBookingScreen reviewBookingScreen =
                    new ReviewBookingScreen();

            reviewBookingScreen.show(
                    stage,
                    train,
                    passengerName,
                    age,
                    gender,
                    journeyDate,
                    selectedClass,
                    berthPreference,
                    quota,
                    username,
                    userId
            );
        });

        payButton.setOnAction(event -> {

            String paymentMethod =
                    paymentMethodBox.getValue();

            String paymentDetails =
                    detailsField.getText().trim();

            if (paymentMethod == null) {

                showError(
                        "Please select a payment method."
                );

                return;
            }

            if (paymentDetails.isEmpty()) {

                showError(
                        "Please enter the required payment details."
                );

                return;
            }

            /*
             * Simulated payment:
             *
             * No real payment gateway is used.
             * No financial transaction occurs.
             */

            showPaymentSuccess(
                    stage,
                    train,
                    passengerName,
                    age,
                    gender,
                    journeyDate,
                    selectedClass,
                    berthPreference,
                    quota,
                    username,
                    userId,
                    paymentMethod
            );
        });

        VBox layout =
                new VBox(
                        15,

                        titleLabel,
                        subtitleLabel,

                        amountLabel,

                        methodLabel,
                        paymentMethodBox,

                        detailsLabel,
                        detailsField,

                        backButton,
                        payButton
                );

        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        Scene scene =
                new Scene(layout, 600, 650);

        stage.setTitle(
                "Crosq - Payment"
        );

        stage.setScene(scene);
        stage.show();
    }

    private void showPaymentSuccess(
            Stage stage,
            Train train,
            String passengerName,
            int age,
            String gender,
            LocalDate journeyDate,
            TrainClass selectedClass,
            String berthPreference,
            String quota,
            String username,
            int userId,
            String paymentMethod) {

        Label titleLabel =
                new Label("Payment Successful ✓");

        titleLabel.setStyle(
                "-fx-font-size: 28px;" +
                        "-fx-font-weight: bold;"
        );

        Label messageLabel =
                new Label(
                        "Payment completed successfully."
                );

        Label methodLabel =
                new Label(
                        "Payment Method: " +
                                paymentMethod
                );

        Label amountLabel =
                new Label(
                        "Amount Paid: ₹" +
                                selectedClass.getFare()
                );

        amountLabel.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;"
        );

        Label infoLabel =
                new Label(
                        "Your booking will now be confirmed."
                );

        Button confirmButton =
                new Button("Confirm Booking");

        confirmButton.setPrefWidth(220);
        confirmButton.setPrefHeight(40);

        confirmButton.setOnAction(event -> {

            boolean available =
                    reservationDAO.isClassAvailable(
                            train.getTrainNumber(),
                            selectedClass.getClassType(),
                            journeyDate.toString()
                    );

            if (!available) {

                showError(
                        "Sorry, this class is no longer available."
                );

                return;
            }

            String pnr =
                    PNRGenerator.generatePNR();

            boolean booked =
                    reservationDAO.createReservation(
                            userId,
                            pnr,
                            passengerName,
                            age,
                            gender,
                            train.getTrainNumber(),
                            selectedClass.getClassType(),
                            journeyDate.toString(),
                            train.getSource(),
                            train.getDestination(),
                            berthPreference,
                            quota,
                            selectedClass.getFare()
                    );

            if (booked) {

                showConfirmation(
                        stage,
                        train,
                        pnr,
                        passengerName,
                        age,
                        gender,
                        journeyDate,
                        selectedClass,
                        berthPreference,
                        quota
                );

            } else {

                showError(
                        "Booking failed. Please try again."
                );
            }
        });

        VBox layout =
                new VBox(
                        15,

                        titleLabel,
                        messageLabel,
                        methodLabel,
                        amountLabel,
                        infoLabel,
                        confirmButton
                );

        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));

        Scene scene =
                new Scene(layout, 600, 500);

        stage.setTitle(
                "Crosq - Payment Successful"
        );

        stage.setScene(scene);
        stage.show();
    }

    private void showConfirmation(
            Stage stage,
            Train train,
            String pnr,
            String passengerName,
            int age,
            String gender,
            LocalDate journeyDate,
            TrainClass selectedClass,
            String berthPreference,
            String quota) {

        Label crosqLabel =
                new Label("CROSQ");

        crosqLabel.setStyle(
                "-fx-font-size: 30px;" +
                        "-fx-font-weight: bold;"
        );

        Label ticketLabel =
                new Label("E-TICKET");

        ticketLabel.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;"
        );

        VBox header =
                new VBox(
                        3,
                        crosqLabel,
                        ticketLabel
                );

        header.setAlignment(Pos.CENTER);

        Label statusLabel =
                new Label("✓  BOOKING CONFIRMED");

        statusLabel.setStyle(
                "-fx-font-size: 17px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 8 15 8 15;" +
                        "-fx-border-color: green;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;"
        );

        Label pnrCaption =
                new Label("PNR NUMBER");

        pnrCaption.setStyle(
                "-fx-font-size: 11px;" +
                        "-fx-font-weight: bold;"
        );

        Label pnrLabel =
                new Label(pnr);

        pnrLabel.setStyle(
                "-fx-font-size: 22px;" +
                        "-fx-font-weight: bold;"
        );

        VBox pnrBox =
                new VBox(
                        3,
                        pnrCaption,
                        pnrLabel
                );

        pnrBox.setAlignment(Pos.CENTER);

        Label trainLabel =
                new Label(
                        train.getTrainNumber() +
                                "  •  " +
                                train.getTrainName()
                );

        trainLabel.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;"
        );

        Label sourceLabel =
                new Label(train.getSource());

        sourceLabel.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;"
        );

        Label arrowLabel =
                new Label("→");

        arrowLabel.setStyle(
                "-fx-font-size: 22px;" +
                        "-fx-font-weight: bold;"
        );

        Label destinationLabel =
                new Label(train.getDestination());

        destinationLabel.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;"
        );

        javafx.scene.layout.HBox routeBox =
                new javafx.scene.layout.HBox(
                        15,
                        sourceLabel,
                        arrowLabel,
                        destinationLabel
                );

        routeBox.setAlignment(Pos.CENTER);

        Label passengerHeading =
                new Label("PASSENGER DETAILS");

        passengerHeading.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;"
        );

        Label passengerLabel =
                new Label(
                        "Passenger: " +
                                passengerName
                );

        Label ageGenderLabel =
                new Label(
                        "Age: " +
                                age +
                                "    |    Gender: " +
                                gender
                );

        VBox passengerBox =
                new VBox(
                        6,
                        passengerHeading,
                        passengerLabel,
                        ageGenderLabel
                );

        passengerBox.setPadding(
                new Insets(10)
        );

        Label journeyHeading =
                new Label("JOURNEY DETAILS");

        journeyHeading.setStyle(
                "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;"
        );

        Label dateLabel =
                new Label(
                        "Journey Date: " +
                                journeyDate
                );

        Label classLabel =
                new Label(
                        "Class: " +
                                selectedClass.getClassType()
                );

        Label berthLabel =
                new Label(
                        "Berth Preference: " +
                                berthPreference
                );

        Label quotaLabel =
                new Label(
                        "Quota: " +
                                quota
                );

        VBox journeyBox =
                new VBox(
                        6,
                        journeyHeading,
                        dateLabel,
                        classLabel,
                        berthLabel,
                        quotaLabel
                );

        journeyBox.setPadding(
                new Insets(10)
        );

        Label fareCaption =
                new Label("TOTAL FARE");

        fareCaption.setStyle(
                "-fx-font-size: 12px;" +
                        "-fx-font-weight: bold;"
        );

        Label fareLabel =
                new Label(
                        "₹" +
                                selectedClass.getFare()
                );

        fareLabel.setStyle(
                "-fx-font-size: 22px;" +
                        "-fx-font-weight: bold;"
        );

        VBox fareBox =
                new VBox(
                        3,
                        fareCaption,
                        fareLabel
                );

        fareBox.setAlignment(Pos.CENTER_RIGHT);

        VBox ticket =
                new VBox(
                        14,
                        header,
                        statusLabel,
                        pnrBox,
                        trainLabel,
                        routeBox,
                        passengerBox,
                        journeyBox,
                        fareBox
                );

        ticket.setAlignment(Pos.CENTER);
        ticket.setPadding(
                new Insets(25)
        );

        ticket.setMaxWidth(500);

        ticket.setStyle(
                "-fx-border-color: #999999;" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 10;" +
                        "-fx-background-color: white;" +
                        "-fx-background-radius: 10;"
        );

        // Download PDF button
        Button downloadButton =
                new Button("Download E-Ticket PDF");

        downloadButton.setPrefWidth(220);
        downloadButton.setPrefHeight(40);

        downloadButton.setOnAction(event -> {

            FileChooser fileChooser =
                    new FileChooser();

            fileChooser.setTitle(
                    "Save Crosq E-Ticket"
            );

            fileChooser.setInitialFileName(
                    "Crosq_E-Ticket_" +
                            pnr +
                            ".pdf"
            );

            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(
                            "PDF Files",
                            "*.pdf"
                    )
            );

            File file =
                    fileChooser.showSaveDialog(stage);

            if (file != null) {

                generateETicketPDF(
                        file,
                        train,
                        pnr,
                        passengerName,
                        age,
                        gender,
                        journeyDate,
                        selectedClass,
                        berthPreference,
                        quota
                );
            }
        });

        Button dashboardButton =
                new Button("Back to Dashboard");

        dashboardButton.setPrefWidth(220);
        dashboardButton.setPrefHeight(40);

        dashboardButton.setOnAction(event ->
                stage.close()
        );

        VBox buttonBox =
                new VBox(
                        10,
                        downloadButton,
                        dashboardButton
                );

        buttonBox.setAlignment(Pos.CENTER);

        VBox layout =
                new VBox(
                        20,
                        ticket,
                        buttonBox
                );

        layout.setAlignment(Pos.CENTER);
        layout.setPadding(
                new Insets(25)
        );

        layout.setStyle(
                "-fx-background-color: #f4f6f8;"
        );

        Scene scene =
                new Scene(
                        layout,
                        650,
                        900
                );

        stage.setTitle(
                "Crosq - E-Ticket"
        );

        stage.setScene(scene);
        stage.show();
    }

    private void generateETicketPDF(
            File file,
            Train train,
            String pnr,
            String passengerName,
            int age,
            String gender,
            LocalDate journeyDate,
            TrainClass selectedClass,
            String berthPreference,
            String quota) {

        Document document =
                new Document();

        try {

            PdfWriter.getInstance(
                    document,
                    new FileOutputStream(file)
            );

            document.open();

            // Fonts
            Font titleFont =
                    new Font(
                            Font.HELVETICA,
                            24,
                            Font.BOLD
                    );

            Font headingFont =
                    new Font(
                            Font.HELVETICA,
                            14,
                            Font.BOLD
                    );

            Font normalFont =
                    new Font(
                            Font.HELVETICA,
                            11,
                            Font.NORMAL
                    );

            Font boldFont =
                    new Font(
                            Font.HELVETICA,
                            11,
                            Font.BOLD
                    );

            // Header
            Paragraph title =
                    new Paragraph(
                            "CROSQ",
                            titleFont
                    );

            title.setAlignment(
                    Paragraph.ALIGN_CENTER
            );

            document.add(title);

            Paragraph ticketTitle =
                    new Paragraph(
                            "E-TICKET",
                            headingFont
                    );

            ticketTitle.setAlignment(
                    Paragraph.ALIGN_CENTER
            );

            document.add(ticketTitle);

            document.add(
                    new Paragraph(" ")
            );

            // Confirmation
            Paragraph confirmed =
                    new Paragraph(
                            "BOOKING CONFIRMED",
                            headingFont
                    );

            confirmed.setAlignment(
                    Paragraph.ALIGN_CENTER
            );

            document.add(confirmed);

            document.add(
                    new Paragraph(" ")
            );

            // PNR
            Paragraph pnrParagraph =
                    new Paragraph(
                            "PNR: " + pnr,
                            boldFont
                    );

            pnrParagraph.setAlignment(
                    Paragraph.ALIGN_CENTER
            );

            document.add(pnrParagraph);

            document.add(
                    new Paragraph(" ")
            );

            // Train
            document.add(
                    new Paragraph(
                            "TRAIN DETAILS",
                            headingFont
                    )
            );

            document.add(
                    new Paragraph(
                            "Train Number: " +
                                    train.getTrainNumber(),
                            normalFont
                    )
            );

            document.add(
                    new Paragraph(
                            "Train Name: " +
                                    train.getTrainName(),
                            normalFont
                    )
            );

            document.add(
                    new Paragraph(
                            "Route: " +
                                    train.getSource() +
                                    " → " +
                                    train.getDestination(),
                            normalFont
                    )
            );

            document.add(
                    new Paragraph(" ")
            );

            // Passenger
            document.add(
                    new Paragraph(
                            "PASSENGER DETAILS",
                            headingFont
                    )
            );

            document.add(
                    new Paragraph(
                            "Passenger Name: " +
                                    passengerName,
                            normalFont
                    )
            );

            document.add(
                    new Paragraph(
                            "Age: " +
                                    age,
                            normalFont
                    )
            );

            document.add(
                    new Paragraph(
                            "Gender: " +
                                    gender,
                            normalFont
                    )
            );

            document.add(
                    new Paragraph(" ")
            );

            // Journey
            document.add(
                    new Paragraph(
                            "JOURNEY DETAILS",
                            headingFont
                    )
            );

            PdfPTable journeyTable =
                    new PdfPTable(2);

            journeyTable.setWidthPercentage(100);

            addPDFRow(
                    journeyTable,
                    "Journey Date",
                    journeyDate.toString(),
                    normalFont,
                    boldFont
            );

            addPDFRow(
                    journeyTable,
                    "Class",
                    selectedClass.getClassType(),
                    normalFont,
                    boldFont
            );

            addPDFRow(
                    journeyTable,
                    "Berth Preference",
                    berthPreference,
                    normalFont,
                    boldFont
            );

            addPDFRow(
                    journeyTable,
                    "Quota",
                    quota,
                    normalFont,
                    boldFont
            );

            addPDFRow(
                    journeyTable,
                    "Status",
                    "CONFIRMED",
                    normalFont,
                    boldFont
            );

            document.add(
                    journeyTable
            );

            document.add(
                    new Paragraph(" ")
            );

            // Fare
            Paragraph fare =
                    new Paragraph(
                            "TOTAL FARE: ₹" +
                                    selectedClass.getFare(),
                            headingFont
                    );

            fare.setAlignment(
                    Paragraph.ALIGN_RIGHT
            );

            document.add(fare);

            document.add(
                    new Paragraph(" ")
            );

            Paragraph footer =
                    new Paragraph(
                            "Thank you for booking with Crosq.",
                            normalFont
                    );

            footer.setAlignment(
                    Paragraph.ALIGN_CENTER
            );

            document.add(footer);

            document.close();

            Alert success =
                    new Alert(
                            Alert.AlertType.INFORMATION
                    );

            success.setTitle("Crosq");
            success.setHeaderText(
                    "E-Ticket Downloaded"
            );

            success.setContentText(
                    "Your E-Ticket has been saved successfully."
            );

            success.showAndWait();

        } catch (Exception e) {

            e.printStackTrace();

            Alert error =
                    new Alert(
                            Alert.AlertType.ERROR
                    );

            error.setTitle("Crosq");
            error.setHeaderText(
                    "PDF Generation Failed"
            );

            error.setContentText(
                    "Unable to generate the E-Ticket PDF."
            );

            error.showAndWait();
        }
    }

    private void addPDFRow(
            PdfPTable table,
            String label,
            String value,
            Font normalFont,
            Font boldFont) {

        PdfPCell labelCell =
                new PdfPCell(
                        new Phrase(
                                label,
                                boldFont
                        )
                );

        PdfPCell valueCell =
                new PdfPCell(
                        new Phrase(
                                value,
                                normalFont
                        )
                );

        table.addCell(labelCell);
        table.addCell(valueCell);
    }
}
