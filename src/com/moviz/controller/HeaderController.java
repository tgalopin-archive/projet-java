package com.moviz.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.moviz.subview.AdminSubView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;

import com.moviz.main.Application;
import com.moviz.entity.User;
import com.moviz.service.SecurityContext;
import com.moviz.subview.UserSubView;
import javafx.scene.layout.HBox;

/**
 * Controller for the User box and the logout button
 */
public class HeaderController implements Initializable {

    /*
     * View
     */
    @FXML
    protected Button userButton;

    @FXML
    protected ImageView avatarDisplay;

    @FXML
    protected ChoiceBox<String> userChoiceBox;

    @FXML
    protected HBox menuBox;


    /*
     * Services
     */
    protected SecurityContext securityContext;



    /**
     * Intiialize the controller
     *
     * @param url URL
     * @param resourceBundle ResourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.securityContext = Application.getSecurityContext();

        User currentUser = this.securityContext.getCurrentUser();
        UserSubView.renderUserBoxIn(currentUser, this.userButton, this.avatarDisplay, this.userChoiceBox);

        if (currentUser.getRole() == User.ROLE_ADMIN) {
            Button adminCreateButton = AdminSubView.createNewFilmButton();
            adminCreateButton.setOnAction(new AdminCreateButtonClickedHandler());

            this.menuBox.getChildren().add(2, adminCreateButton);
        }

        this.userChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChoiceBoxItemChosenHandler());
        this.userButton.setOnAction(new UserButtonClickedHandler());
    }



    /*
     * Handlers
     */

    /**
     * Handler for create button
     */
    private class AdminCreateButtonClickedHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Application.showCreateStage();
        }
    }

    /**
     * Handler for user button: show logout button
     */
    private class UserButtonClickedHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            userChoiceBox.show();
        }
    }

    /**
     * Handler for choice box: logout the user
     */
    private class ChoiceBoxItemChosenHandler implements ChangeListener<String> {
        @Override
        public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            securityContext.logout();
            Application.showLoginStage();
        }
    }

}
