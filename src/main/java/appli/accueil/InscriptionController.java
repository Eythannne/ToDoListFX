package appli.accueil;

import appli.StartApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class InscriptionController {

    @FXML
    private Button connexion;

    @FXML
    private TextField prenom;

    @FXML
    private TextField nom;

    @FXML
    private TextField email;

    @FXML
    private PasswordField mdp;

    @FXML
    private Button inscription;

    @FXML
    private Label erreur;


    @FXML
    void boutonConnexion(ActionEvent event) throws IOException {
        StartApplication.changeScene("accueil/Login");

    }

    @FXML
    void boutonInscription(ActionEvent event) {
        String nomx = "";
        String prenomx ="";
        String emailx ="";
        String mdpx ="";

        if (nomx.equals(nom.getText()) || prenomx.equals(prenom.getText()) || emailx.equals(email.getText()) || mdpx.equals(mdp.getText())) {
            erreur.setText("tout les champs doivent etre rempli");

        }
        else {
            erreur.setText(" ");
            System.out.println("Inscription réussis");
            System.out.println("- " + nom.getText());
            System.out.println("- " + prenom.getText());
            System.out.println("- " + email.getText());
            System.out.println("- " + mdp.getText());
        }
    }
}

