package com.example.acmedepartmentstore.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String uID;
    private String firstName;
    private String lastName;
    private Inventory userInventory;

    public LoggedInUser(String uID, String firstName, String lastName, Inventory userInventory) {
        this.uID = uID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userInventory = userInventory;
    }

    public String getuID() {
        return uID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName(){ return lastName;}

    public Inventory getUserInventory() { return getUserInventory();}

    public void setuID(String uID) {
        this.uID = uID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserInventory(Inventory userInventory) {
        this.userInventory = userInventory;
    }


    }