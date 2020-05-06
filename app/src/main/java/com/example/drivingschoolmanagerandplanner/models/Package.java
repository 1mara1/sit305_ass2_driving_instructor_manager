package com.example.drivingschoolmanagerandplanner.models;

import java.util.Date;

public class Package {
    private Student student;
    private String name;
    private float amount;
    private int numberOfLessons;
    private Date datePurchased;

    public Package(Student student, String name, float amount, int numberOfLessons, Date datePurchased) {
        this.student = student;
        this.name = name;
        this.amount = amount;
        this.numberOfLessons = numberOfLessons;
        this.datePurchased = datePurchased;
    }

    public Student getStudent() {
        return student;
    }

    public String getName() {
        return name;
    }

    public float getAmount() {
        return amount;
    }

    public int getNumberOfLessons() {
        return numberOfLessons;
    }

    public Date getDatePurchased() {
        return datePurchased;
    }
}
