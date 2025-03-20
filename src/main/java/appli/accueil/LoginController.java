package appli.accueil;
import appli.StartApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import repository.UtilisateurRepository;
import model.Utilisateur;


import java.io.IOException;

public class LoginController {
    private UtilisateurRepository utilisateurRepository = new UtilisateurRepository();

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
        BCryptPasswordEncoder hasher = new BCryptPasswordEncoder();
        Utilisateur utilisateur = utilisateurRepository.getUtilisateurParEmail(email.getText());

        if (utilisateur != null && hasher.matches(mdp.getText(), utilisateur.getMdp())) {
            System.out.println("Connexion réussis");
        }
        else {
            erreur.setText("Email ou mot de passe incorrect");
        }
        /*if (utilisateur == null) {
            erreur.setText("email ou mdp incorrect");
        }
        else if (!hasher.matches(utilisateur.getMdp(), mdp.getText())) {
            erreur.setText("email ou mdp incorrect");
        }
        else {
            erreur.setText(" ");
            System.out.println("Connexion réussi");
        }*/
    }

    @FXML
    void boutonInscription(ActionEvent event) throws IOException {
        StartApplication.changeScene("accueil/Inscription");
    }

    @FXML
    void boutonMdpOublie(ActionEvent event) {

    }

}
