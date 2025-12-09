package com.tolgaziftci.routeplanner.entity;

public class Location {
    private final int id;
    private final String name;
    private final String country;
    private final String city;
    private final String locationCode;

    public Location(int id, String name, String country, String city, String locationCode) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.city = city;
        this.locationCode = locationCode;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getLocationCode() {
        return locationCode;
    }
}
