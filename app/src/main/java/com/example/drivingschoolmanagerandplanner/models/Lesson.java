package com.example.drivingschoolmanagerandplanner.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Date;

public class Lesson {
    private String day;
    // private Student student;
    private Package packageLesson;
    private String notes;
    private float amount;
    private String startTime;
    private String endTime;
    private String meetingAddress;
    private int isPackageLesson; // true for package lesson
    private int studentId;


    public Lesson(String notes, float amount, String day, String startTime, String endTime, String meetingAddress, int isPackageLesson, int studentId) {
       // this.student = student;
        this.packageLesson = packageLesson;
        this.notes = notes;
        this.amount = amount;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.meetingAddress = meetingAddress;
        this.isPackageLesson = isPackageLesson;
        this.studentId = studentId;
    }

   // public Student getStudent() {
     //   return student;
   // }

    public Package getPackageLesson() {
        return packageLesson;
    }

    public String getNotes() {
        return notes;
    }

    public int getIsPackageLesson() {
        return isPackageLesson;
    }

    public int getStudentId() {
        return studentId;
    }

    public float getAmount() {
        return amount;
    }

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getMeetingAddress() {
        return meetingAddress;
    }

}
