package com.moviz.controller;

import com.moviz.entity.Film;
import com.moviz.entity.FilmNote;
import com.moviz.entity.FilmSeen;
import com.moviz.entity.User;
import com.moviz.field.NoteField;
import com.moviz.field.NoteFieldHandler;
import com.moviz.main.Application;
import com.moviz.model.FilmManager;
import com.moviz.subview.FilmSubView;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Controller to see a film
 */
public class ReadController implements Initializable {

    /**
     * Film to display
     */
    private Film currentFilm;

    /**
     * Current user
     */
    private User currentUser;

    /**
     * Current user review of the film
     */
    private FilmNote currentUserReview;

    private NoteField reviewField;
    private Button edit;
    private Button delete;

    /*
     * View
     */
    @FXML
    private Button back;

    @FXML
    private HBox actions;

    @FXML
    private ImageView picture;

    @FXML
    private Text title;

    @FXML
    private Text synopsis;

    @FXML
    private Text date;

    @FXML
    private VBox currentUserReviewContainer;

    @FXML
    private VBox otherReviewsContainer;

    @FXML
    private Button seenUnseen;


    /*
     * Services
     */
    protected FilmManager filmManager;


    /**
     * Constructor
     * The controller requires a film to display
     *
     * @param currentFilm The film to display
     */
    public ReadController(Film currentFilm) {
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
        this.currentUser = Application.getSecurityContext().getCurrentUser();

        // Render the fiche
        FilmSubView.renderFicheIn(this.currentFilm, this.picture, this.title, this.synopsis, this.date);

        if (this.currentFilm.hasBeenSeenBy(this.currentUser)) {
            this.seenUnseen.setText("Marquer comme non-vu");
        } else {
            this.seenUnseen.setText("Marquer comme vu");
        }


        // Filter reviews
        ArrayList<FilmNote> othersNotes = new ArrayList<>();

        for (FilmNote filmNote: this.currentFilm.getNotes()) {
            if (! filmNote.getLogin().equals(this.currentUser.getLogin())) {
                othersNotes.add(filmNote);
            } else {
                this.currentUserReview = filmNote;
            }
        }


        // Render the editable note of the current user
        if (this.currentUserReview != null) {
            this.reviewField = new NoteField(true, this.currentUserReview.getValue());
        } else {
            this.reviewField = new NoteField(false, 0);
        }

        this.currentUserReviewContainer.getChildren().add(this.reviewField);


        // Render the notes of others in the global reviews
        FilmSubView.renderOthersReviewsIn(othersNotes, this.otherReviewsContainer);


        // Event handlers
        this.back.setOnAction(new BackButtonHandler());
        this.reviewField.setOnAction(new NoteChangeHandler());
        this.seenUnseen.setOnAction(new SeenUnseenButtonHandler());


        // Admin buttons
        if (Application.getSecurityContext().getCurrentUser().getRole() == User.ROLE_ADMIN) {
            this.edit = new Button("Editer la fiche");
            this.edit.setOnAction(new EditButtonHandler());

            this.delete = new Button("Supprimer la fiche");
            this.delete.setOnAction(new DeleteButtonHandler());

            HBox.setMargin(this.edit, new Insets(0, 0, 0, 20));
            HBox.setMargin(this.delete, new Insets(0, 0, 0, 20));

            this.actions.getChildren().addAll(this.edit, this.delete);
        }

    }

    /**
     * Back to the list button
     */
    private class BackButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Application.showMainStage();
        }
    }

    /**
     * Note changed handler
     */
    private class NoteChangeHandler implements NoteFieldHandler {
        @Override
        public void handle(boolean wasNoted, int oldNote, int newNote) {
            if (currentUserReview != null) {
                currentUserReview.setValue(newNote);
            } else {
                currentUserReview = new FilmNote(currentUser, newNote);
                currentFilm.getNotes().add(currentUserReview);
            }

            filmManager.persist();
        }
    }

    /**
     * Marked the film as seen/unseen
     */
    private class SeenUnseenButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if (currentFilm.hasBeenSeenBy(currentUser)) {
                FilmSeen filmSeen = currentFilm.getSeenBy(currentUser);

                if (filmSeen != null) {
                    currentFilm.getSeens().remove(filmSeen);
                    filmManager.persist();
                }

                seenUnseen.setText("Marquer comme vu");
            } else {
                FilmSeen filmSeen = currentFilm.getSeenBy(currentUser);

                if (filmSeen == null) {
                    currentFilm.getSeens().add(new FilmSeen(currentUser));
                    filmManager.persist();
                }

                seenUnseen.setText("Marquer comme non-vu");
            }
        }
    }

    /**
     * Edit the film button
     */
    private class EditButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Application.showEditStage(currentFilm);
        }
    }

    /**
     * Delete the film button
     */
    private class DeleteButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Application.showDeleteStage(currentFilm);
        }
    }

}
