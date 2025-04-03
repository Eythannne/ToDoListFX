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
            //bjr.setText("Bienvenue à toi " + utilisateurActuel.getNom() + " !");
            //bjr.setVisible(true);
        }
        else {
            System.out.println("Bienvenue à toi l'ami !");
            //bjr.setText("Bienvenue à toi l'ami !");
            //bjr.setVisible(true);
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