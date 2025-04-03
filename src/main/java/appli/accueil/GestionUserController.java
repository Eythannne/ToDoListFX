package appli.accueil;
import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Utilisateur;
import repository.UtilisateurRepository;
import session.SessionUtilisateur;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GestionUserController implements Initializable {
    @FXML
    private TableView<Utilisateur> tableauUser;

    @FXML
    private Button userSupp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String [][] colonnes = {
                { "Id Utilisateur","idUtilisateur" },
                { "Nom","nom" },
                { "Prénom","prenom" },
                { "Email","email" },
                { "Mot de passe","mdp" },
                { "Rôle","role" },
        };

        for ( int i = 0 ; i < colonnes.length ; i ++ ){
            TableColumn<Utilisateur,String> maCol = new TableColumn<>(colonnes[i][0]);
            maCol.setCellValueFactory(
                    new PropertyValueFactory<Utilisateur,String>(colonnes[i][1]));
            tableauUser.getColumns().add(maCol);
        }
        UtilisateurRepository us = new UtilisateurRepository();
        tableauUser.getItems().addAll(us.getAllUtilisateurs());

    }
    @FXML
    public void cliqueTableau(javafx.scene.input.MouseEvent mouseEvent) {
        Utilisateur selection = tableauUser.getSelectionModel().getSelectedItem();
        if (selection != null) {
            userSupp.setDisable(false);
        }else{
            userSupp.setDisable(true);
        }
        if (mouseEvent.getClickCount() == 2) { // Vérifie si c'est un double-clic
            if (selection != null) {
                StartApplication.changeScene(“user/modificationUser”);
            }
        }
    }

    @FXML
    public void supprimer(javafx.event.ActionEvent actionEvent) {
        Utilisateur utilisateurSelectionne = tableauUser.getSelectionModel().getSelectedItem();

        if (utilisateurSelectionne != null) {
            UtilisateurRepository rep = new UtilisateurRepository();
            rep.supprimerUtilisateurParEmail(utilisateurSelectionne.getEmail());

        }
        tableauUser.getItems().remove(utilisateurSelectionne);
    }
}
