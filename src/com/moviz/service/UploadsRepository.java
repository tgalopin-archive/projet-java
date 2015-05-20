package com.moviz.service;

import java.io.*;

/**
 * The uploads repository provides helpers to access (read / write) films and users pictures
 *
 * @author Titouan Galopin <galopintitouan@gmail.com>
 */
public class UploadsRepository {

    /**
     * The uploads directory application parameter
     */
    protected String uploadsDirectory;

    /**
     * Constructor
     *
     * @param uploadsDirectory The uploads directory
     */
    public UploadsRepository(String uploadsDirectory) {
        this.uploadsDirectory = uploadsDirectory;
    }


    /*
     * Read
     */

    /**
     * Get a film picture File instance
     */
    public File getFilmPicture(String pictureName) {
        return new File(this.uploadsDirectory + "/films/" + pictureName + ".jpg");
    }

    /**
     * Get a film picture as a path
     */
    public String getFilmPicturePath(String pictureName) {
        return this.getFilmPicture(pictureName).toURI().toString();
    }

    /**
     * Get a user picture File instance
     */
    public File getUserPicture(String pictureName) {
        return new File(this.uploadsDirectory + "/users/" + pictureName + ".png");
    }

    /**
     * Get a user picture as a path
     */
    public String getUserPicturePath(String pictureName) {
        return this.getUserPicture(pictureName).toURI().toString();
    }


    /*
     * Write
     */

    /**
     * Save a film picture file
     */
    public boolean saveAsFilmPicture(File picture, String pictureName) {
        return this.copy(picture, this.getFilmPicture(pictureName));
    }

    /**
     * Save a user picture file
     */
    public boolean saveAsUserPicture(File picture, String pictureName) {
        return this.copy(picture, this.getUserPicture(pictureName));
    }

    /**
     * Copy a file
     */
    private boolean copy(File source, File target) {
        try {
            InputStream input = new FileInputStream(source);
            OutputStream output = new FileOutputStream(target);

            byte[] buf = new byte[1024];

            int bytesRead;

            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }

            input.close();
            output.close();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

}
