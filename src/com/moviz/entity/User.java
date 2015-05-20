package com.moviz.entity;

import java.io.Serializable;

/**
 * User entity
 *
 * @author Titouan Galopin <galopintitouan@gmail.com>
 */
public class User implements Serializable {

    public static final int ROLE_USER = 10;
    public static final int ROLE_ADMIN = 20;

    /**
     * Unique login
     */
    protected String login;

    /**
     * Encoded password
     */
    protected String password;

    /**
     * Full name
     */
    protected String fullName;

    /**
     * Picture
     */
    protected String picture = "default";

    /**
     * Role
     */
    protected int role = ROLE_USER;


    /**
     * Constructor: the login is required and immutable
     *
     * @param login User login
     */
    public User(String login) {
        this.login = login;
    }


    /*
     * Getters / Setters
     */

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
