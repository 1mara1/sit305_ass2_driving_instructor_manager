package com.example.drivingschoolmanagerandplanner.models;

public class Student {

    private int studentId;
    private String firstName, lastName, email, addressLine, suburb, state, country ;
    private String tempPostcode;
    private int phone, postcode;

    public Student(){
        // Needed for dbHandler to create a student from GetStudentById()
    }

    public Student(String firstName, String lastName, int phone, String email, String addressLine, String suburb, String state, int postcode, String country ) {
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
    public Student(String firstName, String lastName, int phone, String email, String addressLine, String suburb, String state, int postcode, String country, int studentId ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.addressLine = addressLine;
        this.suburb = suburb;
        this.state = state;
        this.country = country;
        this.postcode = postcode;
        this.studentId = studentId;
    }


//    public Student(String firstName, String lastName, String phone, String email, String addressLine, String suburb, String state, String country, String postcode) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.phone = phone;
//        this.email = email;
//        this.addressLine = addressLine;
//        this.suburb = suburb;
//        this.state = state;
//        this.country = country;
//        this.tempPostcode = postcode;
//    }




    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setTempPostcode(String tempPostcode) {
        this.tempPostcode = tempPostcode;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }

    public int getPhone() {
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

    public int getStudentId() {
        return studentId;
    }

    public Student setFullName(String firstname, String lastname) {
        this.firstName = firstname;
        this.lastName = lastname;
        return  this;
    }
}
