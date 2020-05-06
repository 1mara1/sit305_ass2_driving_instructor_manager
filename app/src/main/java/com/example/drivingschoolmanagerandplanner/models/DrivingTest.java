package com.example.drivingschoolmanagerandplanner.models;

import java.util.Date;

public class DrivingTest {

    private Student studentDriver;
    private String location;
    private Date date, time;
    private int bookingNumber;
    private boolean result;

    public DrivingTest(Student studentDriver, String location, Date date, Date time, int bookingNumber, boolean result) {
        this.studentDriver = studentDriver;
        this.location = location;
        this.date = date;
        this.time = time;
        this.bookingNumber = bookingNumber;
        this.result = result;
    }

    public Student getStudentDriver() {
        return studentDriver;
    }

    public String getLocation() {
        return location;
    }

    public Date getDate() {
        return date;
    }

    public Date getTime() {
        return time;
    }

    public int getBookingNumber() {
        return bookingNumber;
    }

    public boolean isResult() {
        return result;
    }
}
