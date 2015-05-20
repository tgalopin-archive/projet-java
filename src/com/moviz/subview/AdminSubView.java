package com.moviz.subview;

import com.moviz.main.Application;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.File;

/**
 * @author Titouan Galopin <galopintitouan@gmail.com>
 */
public class AdminSubView {

    /**
     * Create the header "new film" button
     */
    public static Button createNewFilmButton() {
        File imageFile = new File(Application.getResourcesDirectory() + "/img/plus.png");
        Image image = new Image(imageFile.toURI().toString());

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(10);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);

        Button newFilmButton = new Button("Ajouter un film");
        newFilmButton.getStyleClass().add("header-button");
        newFilmButton.setGraphic(imageView);

        HBox.setMargin(newFilmButton, new Insets(0, 20, 0, 0));

        return newFilmButton;
    }

}
