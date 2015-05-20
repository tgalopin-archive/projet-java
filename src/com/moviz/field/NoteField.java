package com.moviz.field;

import com.moviz.main.Application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Titouan Galopin <galopintitouan@gmail.com>
 */
public class NoteField extends HBox {

    /*
     * Icons
     */
    protected Image starFullIcon;
    protected Image starEmptyIcon;

    /**
     * Has been noted yet?
     */
    protected boolean noted;

    /**
     * Current note
     */
    protected int note;

    /**
     * Changement handler
     */
    protected NoteFieldHandler handler;

    /**
     * Note stars
     */
    protected ArrayList<ImageView> imageViews;

    /**
     * Text display
     */
    protected Text display;


    /**
     * Constructor
     */
    public NoteField(boolean noted, int defaultNote) {
        this.noted = noted;
        this.note = defaultNote;

        this.build();
    }

    /**
     * Constructor
     */
    public NoteField(boolean noted, int defaultNote, NoteFieldHandler handler) {
        this.noted = noted;
        this.note = defaultNote;
        this.handler = handler;

        this.build();
    }

    /**
     * Set the changement handler
     */
    public void setOnAction(NoteFieldHandler handler) {
        this.handler = handler;
    }




    /**
     * Build the field sub view
     */
    private void build() {

        this.starFullIcon = new Image((new File(Application.getResourcesDirectory() + "/img/star-full-large.png")).toURI().toString());
        this.starEmptyIcon = new Image((new File(Application.getResourcesDirectory() + "/img/star-empty-large.png")).toURI().toString());

        this.setAlignment(Pos.CENTER_LEFT);

        // Text display
        if (this.noted) {
            this.display = new Text(this.note + " / 5");
        } else {
            this.display = new Text("- / 5");
        }

        HBox.setMargin(this.display, new Insets(0, 10, 0, 0));

        this.getChildren().add(this.display);

        // Stars
        this.imageViews = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            // Image
            Image icon;

            if (i <= this.note) {
                icon = this.starFullIcon;
            } else {
                icon = this.starEmptyIcon;
            }

            ImageView imageView = new ImageView(icon);
            imageView.setFitHeight(20);
            imageView.setPickOnBounds(true);
            imageView.setPreserveRatio(true);

            this.imageViews.add(imageView);

            // Button
            Button button = new Button();

            button.getStyleClass().add("note-editable-button");
            button.setGraphic(imageView);

            button.setOnMouseEntered(new OnMouseEnteredHandler(this.imageViews, i));
            button.setOnMouseExited(new OnMouseLeaveHandler(this.imageViews));
            button.setOnAction(new OnClickHandler(this.imageViews, i));

            HBox.setMargin(button, new Insets(0, 2, 0, 0));

            // Add as a child
            this.getChildren().add(button);
        }

    }




    /*
     * Handlers
     */

    private class OnMouseEnteredHandler implements EventHandler<MouseEvent> {
        protected ArrayList<ImageView> imageViews;
        protected int noteValue;

        public OnMouseEnteredHandler(ArrayList<ImageView> imageViews, int noteValue) {
            this.imageViews = imageViews;
            this.noteValue = noteValue;
        }

        @Override
        public void handle(MouseEvent event) {
            int i = 1;

            display.setText(noteValue + " / 5");

            for (ImageView imageView: this.imageViews) {
                if (i <= this.noteValue) {
                    imageView.setImage(starFullIcon);
                } else {
                    imageView.setImage(starEmptyIcon);
                }

                i++;
            }
        }
    }

    private class OnMouseLeaveHandler implements EventHandler<MouseEvent> {
        protected ArrayList<ImageView> imageViews;

        public OnMouseLeaveHandler(ArrayList<ImageView> imageViews) {
            this.imageViews = imageViews;
        }

        @Override
        public void handle(MouseEvent event) {
            if (noted) {
                display.setText(note + " / 5");
            } else {
                display.setText("- / 5");
            }

            int i = 1;

            for (ImageView imageView: this.imageViews) {
                if (i <= note) {
                    imageView.setImage(starFullIcon);
                } else {
                    imageView.setImage(starEmptyIcon);
                }

                i++;
            }
        }
    }

    private class OnClickHandler implements EventHandler<ActionEvent> {
        protected ArrayList<ImageView> imageViews;
        protected int noteValue;

        public OnClickHandler(ArrayList<ImageView> imageViews, int noteValue) {
            this.imageViews = imageViews;
            this.noteValue = noteValue;
        }

        @Override
        public void handle(ActionEvent event) {
            boolean wasNoted = noted;
            int oldNote = note;
            int newNote = noteValue;

            noted = true;
            note = this.noteValue;

            display.setText(note + " / 5");

            int i = 1;

            for (ImageView imageView: this.imageViews) {
                if (i <= note) {
                    imageView.setImage(starFullIcon);
                } else {
                    imageView.setImage(starEmptyIcon);
                }

                i++;
            }

            if (handler != null) {
                handler.handle(wasNoted, oldNote, newNote);
            }
        }
    }

}
