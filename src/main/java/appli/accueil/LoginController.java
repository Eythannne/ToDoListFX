/*

        */
package appli.accueil;

import appli.StartApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    void boutonConnexion(ActionEvent event) {
        String emailtemp = "test@gmail.com";
        String mdptemp = "1234";

        email.getText();
        mdp.getText();

        if (!emailtemp.equals(email.getText()) || !mdptemp.equals(mdp.getText())) {
            System.out.println("email ou mot de passe incorrect");

        }
        else {
            System.out.println("Parfait");
        }
        System.out.println(email.getText());
        System.out.println(mdp.getText());
    }

    @FXML
    void boutonInscription(ActionEvent event) throws IOException {
        StartApplication.changeScene("accueil/Inscription");
    }

    @FXML
    void boutonMdpOublie(ActionEvent event) {

    }

}
