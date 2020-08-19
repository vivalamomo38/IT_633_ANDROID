package com.example.acmedepartmentstore.data.model;

import java.util.ArrayList;

public class Inventory {

    /** Step 1: Variables **/
    private String inventoryOwnerID;
    private int numOfItems;
    private ArrayList<Item> inventoryItems;
    private double inventoryTotalPrice;


    /** Step 2: Constructors**/
    public Inventory() {
    }

    public String getinventoryOwnerID() {
        return inventoryOwnerID;
    }

    public void setinventoryOwnerID(String inventoryOwnerID) {
        this.inventoryOwnerID = inventoryOwnerID;
    }

    public int getNumOfItems() {
        return numOfItems;
    }

    public void setNumOfItems(int numOfItems) {
        this.numOfItems = numOfItems;
    }

    public ArrayList<Item> getinventoryItems() {
        return inventoryItems;
    }

    public void setinventoryItems(ArrayList<Item> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }

    public double getinventoryTotalPrice() {
        return inventoryTotalPrice;
    }

    public void setinventoryTotalPrice(double inventoryTotalPrice) {
        this.inventoryTotalPrice = inventoryTotalPrice;
    }

    public Inventory(String inventoryOwnerID, int numOfItems, ArrayList<Item> inventoryItems, double inventoryTotalPrice) {
        this.inventoryOwnerID = inventoryOwnerID;
        this.numOfItems = numOfItems;
        this.inventoryItems = inventoryItems;
        this.inventoryTotalPrice = inventoryTotalPrice;
    }
    /** Step 3: Getters & Setters**/
}
