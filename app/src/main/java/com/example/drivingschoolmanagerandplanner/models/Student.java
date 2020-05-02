package com.example.drivingschoolmanagerandplanner.models;

public class Student {

    String firstName, lastName, phone, email, addressLine, suburb, state, country ;
    int postcode;

    public Student(String firstName, String lastName, String phone, String email, String addressLine, String suburb, String state, String country, int postcode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.addressLine = addressLine;
        this.suburb = suburb;
        this.state = state;
        this.country = country;
        this.postcode = postcode;
    }


    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddressLine() {
        return addressLine;
    }

    public String getSuburb() {
        return suburb;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public int getPostcode() {
        return postcode;
    }
    public String getFullName() {
        return firstName + " " + lastName;
    }

}
