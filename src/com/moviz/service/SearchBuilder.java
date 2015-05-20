package com.moviz.service;

import com.moviz.entity.Film;
import com.moviz.model.FilmManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;

/**
 * The list renderer manage the list of the main scene by filtering and sorting films
 *
 * @author Titouan Galopin <galopintitouan@gmail.com>
 */
public class SearchBuilder extends Observable {

    public static final int FILTER_NONE = 0;
    public static final int FILTER_NOT_NOTED = 1;
    public static final int FILTER_NOTED = 2;
    public static final int FILTER_SEEN = 3;
    public static final int FILTER_TO_SEE = 4;

    public static final int SORT_NAME_ASC = 0;
    public static final int SORT_NAME_DESC = 1;
    public static final int SORT_YEAR_ASC = 2;
    public static final int SORT_YEAR_DESC = 3;
    public static final int SORT_AVG_NOTE_ASC = 4;
    public static final int SORT_AVG_NOTE_DESC = 5;

    /**
     * List filter (FILTER_*)
     */
    private int filter = SearchBuilder.FILTER_NONE;

    /**
     * List order (SORT_*)
     */
    private int sort = SearchBuilder.SORT_NAME_ASC;


    /*
     * Dependencies
     */
    /**
     * Films manager
     */
    protected FilmManager filmManager;

    /**
     * Security context
     */
    protected SecurityContext securityContext;


    /**
     * Constructor
     *
     * @param filmManager FilmManager
     * @param securityContext SecurityContext
     */
    public SearchBuilder(FilmManager filmManager, SecurityContext securityContext) {
        this.filmManager = filmManager;
        this.securityContext = securityContext;
    }

    /**
     * Return the filtered and sorted list of films
     *
     * @return The films
     */
    public ArrayList<Film> search() {
        ArrayList<Film> films;

        // Filter
        switch (filter) {
            case FILTER_NOT_NOTED:
                films = this.filmManager.findNotNotedBy(this.securityContext.getCurrentUser());
                break;

            case FILTER_NOTED:
                films = this.filmManager.findNotedBy(this.securityContext.getCurrentUser());
                break;

            case FILTER_SEEN:
                films = this.filmManager.findSeenBy(this.securityContext.getCurrentUser());
                break;

            case FILTER_TO_SEE:
                films = this.filmManager.findNotSeenBy(this.securityContext.getCurrentUser());
                break;

            default:
                films = this.filmManager.findAll();
        }

        // Sort
        switch (sort) {
            case SORT_AVG_NOTE_DESC:
                this.filmManager.sortByNote(films);
                Collections.reverse(films);
                break;

            case SORT_AVG_NOTE_ASC:
                this.filmManager.sortByNote(films);
                break;

            case SORT_YEAR_DESC:
                this.filmManager.sortByReleaseDate(films);
                Collections.reverse(films);
                break;

            case SORT_YEAR_ASC:
                this.filmManager.sortByReleaseDate(films);
                break;

            case SORT_NAME_DESC:
                this.filmManager.sortByName(films);
                Collections.reverse(films);
                break;

            default:
                this.filmManager.sortByName(films);
        }

        return films;
    }


    /**
     * Get the current filter option
     *
     * @return The filter
     */
    public int getFilter() {
        return filter;
    }

    /**
     * Get the current sort option
     *
     * @return The filter
     */
    public int getSort() {
        return sort;
    }

    /**
     * Change the filter option and re-render
     *
     * @param filter New filter
     * @return Result
     */
    public boolean setFilter(int filter) {
        if (filter < 0 || filter > 4) {
            return false;
        }

        this.filter = filter;

        this.setChanged();
        this.notifyObservers();

        return true;
    }

    /**
     * Change the sort option and re-render
     *
     * @param sort New sort
     * @return Result
     */
    public boolean setSort(int sort) {
        if (sort < 0 || sort > 7) {
            return false;
        }

        this.sort = sort;

        this.setChanged();
        this.notifyObservers();

        return true;
    }
}
