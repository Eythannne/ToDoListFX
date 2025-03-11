package appli.accueil;

import appli.StartApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class InscriptionController {

    @FXML
    private Button connexion;

    @FXML
    private TextField email;

    @FXML
    private TextField email1;

    @FXML
    private TextField email2;

    @FXML
    private Button inscription;

    @FXML
    private PasswordField mdp;

    @FXML
    void boutonConnexion(ActionEvent event) throws IOException {
        StartApplication.changeScene("accueil/Login");

    }

    @FXML
    void boutonInscription(ActionEvent event) {

    }

}
