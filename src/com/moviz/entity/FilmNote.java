package com.moviz.entity;

import java.io.Serializable;

/**
 * Film note entity
 * This entity is immutable: you can't edit a note, you can only
 * remove it and put another
 *
 * @author Titouan Galopin <galopintitouan@gmail.com>
 */
public class FilmNote implements Serializable {

    /**
     * User login
     */
    protected String login;

    /**
     * Note itself
     */
    protected int value;


    /**
     * Constructor using the login directly
     *
     * @param login User login
     * @param value Note value
     */
    public FilmNote(String login, int value) {
        this.login = login;
        this.value = value;
    }

    /**
     * Constructor using a User entity
     *
     * @param user User
     * @param value Note value
     */
    public FilmNote(User user, int value) {
        this.login = user.getLogin();
        this.value = value;
    }


    /*
     * Getters / Setters
     */

    public String getLogin() {
        return login;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
