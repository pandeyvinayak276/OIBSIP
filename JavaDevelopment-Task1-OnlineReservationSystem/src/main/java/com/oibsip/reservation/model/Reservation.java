package com.oibsip.reservation.model;

public class Reservation {

    private String pnr;
    private String passengerName;
    private int passengerAge;
    private String gender;

    private int trainNumber;
    private String trainName;

    private String classType;
    private String journeyDate;

    private String sourceStation;
    private String destinationStation;

    private String berthPreference;
    private String quota;

    private double fare;
    private String status;

    public Reservation(
            String pnr,
            String passengerName,
            int passengerAge,
            String gender,
            int trainNumber,
            String trainName,
            String classType,
            String journeyDate,
            String sourceStation,
            String destinationStation,
            String berthPreference,
            String quota,
            double fare,
            String status
    ) {
        this.pnr = pnr;
        this.passengerName = passengerName;
        this.passengerAge = passengerAge;
        this.gender = gender;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.classType = classType;
        this.journeyDate = journeyDate;
        this.sourceStation = sourceStation;
        this.destinationStation = destinationStation;
        this.berthPreference = berthPreference;
        this.quota = quota;
        this.fare = fare;
        this.status = status;
    }

    public String getPnr() {
        return pnr;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public int getPassengerAge() {
        return passengerAge;
    }

    public String getGender() {
        return gender;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public String getClassType() {
        return classType;
    }

    public String getJourneyDate() {
        return journeyDate;
    }

    public String getSourceStation() {
        return sourceStation;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public String getBerthPreference() {
        return berthPreference;
    }

    public String getQuota() {
        return quota;
    }

    public double getFare() {
        return fare;
    }

    public String getStatus() {
        return status;
    }
}