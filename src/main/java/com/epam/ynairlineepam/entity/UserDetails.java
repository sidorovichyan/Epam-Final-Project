package com.epam.ynairlineepam.entity;

import java.util.Objects;

public class UserDetails extends User {

    private String fullName;

    private String gender;

    private int age;

    private String phone;

    private String address;

    private String position;

    public UserDetails() {
    }

    public UserDetails(String fullName, String gender, int age, String phone, String address, String position) {
        this.fullName = fullName;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
        this.address = address;
        this.position = position;
    }

    public UserDetails(int id, String login, String password, String role, String fullName, String gender, int age, String phone, String address, String position) {
        super(id, login, password, role);
        this.fullName = fullName;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
        this.address = address;
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "fullName='" + fullName + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDetails)) return false;
        if (!super.equals(o)) return false;
        UserDetails that = (UserDetails) o;
        return getAge() == that.getAge() && Objects.equals(getFullName(), that.getFullName()) && Objects.equals(getGender(), that.getGender()) && Objects.equals(getPhone(), that.getPhone()) && Objects.equals(getAddress(), that.getAddress()) && Objects.equals(getPosition(), that.getPosition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getFullName(), getGender(), getAge(), getPhone(), getAddress(), getPosition());
    }
}
