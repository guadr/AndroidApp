package com.example.guadrandroid;

public class Item {
    private  String itemPrice;
    private String itemName;
    private String itemDescription;
    private String itemSeller;

    public Item(String price, String name, String description, String seller) {
        this.itemPrice = price;
        this.itemName = name;
        this.itemDescription = description;
        this.itemSeller = seller;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemSeller() {
        return itemSeller;
    }

    public void setItemSeller(String itemSeller) {
        this.itemSeller = itemSeller;
    }
}
