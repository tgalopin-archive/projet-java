package com.moviz.main;

import com.moviz.controller.DeleteController;
import com.moviz.controller.EditController;
import com.moviz.service.*;
import com.moviz.service.FileChooser;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.*;

import com.moviz.controller.ReadController;
import com.moviz.entity.Film;
import com.moviz.model.FilmManager;
import com.moviz.model.UserManager;

import java.net.URL;

/**
 * The application com.moviz.main class
 *
 * This class implements a very simple Dependency Injection container to
 * provide services in all the application
 */
public class Application extends javafx.application.Application {

    @Override
    public void start(Stage stage) throws Exception {
        Application.stage = stage;

        Application.loginView = getClass().getResource("../views/login.fxml");
        Application.mainView = getClass().getResource("../views/main.fxml");
        Application.readView = getClass().getResource("../views/read.fxml");
        Application.createView = getClass().getResource("../views/create.fxml");
        Application.editView = getClass().getResource("../views/edit.fxml");
        Application.deleteView = getClass().getResource("../views/delete.fxml");

        Application.showLoginStage();
    }

    public static void main(String[] args) {
        launch(args);
    }



    /*
     * Scenes
     */

    // Stage used to display the scenes
    protected static Stage stage;

    // Views URL
    protected static URL loginView;
    protected static URL mainView;
    protected static URL readView;
    protected static URL createView;
    protected static URL editView;
    protected static URL deleteView;

    /**
     * Display the login window
     */
    public static void showLoginStage() {
        try {
            Stage stage = Application.stage;

            stage.setTitle("Moviz - Authentification");
            stage.setScene(new Scene(FXMLLoader.load(Application.loginView), 480, 320));

            Application.showStage(stage);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Display the main window
     */
    public static void showMainStage() {
        try {
            Stage stage = Application.stage;

            stage.setTitle("Moviz");
            stage.setScene(new Scene(FXMLLoader.load(Application.mainView), 1280, 800));

            Application.showStage(stage);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Display the read window
     */
    public static void showReadStage(Film film) {
        try {
            FXMLLoader loader = new FXMLLoader(Application.readView);
            loader.setController(new ReadController(film));

            Stage stage = Application.stage;

            stage.setTitle("Moviz - " + film.getTitle());
            stage.setScene(new Scene(loader.load()));

            Application.showStage(stage);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Display the create window
     */
    public static void showCreateStage() {
        try {
            Stage stage = Application.stage;

            stage.setTitle("Moviz - Créer une fiche de film");
            stage.setScene(new Scene(FXMLLoader.load(Application.createView), 1280, 800));

            Application.showStage(stage);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Display the edit window
     */
    public static void showEditStage(Film film) {
        try {
            FXMLLoader loader = new FXMLLoader(Application.editView);
            loader.setController(new EditController(film));

            Stage stage = Application.stage;

            stage.setTitle("Moviz - Modifier la fiche de " + film.getTitle());
            stage.setScene(new Scene(loader.load()));

            Application.showStage(stage);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Display the delete window
     */
    public static void showDeleteStage(Film film) {
        try {
            FXMLLoader loader = new FXMLLoader(Application.deleteView);
            loader.setController(new DeleteController(film));

            Stage stage = Application.stage;

            stage.setTitle("Moviz - Supprimer la fiche de " + film.getTitle());
            stage.setScene(new Scene(loader.load()));

            Application.showStage(stage);
        } catch (Exception e) {
            System.out.println(e);
        }
    }



    // Helper to display a centerd, titled stage
    private static void showStage(Stage stage) {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        stage.setX((primaryScreenBounds.getWidth() - stage.getScene().getWidth()) / 2);
        stage.setY((primaryScreenBounds.getHeight() - stage.getScene().getHeight()) / 2);

        stage.show();
    }



    /*
     * Parameters
     */
    public static String getWorkingDirectory() {
        return System.getProperty("user.dir");
    }

    public static String getResourcesDirectory() {
        return Application.getWorkingDirectory() + "/src/com/moviz/views";
    }

    public static String getDatabaseDirectory() {
        return Application.getWorkingDirectory() + "/src/com/moviz/data/db";
    }

    public static String getUploadsDirectory() {
        return Application.getWorkingDirectory() + "/src/com/moviz/data/img";
    }



    /*
     * Managers
     */

    // Users
    protected static UserManager userManager;

    // Films
    protected static FilmManager filmManager;


    /**
     * Public
     */
    public static UserManager getUserManager() {
        if (Application.userManager == null) {
            Application.userManager = new UserManager(Application.getDataPersister());
        }

        return Application.userManager;
    }

    /**
     * Public
     */
    public static FilmManager getFilmManager() {
        if (Application.filmManager == null) {
            Application.filmManager = new FilmManager(Application.getDataPersister());
        }

        return Application.filmManager;
    }



    /*
     * Services
     */

    // Data persister: serialize and unserialize com.moviz.data into and from files
    protected static DataPersister dataPersister;

    // Password encoder: encode passwords in a secure way using SHA512
    protected static PasswordEncoder passwordEncoder;

    // Security context: manage the authentication, the authorization and the current user
    protected static SecurityContext securityContext;

    // Search builder: manage the list of the main scene by filtering and sorting films
    protected static SearchBuilder searchBuilder;

    // File chooser: open a "Choose file" dialog using the application stage
    protected static FileChooser fileChooser;

    // Resource repository: provides helpers to access (read / write) films and users pictures
    protected static UploadsRepository uploadsRepository;


    /**
     * Private
     */
    protected static DataPersister getDataPersister() {
        if (Application.dataPersister == null) {
            Application.dataPersister = new DataPersister(Application.getDatabaseDirectory());
        }

        return Application.dataPersister;
    }

    /**
     * Private
     */
    protected static PasswordEncoder getPasswordEncoder() {
        if (Application.passwordEncoder == null) {
            Application.passwordEncoder = new PasswordEncoder();
        }

        return Application.passwordEncoder;
    }


    /**
     * Public
     */
    public static SecurityContext getSecurityContext() {
        if (Application.securityContext == null) {
            Application.securityContext = new SecurityContext(
                Application.getUserManager(),
                Application.getPasswordEncoder()
            );
        }

        return Application.securityContext;
    }

    /**
     * Public
     */
    public static SearchBuilder getSearchBuilder() {
        if (Application.searchBuilder == null) {
            Application.searchBuilder = new SearchBuilder(
                Application.getFilmManager(),
                Application.getSecurityContext()
            );
        }

        return Application.searchBuilder;
    }

    /**
     * Public
     */
    public static FileChooser getFileChooser() {
        if (Application.fileChooser == null) {
            Application.fileChooser = new FileChooser(Application.stage);
        }

        return Application.fileChooser;
    }

    /**
     * Public
     */
    public static UploadsRepository getUploadsRepository() {
        if (Application.uploadsRepository == null) {
            Application.uploadsRepository = new UploadsRepository(Application.getUploadsDirectory());
        }

        return Application.uploadsRepository;
    }
}