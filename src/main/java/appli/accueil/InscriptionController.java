package appli.accueil;

import appli.StartApplication;
import com.mysql.cj.util.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import repository.UtilisateurRepository;
import model.Utilisateur;


public class InscriptionController {
    private UtilisateurRepository utilisateurRepository = new UtilisateurRepository();

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
    private PasswordField confirmationmdp;

    @FXML
    private Button inscription;

    @FXML
    private Label erreur;


    @FXML
    void boutonConnexion(ActionEvent event) throws IOException {
        StartApplication.changeScene("accueil/Login");

    }

    @FXML
    void boutonInscription(ActionEvent event) throws IOException {

        BCryptPasswordEncoder hasher = new BCryptPasswordEncoder();
        Utilisateur utilisateur = new Utilisateur(nom.getText(), prenom.getText(), email.getText(), hasher.encode(mdp.getText()));

        if (nom.getText().isEmpty() || prenom.getText().isEmpty() || email.getText().isEmpty() || mdp.getText().isEmpty() || confirmationmdp.getText().isEmpty()) {
            erreur.setText("tout les champs doivent etre rempli");

        }
        else if (utilisateurRepository.getUtilisateurParEmail(email.getText()) != null) {
            erreur.setText("l'email entrée est déjà utilisé");
        }
        else if (!mdp.getText().equals(confirmationmdp.getText())) {
            erreur.setText("les mot de passe ne correspondent pas");
        }
        else {
            erreur.setText(" ");
            System.out.println("Inscription réussis");
            utilisateurRepository.ajouterUtilisateur(utilisateur);
            StartApplication.changeScene("accueil/Login");

        }
    }

}

