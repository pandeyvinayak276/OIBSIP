# 🚆 Crosq - Online Ticket Booking System

Crosq is a Java-based desktop Online Ticket Booking System built using
JavaFX, JDBC, and SQLite.

It allows users to search trains, check class-wise availability and
fares, enter passenger details, book tickets, make payments, generate
e-tickets, view bookings, and cancel tickets.

## ✨ Features

- User Registration & Login
- Train Search by Source, Destination & Journey Date
- Class-wise Fare & Seat Availability
- Passenger Details
- Berth Preference & Quota Selection
- Review Booking & Payment
- E-Ticket Generation
- My Bookings
- Ticket Cancellation
- User-specific Booking History

## 🛠️ Tech Stack

- Java 17
- JavaFX
- JDBC
- SQLite
- Maven

- ## 🚀 How to Run

1. Clone the repository.
2. Open the project in IntelliJ IDEA.
3. Make sure Java 17+ and Maven are installed.
4. Load Maven dependencies.
5. Run the application using Maven.

6. ## 🏗️ Project Structure

```text
Crosq/
├── database/
│   └── reservation.db
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── oibsip/
│                   └── reservation/
│                       ├── db/
│                       ├── model/
│                       ├── ui/
│                       └── util/
├── screenshots/
├── pom.xml
└── README.md
