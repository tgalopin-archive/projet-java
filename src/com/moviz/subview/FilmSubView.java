package com.moviz.subview;

import com.moviz.entity.Film;
import com.moviz.entity.FilmNote;
import com.moviz.main.Application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @author Titouan Galopin <galopintitouan@gmail.com>
 */
public class FilmSubView {

    /**
     * Render a given film fiche
     */
    public static void renderFicheIn(Film film, ImageView picture, Text title, Text synopsis, Text date) {
        title.setWrappingWidth(500);
        synopsis.setWrappingWidth(500);
        date.setWrappingWidth(500);

        title.setText(film.getTitle());
        synopsis.setText(film.getSynopsis());
        date.setText("Sortie le " + (new SimpleDateFormat("dd/MM/yyyy")).format(film.getReleaseDate()));

        picture.setImage(new Image(Application.getUploadsRepository().getFilmPicturePath(film.getPicture())));
    }

    /**
     * Render a given film reviews
     */
    public static void renderOthersReviewsIn(ArrayList<FilmNote> filmNotes, VBox container) {
        if (filmNotes.size() > 0) {
            for (FilmNote note: filmNotes) {
                HBox review = NoteSubView.createUserReviewBox(note);
                VBox.setMargin(review, new Insets(0, 0, 15, 0));

                container.getChildren().add(review);
            }
        } else {
            container.getChildren().add(new Text("Aucune autre note pour le moment"));
        }
    }

    /**
     * Create a Button element based on a given film
     *
     * @param film The film to use
     * @return The creted button
     */
    public static Button createListButton(Film film) {

        // Image
        ImageView imageView = createFilmImageView(film);
        imageView.setFitHeight(200);

        VBox.setMargin(imageView, new Insets(0, 0, 5, 0));


        // Name
        String title = film.getTitle();

        if (title.length() > 18) {
            title = title.substring(0, 18) + "...";
        }

        Text text = new Text();
        text.setText(title);
        text.setTextAlignment(TextAlignment.CENTER);

        VBox.setVgrow(text, Priority.ALWAYS);


        // Note
        HBox starsBox = NoteSubView.createNoteBox(film.getAverageNote());
        starsBox.setAlignment(Pos.TOP_CENTER);


        // Container
        VBox box = new VBox();
        box.setAlignment(Pos.TOP_CENTER);
        box.getChildren().addAll(imageView, text, starsBox);


        // Button
        Button button = new Button();
        button.setMaxWidth(200);
        button.setPrefWidth(200);
        button.setMinWidth(200);
        button.setMnemonicParsing(false);
        button.getStyleClass().add("list-item");
        button.setGraphic(box);

        FlowPane.setMargin(button, new Insets(0, 10, 10, 0));


        return button;
    }

    /**
     * Create an ImageView element based on a given film
     *
     * @param film The film to use
     * @return The creted ImageView
     */
    public static ImageView createFilmImageView(Film film) {
        Image image = new Image(Application.getUploadsRepository().getFilmPicturePath(film.getPicture()));

        ImageView imageView = new ImageView(image);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);

        return imageView;
    }


}
