package com.moviz.service;

import com.moviz.entity.User;
import com.moviz.model.UserManager;

/**
 * The security context manage the authentication, the authorization and the current user
 *
 * @author Titouan Galopin <galopintitouan@gmail.com>
 */
public class SecurityContext {

    /**
     * Store the current user (null if anonymous)
     */
    protected User currentUser;


    /*
     * Dependencies
     */
    /**
     * User manager to retreive user informations
     */
    protected UserManager userManager;

    /**
     * Password encoder to check a given plain password against the stored hash
     */
    protected PasswordEncoder passwordEncoder;


    /**
     * Constructor
     *
     * @param userManager UserManager
     * @param passwordEncoder PasswordEncoder
     */
    public SecurityContext(UserManager userManager, PasswordEncoder passwordEncoder) {
        this.userManager = userManager;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Try to login the user given credentials
     * Return true if the login succeed, false otherwise
     *
     * @param login String
     * @param password String
     * @return Result of login
     */
    public boolean login(String login, String password) {
        User user = this.userManager.findOneByLogin(login);

        if (user == null) {
            return false;
        }

        if (! user.getPassword().equals(this.passwordEncoder.encode(password))) {
            return false;
        }

        this.currentUser = user;

        return true;
    }

    /**
     * Logout the current user
     * Return always true to be consistent with the login method
     *
     * @return boolean
     */
    public boolean logout() {
        this.currentUser = null;
        return true;
    }

    /**
     * Check if a user is currently logged in
     *
     * @return boolean
     */
    public boolean isLoggedIn() {
        return this.currentUser != null;
    }

    /**
     * Get the current user
     *
     * @return boolean
     */
    public User getCurrentUser() {
        return currentUser;
    }

}
