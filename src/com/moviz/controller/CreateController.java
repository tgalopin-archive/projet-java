package com.moviz.controller;

import com.moviz.entity.Film;
import com.moviz.main.Application;
import com.moviz.model.FilmManager;
import com.moviz.util.DateConverter;
import com.moviz.service.FileChooser;
import com.moviz.service.UploadsRepository;

import com.moviz.validator.FilmValidator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller to create a film
 */
public class CreateController implements Initializable {

    private Film film;

    /*
     * View
     */
    @FXML
    private ImageView picture;

    @FXML
    private Button choosePicture;

    @FXML
    private TextField title;

    @FXML
    private TextArea synopsis;

    @FXML
    private TextField director;

    @FXML
    private TextField duration;

    @FXML
    private DatePicker releaseDate;

    @FXML
    private Button back;

    @FXML
    private Button save;

    @FXML
    private Button cancel;

    @FXML
    protected Text errorDisplay;


    /*
     * Services
     */
    protected FileChooser fileChooser;
    protected UploadsRepository uploadsRepository;
    protected FilmManager filmManager;



    /**
     * Initialize the controller and the view
     *
     * @param url The Scene URL
     * @param resourceBundle The resources bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.film = new Film();

        // Services
        this.fileChooser = Application.getFileChooser();
        this.uploadsRepository = Application.getUploadsRepository();
        this.filmManager = Application.getFilmManager();

        // Event handlers
        this.back.setOnAction(new BackButtonHandler());
        this.cancel.setOnAction(new BackButtonHandler());
        this.choosePicture.setOnAction(new ChooseImageHandler());
        this.save.setOnAction(new SaveHandler());
    }




    /*
     * Handlers
     */

    /**
     * Back button
     */
    private class BackButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Application.showMainStage();
        }
    }

    /**
     * Choose image handler
     */
    private class ChooseImageHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            File file = fileChooser.chooseImageFile();

            // File not canceled
            if (file != null) {

                // Save it as a temporary file only to display it
                if (uploadsRepository.saveAsFilmPicture(file, "temp")) {
                    picture.setImage(new Image(uploadsRepository.getFilmPicturePath("temp")));
                    film.setPicture("temp");
                }

            }
        }
    }

    /**
     * Save handler
     */
    private class SaveHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

            ArrayList<String> errors = new ArrayList<>();

            try {

                // Text elements
                film.setTitle(title.getText());
                film.setSynopsis(synopsis.getText());
                film.setDirector(director.getText());

                // Duration
                try {
                    film.setDuration(Integer.parseInt(duration.getText()));
                } catch (NumberFormatException e) {
                    film.setDuration(0);
                }

                // Date
                film.setReleaseDate(DateConverter.localDateToDate(releaseDate.getValue()));

                // Picture
                String pictureName = (filmManager.count() + 1) + "";
                uploadsRepository.saveAsFilmPicture(uploadsRepository.getFilmPicture(film.getPicture()), pictureName);
                film.setPicture(pictureName);

                // Validate data
                errors = FilmValidator.validate(film);

            } catch (Exception e) {
                errors.add("Une erreur s'est produite. Veuillez vérifier vos données et réessayer");
                System.out.println(e);
            }

            if (errors.size() > 0) {
                String errorString = "";

                for (String error: errors) {
                    errorString += error + "\n";
                }

                errorDisplay.setText(errorString);
            } else {
                filmManager.addFilm(film);
                filmManager.persist();

                Application.showReadStage(film);
            }

        }
    }

}
