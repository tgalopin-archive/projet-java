package com.moviz.controller;

import com.moviz.entity.Film;
import com.moviz.entity.FilmNote;
import com.moviz.entity.User;
import com.moviz.field.NoteField;
import com.moviz.field.NoteFieldHandler;
import com.moviz.main.Application;
import com.moviz.model.FilmManager;
import com.moviz.service.UploadsRepository;
import com.moviz.subview.FilmSubView;
import com.moviz.util.DateConverter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller to delete a film
 */
public class DeleteController implements Initializable {

    /**
     * Film to display
     */
    private Film currentFilm;


    /*
     * View
     */
    @FXML
    private Button back;

    @FXML
    private Button confirm;

    @FXML
    private Button cancel;

    @FXML
    private ImageView picture;

    @FXML
    private Text title;

    @FXML
    private Text question;


    /*
     * Services
     */
    protected FilmManager filmManager;
    protected UploadsRepository uploadsRepository;


    /**
     * Constructor
     * The controller requires a film to display
     *
     * @param currentFilm The film to display
     */
    public DeleteController(Film currentFilm) {
        this.currentFilm = currentFilm;
    }

    /**
     * Initialize the controller and the view
     *
     * @param url The Scene URL
     * @param resourceBundle The resources bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.filmManager = Application.getFilmManager();
        this.uploadsRepository = Application.getUploadsRepository();

        // Default values
        this.picture.setImage(new Image(this.uploadsRepository.getFilmPicturePath(currentFilm.getPicture())));
        this.title.setText(currentFilm.getTitle());
        this.question.setText("Voulez-vous vraiment supprimer le film \"" + currentFilm.getTitle() + "\" ?");

        // Event handlers
        this.back.setOnAction(new BackButtonHandler());
        this.cancel.setOnAction(new BackButtonHandler());
        this.confirm.setOnAction(new DeleteButtonHandler());
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
            Application.showReadStage(currentFilm);
        }
    }

    /**
     * Back button
     */
    private class DeleteButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            filmManager.removeFilm(currentFilm);
            filmManager.persist();

            Application.showMainStage();
        }
    }

}
