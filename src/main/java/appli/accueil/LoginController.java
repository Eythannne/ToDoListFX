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
import session.SessionUtilisateur;


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
    void boutonConnexion(ActionEvent event) throws IOException {
        BCryptPasswordEncoder hasher = new BCryptPasswordEncoder();
        Utilisateur utilisateur = utilisateurRepository.getUtilisateurParEmail(email.getText());

        if (utilisateur != null && hasher.matches(mdp.getText(), utilisateur.getMdp())) {
            System.out.println("Connexion réussis");
            System.out.println("Connexion réussie pour : " + utilisateur.getNom());
            SessionUtilisateur.getInstance().sauvegardeSession(utilisateur);
            erreur.setVisible(false);
            StartApplication.changeScene("accueil/Accueil");
        }
        else {
            System.out.println("Échec de la connexion. Email ou mot de passe incorrect.");
            erreur.setText("Email ou mot de passe incorrect.");
            erreur.setVisible(true);
        }

        Utilisateur utilisateurActuel = SessionUtilisateur.getInstance().getUtilisateur();
        if (utilisateurActuel != null) {
            System.out.println("Utilisateur connecté : " + utilisateurActuel.getNom());
        }
    }


    @FXML
    void boutonInscription(ActionEvent event) throws IOException {
        StartApplication.changeScene("accueil/Inscription");
    }

    @FXML
    void boutonMdpOublie(ActionEvent event) {

    }

    @FXML
    protected void handleLogout() {
        SessionUtilisateur.getInstance().deconnecter();
        System.out.println("Utilisateur déconnecté.");
        // Redirection vers la page de connexion
    }
}
