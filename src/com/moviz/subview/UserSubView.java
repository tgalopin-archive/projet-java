package com.moviz.subview;

import com.moviz.entity.User;
import com.moviz.main.Application;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

/**
 * A sub-view build view elements based on the given data in pure Java
 *
 * @author Titouan Galopin <galopintitouan@gmail.com>
 */
public class UserSubView {

    /**
     * Render the user box
     *
     * @param currentUser Current user
     * @param userButton User button
     * @param avatarDisplay Avatar image view
     * @param userChoiceBox Choice box to log out
     */
    public static void renderUserBoxIn(User currentUser,
                                       Button userButton,
                                       ImageView avatarDisplay,
                                       ChoiceBox<String> userChoiceBox) {

        String userBoxText = currentUser.getFullName();

        if (currentUser.getRole() == User.ROLE_ADMIN) {
            userBoxText += " (administrateur)";
        }

        userButton.setText(userBoxText);
        avatarDisplay.setImage(UserSubView.createUserPicture(currentUser));
        userChoiceBox.setItems(FXCollections.observableArrayList("Changer d'utilisateur"));
    }

    /**
     * Create the user picture Image object
     *
     * @param user User
     * @return The picture
     */
    public static Image createUserPicture(User user) {
        return new Image(Application.getUploadsRepository().getUserPicturePath(user.getPicture()));
    }

}
