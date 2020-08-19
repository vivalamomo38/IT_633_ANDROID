package com.example.acmedepartmentstore.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String uID;
    private String firstName;
    private String lastName;

    public LoggedInUser(String userId, String displayName) {
        this.uID = userId;
        this.firstName = firstName;
        this.lastName =  lastName;
    }

    public String getuID() {
        return uID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName(){ return lastName;}
}