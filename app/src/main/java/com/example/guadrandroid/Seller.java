package com.example.guadrandroid;

public class Seller {
    private String sellerName;
    private String sellerDescription;

    public Seller(String name, String sellerDescription) {
        this.sellerName = name;
        this.sellerDescription = sellerDescription;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerDescription() {
        return sellerDescription;
    }

    public void setSellerDescription(String sellerDescription) {
        this.sellerDescription = sellerDescription;
    }
}

