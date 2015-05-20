package com.moviz.validator;

import com.moviz.entity.Film;

import java.util.ArrayList;

/**
 * Validate a film entity against a set of rules
 *
 * @author Titouan Galopin <galopintitouan@gmail.com>
 */
public class FilmValidator {

    public static ArrayList<String> validate(Film film) {
        ArrayList<String> errors = new ArrayList<>();

        if (film.getTitle() == null || film.getTitle().length() == 0) {
            errors.add("Le titre est requis");
        } else if (film.getTitle().length() <= 2) {
            errors.add("Le titre est trop court");
        }

        if (film.getSynopsis() == null || film.getSynopsis().length() == 0) {
            errors.add("Le synopsis est requis");
        } else if (film.getSynopsis().length() <= 15) {
            errors.add("Le synopsis est trop court");
        }

        if (film.getDirector() == null || film.getDirector().length() == 0) {
            errors.add("Le nom du réalisateur est requis");
        } else if (film.getDirector().length() <= 2) {
            errors.add("Le nom du réalisateur est trop court");
        }

        if (film.getDuration() == 0) {
            errors.add("La durée est invalide");
        }

        if (film.getReleaseDate() == null) {
            errors.add("La date de sortie est requise");
        }

        return errors;
    }

}
