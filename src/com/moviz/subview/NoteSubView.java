package com.moviz.subview;

import com.moviz.entity.FilmNote;
import com.moviz.entity.User;
import com.moviz.field.NoteField;
import com.moviz.main.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;

/**
 * @author Titouan Galopin <galopintitouan@gmail.com>
 */
public class NoteSubView {

    /**
     * Create a user review box
     *
     * @param filmNote The review
     * @return The review display
     */
    public static HBox createUserReviewBox(FilmNote filmNote) {
        // User display
        User user = Application.getUserManager().findOneByLogin(filmNote.getLogin());

        ImageView avatar = new ImageView(UserSubView.createUserPicture(user));
        avatar.setFitHeight(50);
        avatar.setPickOnBounds(true);
        avatar.setPreserveRatio(true);

        HBox.setMargin(avatar, new Insets(0, 20, 0, 0));

        // User name and note
        Text fullName = new Text(user.getFullName());
        HBox noteStars = createNoteBox(filmNote.getValue());

        VBox.setMargin(fullName, new Insets(0, 0, 5, 0));

        VBox noteBox = new VBox();
        noteBox.getChildren().addAll(fullName, noteStars);

        // Box
        HBox box = new HBox();
        box.getChildren().addAll(avatar, noteBox);

        return box;
    }

    /**
     * Create a box for a given note stars
     *
     * @param note The value of the note
     * @return The stars
     */
    public static HBox createNoteBox(double note) {
        HBox noteBox = new HBox();

        String starFull = Application.getResourcesDirectory() + "/img/star-full.png";
        String starHalf = Application.getResourcesDirectory() + "/img/star-half.png";
        String starEmpty = Application.getResourcesDirectory() + "/img/star-empty.png";

        for (int i = 1; i <= 5; i++) {
            String icon;

            if (i <= note) {
                icon = starFull;
            } else if (i - 1 < note && i > note) {
                icon = starHalf;
            } else {
                icon = starEmpty;
            }

            ImageView imageView = new ImageView();
            imageView.setImage(new Image((new File(icon)).toURI().toString()));
            imageView.setFitHeight(12);
            imageView.setPickOnBounds(true);
            imageView.setPreserveRatio(true);

            noteBox.getChildren().add(imageView);
        }

        return noteBox;
    }

}
