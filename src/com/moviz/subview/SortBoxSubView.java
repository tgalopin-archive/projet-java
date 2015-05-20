package com.moviz.subview;

import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;

/**
 * @author Titouan Galopin <galopintitouan@gmail.com>
 */
public class SortBoxSubView {

    /**
     * Render the sort choice box
     *
     * @param sortChoiceBox The sort choice box object to edit
     */
    public static void renderIn(ChoiceBox<String> sortChoiceBox) {
        sortChoiceBox.setItems(FXCollections.observableArrayList(
            "Nom (croissant)",
            "Nom (décroissant)",
            "Date de sortie (croissant)",
            "Date de sortie (décroissant)",
            "Note moyenne (croissant)",
            "Note moyenne (décroissant)"
        ));
    }

}
