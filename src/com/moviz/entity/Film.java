package com.moviz.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Film entity
 *
 * @author Titouan Galopin <galopintitouan@gmail.com>
 */
public class Film implements Serializable {

    /**
     * Title
     */
    protected String title;

    /**
     * Synopsis (description of the story)
     */
    protected String synopsis;

    /**
     * Duration (stored in minutes)
     */
    protected int duration;

    /**
     * Release date
     */
    protected Date releaseDate;

    /**
     * Director name
     */
    protected String director = "Inconnu";

    /**
     * Picture
     */
    protected String picture = "default";

    /**
     * Notes
     */
    protected ArrayList<FilmNote> notes;

    /**
     * Seens
     */
    protected ArrayList<FilmSeen> seens;


    public Film() {
        this.notes = new ArrayList<>();
        this.seens = new ArrayList<>();
    }

    public String toString() {
        return this.title + " (par " + this.director + ", sortie le " + this.releaseDate + ")\n"
            + this.synopsis + "\n"
            + "Durée : " + this.duration + " minutes";
    }

    /*
     * Getters / Setters
     */

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public ArrayList<FilmNote> getNotes() {
        return notes;
    }

    public double getAverageNote() {
        double sum = 0;

        for (FilmNote note: this.notes) {
            sum += note.getValue();
        }

        return (double) Math.round((sum / this.notes.size()) * 100) / 100;
    }

    public boolean hasNoteBy(User user) {
        return this.hasNoteBy(user.getLogin());
    }

    public boolean hasNoteBy(String login) {
        for (FilmNote note: this.notes) {
            if (note.getLogin().equals(login)) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<FilmSeen> getSeens() {
        return seens;
    }

    public boolean hasBeenSeenBy(User user) {
        return this.hasBeenSeenBy(user.getLogin());
    }

    public boolean hasBeenSeenBy(String login) {
        for (FilmSeen seen: this.seens) {
            if (seen.getLogin().equals(login)) {
                return true;
            }
        }

        return false;
    }

    public FilmSeen getSeenBy(User user) {
        return this.getSeenBy(user.getLogin());
    }

    public FilmSeen getSeenBy(String login) {
        for (FilmSeen seen: this.seens) {
            if (seen.getLogin().equals(login)) {
                return seen;
            }
        }

        return null;
    }

}
