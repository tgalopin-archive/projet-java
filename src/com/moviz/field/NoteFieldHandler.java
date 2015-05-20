package com.moviz.field;

/**
 * Handler to react to a note field change
 *
 * @author Titouan Galopin <galopintitouan@gmail.com>
 */
public interface NoteFieldHandler {
    void handle(boolean wasNoted, int oldNote, int newNote);
}
