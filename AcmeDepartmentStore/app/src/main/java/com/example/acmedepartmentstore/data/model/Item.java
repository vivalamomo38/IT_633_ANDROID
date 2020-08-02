package com.example.acmedepartmentstore.data.model;

public class Item {

    /** Step 1: Variables **/
    private String itemName;
    private int numOfItems;
    private double unitPrice;
    private String itemDescription;
    private int thumbnail;

    /** Step 2: Constructors**/
    public Item() {
    }

    public Item(String itemName, int numOfItems, double unitPrice, String itemDescription, int thumbnail) {
        this.itemName = itemName;
        this.numOfItems = numOfItems;
        this.unitPrice = unitPrice;
        this.itemDescription = itemDescription;
        this.thumbnail = thumbnail;
    }
/** Step 3: Getters & Setters**/

    public String getItemName() {
        return itemName;
    }

    public int getNumOfItems() {
        return numOfItems;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setNumOfItems(int numOfItems) {
        this.numOfItems = numOfItems;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
