package com.moviz.service;

import javafx.stage.Stage;
import java.io.File;

/**
 * Open a "Choose file" dialog using the application stage
 *
 * @author Titouan Galopin <galopintitouan@gmail.com>
 */
public class FileChooser {

    /**
     * The main stage used to open the dialog
     */
    protected Stage appStage;


    /**
     * Constructor
     *
     * @param appStage Stage
     */
    public FileChooser(Stage appStage) {
        this.appStage = appStage;
    }

    /**
     * Open the dialog and return the resultig image file (or null if canceled)
     *
     * @return File
     */
    public File chooseImageFile() {
        javafx.stage.FileChooser fileChooser = new javafx.stage.FileChooser();
        fileChooser.getExtensionFilters().add(new javafx.stage.FileChooser.ExtensionFilter("JPG", "*.jpg"));

        return fileChooser.showOpenDialog(this.appStage);
    }

}
