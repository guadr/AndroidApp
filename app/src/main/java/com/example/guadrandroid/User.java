
/**
 * This program is a proof of concept app for
 * an android version of the GUADR web application.
 *
 * Anyone is free to take and expand upon this code with credit
 * @author Jackson Paris
 * @version v1.0 4/26/20
 */

package com.example.guadrandroid;

public class User {
    private String email;
    private String password;
    private String name;

    /**
     * constructor for the user class
     * @param email email for the user
     * @param password password for the user
     * @param name name of the user
     */
    public User( String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    /**
     * getter function for the user email
     * @return the user email
     */
    public String getEmail() {
        return email;
    }

    /**
     * setter function for user email
     * @param email the user email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * getter function for user email
     * @return the user email
     */
    public String getName() {
        return name;
    }

    /**
     * setter function for user name
     * @param name the name of the users
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * getter function for user password
     * @return the user password
     */
    public String getPassword() {
        //TODO make this much more secure
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
