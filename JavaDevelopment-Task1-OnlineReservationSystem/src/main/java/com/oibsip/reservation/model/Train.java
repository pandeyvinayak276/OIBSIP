package com.oibsip.reservation.model;

public class Train {

    private int trainNumber;
    private String trainName;
    private String source;
    private String destination;
    private String departureTime;
    private String arrivalTime;

    public Train(int trainNumber, String trainName, String source,
                 String destination, String departureTime, String arrivalTime) {

        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public int getTrainNumber(){
        return trainNumber;
    }

    public String getTrainName(){
        return trainName;
    }

    public String getSource(){
        return source;
    }

    public String getDestination(){
        return destination;
    }

    public String getDepartureTime(){
        return departureTime;
    }

    public String getArrivalTime(){
        return arrivalTime;
    }
}
