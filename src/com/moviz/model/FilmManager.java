package com.moviz.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.moviz.definition.AbstractModel;

import com.moviz.entity.Film;
import com.moviz.entity.User;
import com.moviz.service.DataPersister;

/**
 * User manager: users com.moviz.data com.moviz.model
 *
 * @author Titouan Galopin <galopintitouan@gmail.com>
 */
public class FilmManager extends AbstractModel {

    /**
     * Films data
     */
    protected ArrayList<Film> films;


    /**
     * Constructor
     * Depends on the DataPersister to fetch and save data
     *
     * @param persister The data persister
     */
    public FilmManager(DataPersister persister) {
        super(persister);
        persister.createFile("films");
        this.fetch();
    }

    /**
     * Fetch the data from the database file and refresh the model accordingly
     */
    public void fetch() {
        try {
            this.films = (ArrayList<Film>) this.persister.read("films");
        } catch (Exception exception) {
            this.films = new ArrayList<>();
        }
    }

    /**
     * Persist the data into the data file
     */
    public void persist() {
        try {
            this.persister.write("films", this.films);
        } catch (Exception exception) {
            System.out.println("Save failed");
        }
    }


    /*
     * Retrieve the data
     */

    public int count() {
        return this.films.size();
    }

    public ArrayList<Film> findAll() {
        return this.films;
    }

    public ArrayList<Film> findNotedBy(User user) {
        ArrayList<Film> filtered = new ArrayList<>();

        for (Film film: this.films) {
            if (film.hasNoteBy(user)) {
                filtered.add(film);
            }
        }

        return filtered;
    }

    public ArrayList<Film> findNotNotedBy(User user) {
        ArrayList<Film> filtered = new ArrayList<>();

        for (Film film: this.films) {
            if (! film.hasNoteBy(user)) {
                filtered.add(film);
            }
        }

        return filtered;
    }

    public ArrayList<Film> findSeenBy(User user) {
        ArrayList<Film> filtered = new ArrayList<>();

        for (Film film: this.films) {
            if (film.hasBeenSeenBy(user)) {
                filtered.add(film);
            }
        }

        return filtered;
    }

    public ArrayList<Film> findNotSeenBy(User user) {
        ArrayList<Film> filtered = new ArrayList<>();

        for (Film film: this.films) {
            if (! film.hasBeenSeenBy(user)) {
                filtered.add(film);
            }
        }

        return filtered;
    }


    /*
     * Sort the data
     */

    public void sortByName(ArrayList<Film> films) {
        Collections.sort(films, new NameComparator());
    }

    public void sortByReleaseDate(ArrayList<Film> films) {
        Collections.sort(films, new ReleaseDateComparator());
    }

    public void sortByNote(ArrayList<Film> films) {
        Collections.sort(films, new NoteComparator());
    }



    /*
     * Edit the data
     */

    /**
     * Add a film to the data
     *
     * @param film Film to add
     * @return Result of adding
     */
    public boolean addFilm(Film film) {
        this.films.add(film);
        return true;
    }

    /**
     * Remove a film from the data
     *
     * @param film Film to remove
     * @return Result of removing
     */
    public boolean removeFilm(Film film) {
        return this.films.remove(film);
    }


    /*
     * Comparators to sort the data
     */

    private class NameComparator implements Comparator<Film> {
        @Override
        public int compare(Film film1, Film film2) {
            return film1.getTitle().compareTo(film2.getTitle());
        }
    }

    private class ReleaseDateComparator implements Comparator<Film> {
        @Override
        public int compare(Film film1, Film film2) {
            return ((int) film2.getReleaseDate().getTime()) - ((int) film1.getReleaseDate().getTime());
        }
    }

    private class NoteComparator implements Comparator<Film> {
        @Override
        public int compare(Film film1, Film film2) {
            if (film1.getAverageNote() < film2.getAverageNote()) {
                return -1;
            } else if (film1.getAverageNote() > film2.getAverageNote()) {
                return 1;
            } else {
                return 0;
            }
        }
    }

}
