package com.example.drivingschoolmanagerandplanner.models;

import java.util.Date;

public class DrivingTest {

    private String location, date, time, bookingNumber;
    private int result, studentId;

    public DrivingTest(String location, String date, String time, String bookingNumber, int result, int studentId) {
        this.location = location;
        this.date = date;
        this.time = time;
        this.bookingNumber = bookingNumber;
        this.result = result;
        this.studentId = studentId;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public int getResult() {
        return result;
    }

    public int getStudentId() {
        return studentId;
    }

}
