package com.moviz.entity;

import java.io.Serializable;

/**
 * Film entity
 *
 * @author Titouan Galopin <galopintitouan@gmail.com>
 */
public class FilmSeen implements Serializable {

    /**
     * User login
     */
    protected String login;


    /**
     * Constructor using the login directly
     *
     * @param login User login
     */
    public FilmSeen(String login) {
        this.login = login;
    }

    /**
     * Constructor using a User entity
     *
     * @param user User
     */
    public FilmSeen(User user) {
        this.login = user.getLogin();
    }


    /*
     * Getters / Setters
     */

    public String getLogin() {
        return login;
    }

}
