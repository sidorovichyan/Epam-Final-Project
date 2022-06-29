package com.epam.ynairlineepam.entity;

import java.util.HashMap;
import java.util.Objects;

public class Plane {

    private int id;

    private String departureAirport;

    private String departureDateTime;

    private String arrivalAirport;

    private String arrivalDateTime;

    private String planeType;

    private HashMap<String,Integer> numberPosition;

    private boolean teamFull;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isTeamFull() {
        return teamFull;
    }

    public void setTeamFull(boolean teamFull) {
        this.teamFull = teamFull;
    }

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(String departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public String getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(String arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    public String getPlaneType() {
        return planeType;
    }

    public void setPlaneType(String planeType) {
        this.planeType = planeType;
    }

    public HashMap<String, Integer> getNumberPosition() {
        return numberPosition;
    }

    public void setNumberPosition(HashMap<String, Integer> numberPosition) {
        this.numberPosition = numberPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plane)) return false;
        Plane plane = (Plane) o;
        return getId() == plane.getId() && isTeamFull() == plane.isTeamFull() && Objects.equals(getDepartureAirport(), plane.getDepartureAirport()) && Objects.equals(getDepartureDateTime(), plane.getDepartureDateTime()) && Objects.equals(getArrivalAirport(), plane.getArrivalAirport()) && Objects.equals(getArrivalDateTime(), plane.getArrivalDateTime()) && Objects.equals(getPlaneType(), plane.getPlaneType()) && Objects.equals(getNumberPosition(), plane.getNumberPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDepartureAirport(), getDepartureDateTime(), getArrivalAirport(), getArrivalDateTime(), getPlaneType(), getNumberPosition(), isTeamFull());
    }
}
