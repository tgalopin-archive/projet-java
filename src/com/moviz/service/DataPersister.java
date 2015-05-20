package com.moviz.service;

import java.io.*;

/**
 * The data persister serialize and unserialize data into and from files
 *
 * @author Titouan Galopin <galopintitouan@gmail.com>
 */
public class DataPersister {

    protected String databaseDirectory;

    /**
     * Constructor
     *
     * @param databaseDirectory String
     */
    public DataPersister(String databaseDirectory) {
        this.databaseDirectory = databaseDirectory;
    }

    /**
     * Create a file ignoring if the error if the file already existed
     *
     * @param filename String
     */
    public void createFile(String filename) {
        try {
            (new File(this.getAbsolutePath(filename))).createNewFile();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Remove a file ignoring if the error if the file do not existed
     *
     * @param filename String
     */
    public void removeFile(String filename) {
        try {
            (new File(this.getAbsolutePath(filename))).delete();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Read a database file
     *
     * @param filename String
     * @return Object
     * @throws IOException
     */
    public Object read(String filename) throws IOException, ClassNotFoundException {
        ObjectInputStream in =
            new ObjectInputStream(
                new FileInputStream(
                    this.getAbsolutePath(filename)
                )
            );

        return in.readObject();
    }

    /**
     * Write in a database file
     *
     * @param filename Where to write
     * @param object What to write
     * @throws IOException
     */
    public void write(String filename, Serializable object) throws IOException {
        ObjectOutputStream out =
            new ObjectOutputStream(
                new FileOutputStream(
                    this.getAbsolutePath(filename)
                )
            );

        out.writeObject(object);
        out.close();
    }

    /**
     * Return the absolute path of the given com.moviz.data filename
     *
     * @param filename String
     * @return String
     */
    protected String getAbsolutePath(String filename) {
        return this.databaseDirectory + "/" + filename + ".data";
    }

}
