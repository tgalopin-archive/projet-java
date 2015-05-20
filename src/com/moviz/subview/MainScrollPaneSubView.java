package com.moviz.subview;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;

/**
 * A sub-view build view elements based on the given data in pure Java
 *
 * @author Titouan Galopin <galopintitouan@gmail.com>
 */
public class MainScrollPaneSubView {

    /**
     * Create the scroll pane using the flow pane
     *
     * @param scrollPane ScrollPane
     * @param listPane FlowPane
     */
    public static void renderIn(ScrollPane scrollPane, FlowPane listPane) {
        scrollPane.viewportBoundsProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds) {
                listPane.setPrefWidth(bounds.getWidth());
                listPane.setPrefHeight(bounds.getHeight());
            }
        });
    }

}
