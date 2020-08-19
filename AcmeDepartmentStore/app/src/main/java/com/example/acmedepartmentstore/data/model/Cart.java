package com.example.acmedepartmentstore.data.model;

import java.util.ArrayList;

public class Cart {

    /** Step 1: Variables **/
    private String cartOwnerID;
    private int numOfItems;
    private ArrayList<Item> cartItems;
    private double cartTotalPrice;


    /** Step 2: Constructors**/
    public Cart() {
    }

    public String getCartOwnerID() {
        return cartOwnerID;
    }

    public void setCartOwnerID(String cartOwnerID) {
        this.cartOwnerID = cartOwnerID;
    }

    public int getNumOfItems() {
        return numOfItems;
    }

    public void setNumOfItems(int numOfItems) {
        this.numOfItems = numOfItems;
    }

    public ArrayList<Item> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<Item> cartItems) {
        this.cartItems = cartItems;
    }

    public double getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(double cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    public Cart(String cartOwnerID, int numOfItems, ArrayList<Item> cartItems, double cartTotalPrice) {
        this.cartOwnerID = cartOwnerID;
        this.numOfItems = numOfItems;
        this.cartItems = cartItems;
        this.cartTotalPrice = cartTotalPrice;
    }
    /** Step 3: Getters & Setters**/



}
