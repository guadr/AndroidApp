
/**
 * This program is a proof of concept app for
 * an android version of the GUADR web application.
 *
 * Anyone is free to take and expand upon this code with credit
 * @author Jackson Paris
 * @version v1.0 4/26/20
 */

package com.example.guadrandroid;

public class Item {
    private  String itemPrice;
    private String itemName;
    private String itemDescription;
    private String itemSeller;

    /**
     * Constructor for the item class
     * @param price price of the item
     * @param name name of the item
     * @param description description of the item
     * @param seller the seller of item
     */
    public Item(String price, String name, String description, String seller) {
        this.itemPrice = price;
        this.itemName = name;
        this.itemDescription = description;
        this.itemSeller = seller;
    }

    /**
     * getter function for item price
     * @return item price
     */
    public String getItemPrice() {
        return itemPrice;
    }

    /**
     * Setter function for item price
     * @param itemPrice the new price of the item
     */
    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    /**
     *
     * getter function for item name
     * @return the items name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * setter function for item name
     * @param itemName the name of the item
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * getter function for item description
     * @return the item description
     */
    public String getItemDescription() {
        return itemDescription;
    }

    /**
     * setter function for item description
     * @param itemDescription the new item description
     */
    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    /**
     * getter function for item seller
     * @return the seller of the item
     */
    public String getItemSeller() {
        return itemSeller;
    }

    /**
     * setter function for item seller
     * @param itemSeller the new item seller
     */
    public void setItemSeller(String itemSeller) {
        this.itemSeller = itemSeller;
    }
}
