package com.moviz.console;

import java.util.Date;
import java.util.Random;

import com.github.jbueza.Faker.Faker;

import com.moviz.entity.FilmNote;
import com.moviz.entity.FilmSeen;
import com.moviz.main.Application;
import com.moviz.entity.Film;
import com.moviz.entity.User;

/**
 * The fixtures are testing data used in the development process
 * Automating these fixtures let me reload them every time I need
 *
 * @author Titouan Galopin <galopintitouan@gmail.com>
 */
public class Fixtures extends Application {

    public static void main(String[] args) {
        Faker faker = new Faker();

        loadUsers(faker);
        loadFilms(faker);
    }

    /**
     * Load the users data
     */
    public static void loadUsers(Faker faker) {
        System.out.println("Loading users ...");

        Application.getDataPersister().removeFile("users");

        for (int i = 1; i <= 10; i++) {
            User user = new User("user" + i);

            user.setFullName(faker.Person().fullName());
            user.setPassword(Application.getPasswordEncoder().encode("user" + i));
            user.setPicture("default");

            // First user is admin
            if (i == 1) {
                user.setRole(User.ROLE_ADMIN);
            }

            Application.getUserManager().addUser(user);
        }

        Application.getUserManager().save();

        System.out.println("10 users loaded\n");
    }

    /**
     * Load the films, films notes and films seen data
     */
    public static void loadFilms(Faker faker) {
        System.out.println("Loading films ...");

        Application.getDataPersister().removeFile("films");

        Random rand = new Random();

        int nbFilms = 90 + rand.nextInt(21);

        for (int i = 1; i <= nbFilms; i++) {
            Film film = new Film();

            char[] title = faker.LoremIpsum().words(3).toCharArray();
            title[0] = Character.toUpperCase(title[0]);

            film.setTitle(String.valueOf(title));
            film.setDirector(faker.Person().fullName());
            film.setSynopsis(faker.LoremIpsum().paragraphs(2));
            film.setReleaseDate(new Date());
            film.setDuration(90 + rand.nextInt(41));
            film.setPicture((i % 4 + 1) + "");

            for (int j = 1; j <= rand.nextInt(11); j++) {
                film.getNotes().add(new FilmNote("user" + (rand.nextInt(10) + 1), rand.nextInt(6)));
            }

            for (int k = 1; k <= rand.nextInt(11); k++) {
                film.getSeens().add(new FilmSeen("user" + (rand.nextInt(10) + 1)));
            }

            Application.getFilmManager().addFilm(film);
        }

        Application.getFilmManager().persist();

        System.out.println(nbFilms + " films loaded\n");
    }

}
