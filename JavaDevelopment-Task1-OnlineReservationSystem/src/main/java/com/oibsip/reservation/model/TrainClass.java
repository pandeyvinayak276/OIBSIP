package com.oibsip.reservation.model;

public class TrainClass {

    private int trainNumber;
    private String classType;
    private double fare;
    private int totalSeats;
    private int availableSeats;

    public TrainClass(int trainNumber, String classType,
                      double fare, int totalSeats, int availableSeats) {

        this.trainNumber = trainNumber;
        this.classType = classType;
        this.fare = fare;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public String getClassType() {
        return classType;
    }

    public double getFare() {
        return fare;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }
}
