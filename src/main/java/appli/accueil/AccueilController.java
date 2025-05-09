package appli.accueil;
import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Utilisateur;
import session.SessionUtilisateur;

import java.io.IOException;

public class AccueilController {
    private Utilisateur utilisateurActuel;

    @FXML
    private Label bjr;
    public AccueilController() {
        Utilisateur utilisateurActuel = SessionUtilisateur.getInstance().getUtilisateur();
        if (utilisateurActuel != null) {
            System.out.println("Bienvenue à toi " + utilisateurActuel.getNom() + " !");
        }
        else {
            System.out.println("Bienvenue à toi l'ami !");
        }
    }

    @FXML
    void boutonDeconnexion() throws IOException {
        SessionUtilisateur.getInstance().deconnecter();
        System.out.println("Utilisateur déconnecté.");
        StartApplication.changeScene("accueil/Login");
    }

    @FXML
    void boutonGestionUtilisateur() throws IOException {
        System.out.println("Liste des utilisateurs !");
        StartApplication.changeScene("accueil/GestionUser");
    }

}