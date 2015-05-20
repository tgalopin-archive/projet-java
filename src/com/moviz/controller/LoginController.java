package com.moviz.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import com.moviz.main.Application;
import com.moviz.service.SecurityContext;


/**
 * Controller for the user authentication
 */
public class LoginController implements Initializable {

    /*
     * View
     */
    @FXML
    protected TextField loginField;

    @FXML
    protected PasswordField passwordField;

    @FXML
    protected Button loginButton;

    @FXML
    protected Text errorDisplay;


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
        this.loginButton.setOnAction(new LoginButtonClickedHandler());
    }



    /**
     * Handler for button click
     */
    private class LoginButtonClickedHandler implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            String login = loginField.getText();
            String password = passwordField.getText();

            if (login.length() == 0) {
                errorDisplay.setText("L'identifiant est requis");
                return;
            }

            if (password.length() == 0) {
                errorDisplay.setText("Le mot de passe est requis");
                return;
            }

            if (! securityContext.login(login, password)) {
                errorDisplay.setText("Ces identifiants sont invalides");
                return;
            }

            Application.showMainStage();
        }

    }

}
