/*

        */
package appli.accueil;

import appli.StartApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {

    @FXML
    private Button connexion;

    @FXML
    private TextField email;

    @FXML
    private Button inscription;

    @FXML
    private PasswordField mdp;

    @FXML
    private Button oubliemdp;

    @FXML
    private Label erreur;

    @FXML
    void boutonConnexion(ActionEvent event) {
        String emailtemp = "user@gmail.com";
        String mdptemp = "user";

        email.getText();
        mdp.getText();

        if (!emailtemp.equals(email.getText()) || !mdptemp.equals(mdp.getText())) {
            erreur.setText("email ou mdp incorrect");

        }
        else {
            erreur.setText(" ");
            System.out.println("Connexion réussi");
        }
    }

    @FXML
    void boutonInscription(ActionEvent event) throws IOException {
        StartApplication.changeScene("accueil/Inscription");
    }

    @FXML
    void boutonMdpOublie(ActionEvent event) {

    }

}
