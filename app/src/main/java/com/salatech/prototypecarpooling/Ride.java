package com.salatech.prototypecarpooling;

public class Ride {
    private String name;
    private String details;

    public Ride(String name, String details) {
        this.name = name;
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }
}

