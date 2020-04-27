
/**
 * This program is a proof of concept app for
 * an android version of the GUADR web application.
 *
 * Anyone is free to take and expand upon this code with credit
 * @author Jackson Paris
 * @version v1.0 4/26/20
 */

package com.example.guadrandroid;

public class Seller {
    private String sellerEmail;
    private String sellerPassword;
    private String sellerName;
    private String sellerDescription;

    /**
     * This constructor is used to create a new seller object
     * @param sellerEmail the email for the seller
     * @param sellerPassword the password for the seller
     * @param name name of the seller
     * @param sellerDescription description of the seller
     */
    public Seller(String sellerEmail,String sellerPassword,String name, String sellerDescription) {
        this.sellerEmail =  sellerEmail;
        this.sellerPassword = sellerPassword;
        this.sellerName = name;
        this.sellerDescription = sellerDescription;
    }

    /**
     * getter function for seller email
     * @return the seller email
     */
    public String getSellerEmail() {
        return sellerEmail;
    }

    /**
     * setter function for seller email
     * @param sellerEmail the seller email to be set
     */
    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    /**
     * getter function for seller password
     * @return the seller password
     */
    public String getSellerPassword() {
        return sellerPassword;
    }

    /**
     * setter function for seller password
     * @param sellerPassword the new seller password
     */
    public void setSellerPassword(String sellerPassword) {
        this.sellerPassword = sellerPassword;
    }

    /**
     * getter function for seller name
     * @return seller name
     */
    public String getSellerName() {
        return sellerName;
    }

    /**
     * setter function for seller name
     * @param sellerName the new seller name
     */
    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    /**
     * getter function for seller description
     * @return the seller description
     */
    public String getSellerDescription() {
        return sellerDescription;
    }

    /**
     * setter function for seller description
     * @param sellerDescription the new seller description
     */
    public void setSellerDescription(String sellerDescription) {
        this.sellerDescription = sellerDescription;
    }
}

