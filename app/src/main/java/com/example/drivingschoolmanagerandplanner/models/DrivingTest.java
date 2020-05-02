package com.example.drivingschoolmanagerandplanner.models;

public class DrivingTest {

    Student studentDriver;
    String date, time, location;
    int bookingNumber;
    boolean result;

    public DrivingTest(Student studentDriver, String date, String time, String location, int bookingNumber, boolean result) {
        this.studentDriver = studentDriver;
        this.date = date;
        this.time = time;
        this.location = location;
        this.bookingNumber = bookingNumber;
        this.result = result;
    }

    public Student getStudentDriver() {
        return studentDriver;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    public int getBookingNumber() {
        return bookingNumber;
    }

    public boolean isResult() {
        return result;
    }
}
