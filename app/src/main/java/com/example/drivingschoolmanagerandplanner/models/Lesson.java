package com.example.drivingschoolmanagerandplanner.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Date;

public class Lesson {
    private Student student;
    private Package packageLesson;
    private String notes;
    private float amount;
    private Date startTime;
    private Date endTime;
    private String meetingAddress;
    private boolean isPackageLesson; // true for package lesson


    public Lesson(Student student, Package packageLesson, String notes, float amount, Date startTime, Date endTime, String meetingAddress, boolean isPackageLesson) {
        this.student = student;
        this.packageLesson = packageLesson;
        this.notes = notes;
        this.amount = amount;
        this.startTime = startTime;
        this.endTime = endTime;
        this.meetingAddress = meetingAddress;
        this.isPackageLesson = isPackageLesson;
    }

    public Student getStudent() {
        return student;
    }

    public Package getPackageLesson() {
        return packageLesson;
    }

    public String getNotes() {
        return notes;
    }

    public float getAmount() {
        return amount;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getMeetingAddress() {
        return meetingAddress;
    }

    public boolean isPackageLesson() {
        return isPackageLesson;
    }
}
