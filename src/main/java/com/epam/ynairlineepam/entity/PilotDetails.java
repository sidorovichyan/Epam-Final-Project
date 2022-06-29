package com.epam.ynairlineepam.entity;

import java.util.Objects;

public class PilotDetails extends UserDetails{

    private int flyingHours;

    private String qualification;

    public PilotDetails(int flyingHours, String qualification) {
        this.flyingHours = flyingHours;
        this.qualification = qualification;
    }

    public PilotDetails(String fullName, String gender, int age, String phone, String address, String position, int flyingHours, String qualification) {
        super(fullName, gender, age, phone, address, position);
        this.flyingHours = flyingHours;
        this.qualification = qualification;
    }

    public PilotDetails(int id, String login, String password, String role, String fullName, String gender, int age, String phone, String address, String position, int flyingHours, String qualification) {
        super(id, login, password, role, fullName, gender, age, phone, address, position);
        this.flyingHours = flyingHours;
        this.qualification = qualification;
    }

    public PilotDetails() {

    }

    public int getFlyingHours() {
        return flyingHours;
    }

    public void setFlyingHours(int flyingHours) {
        this.flyingHours = flyingHours;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    @Override
    public String toString() {
        return super.toString()+" PilotDetails{" +
                "flyingHours=" + flyingHours +
                ", qualification='" + qualification + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PilotDetails)) return false;
        if (!super.equals(o)) return false;
        PilotDetails that = (PilotDetails) o;
        return getFlyingHours() == that.getFlyingHours() && Objects.equals(getQualification(), that.getQualification());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getFlyingHours(), getQualification());
    }
}
