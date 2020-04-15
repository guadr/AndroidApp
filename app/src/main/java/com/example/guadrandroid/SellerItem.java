package com.example.guadrandroid;

public class SellerItem {
    private static double itemPrice;
    private static String itemName;
    private static String itemDescription;

    public static double getItemPrice() {
        return itemPrice;
    }

    public static void setItemPrice(double itemPrice) {
        SellerItem.itemPrice = itemPrice;
    }

    public static String getItemName() {
        return itemName;
    }

    public static void setItemName(String itemName) {
        SellerItem.itemName = itemName;
    }

    public static String getItemDescription() {
        return itemDescription;
    }

    public static void setItemDescription(String itemDescription) {
        SellerItem.itemDescription = itemDescription;
    }
}
