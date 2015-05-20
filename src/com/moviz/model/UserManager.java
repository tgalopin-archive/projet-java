package com.moviz.model;

import java.util.ArrayList;

import com.moviz.definition.AbstractModel;

import com.moviz.entity.User;
import com.moviz.service.DataPersister;

/**
 * User manager: users com.moviz.data com.moviz.model
 *
 * @author Titouan Galopin <galopintitouan@gmail.com>
 */
public class UserManager extends AbstractModel {

    /**
     * Users data
     */
    protected ArrayList<User> users;


    /**
     * Constructor
     * Depends on the DataPersister to fetch and save data
     *
     * @param persister The data persister
     */
    public UserManager(DataPersister persister) {
        super(persister);
        persister.createFile("users");
        this.fetch();
    }

    /**
     * Fetch the data from the database file and refresh the model accordingly
     */
    public void fetch() {
        try {
            this.users = (ArrayList<User>) this.persister.read("users");
        } catch (Exception exception) {
            this.users = new ArrayList<>();
        }
    }

    /**
     * Persist the data into the data file
     */
    public void save() {
        try {
            this.persister.write("users", this.users);
        } catch (Exception exception) {
            System.out.println("Save failed");
        }
    }


    /*
     * Retrieve the data
     */

    /**
     * Find a single user by its login
     *
     * @param login Login of the user to search
     * @return The user or null if not found
     */
    public User findOneByLogin(String login) {
        for (User user: this.users) {
            if (login.equals(user.getLogin())) {
                return user;
            }
        }

        return null;
    }



    /*
     * Edit the data
     */

    /**
     * Add a user to the data
     *
     * @param user User to add
     * @return Result of the adding
     */
    public boolean addUser(User user) {
        // Unique identifier
        for (User u: this.users) {
            if (user.getLogin().equals(u.getLogin())) {
                return false;
            }
        }

        this.users.add(user);

        return true;
    }

}
