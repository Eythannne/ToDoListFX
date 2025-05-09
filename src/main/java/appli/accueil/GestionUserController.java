package appli.accueil;
import appli.StartApplication;
import appli.user.ModificationUserController;
import appli.user.TodoListController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Utilisateur;
import repository.UtilisateurRepository;
import session.SessionUtilisateur;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class GestionUserController implements Initializable {
    @FXML
    private TableView<Utilisateur> tableauUser;

    @FXML
    private Button userSupp;
    
    @FXML
    private Button modifierBtn;
    
    @FXML
    private Button todoListBtn;
    
    @FXML
    private Label messageLabel;
    
    private ObservableList<Utilisateur> listeUtilisateurs;
    private UtilisateurRepository utilisateurRepository;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        utilisateurRepository = new UtilisateurRepository();

        String[][] colonnes = {
                {"Id Utilisateur", "idUtilisateur"},
                {"Nom", "nom"},
                {"Prénom", "prenom"},
                {"Email", "email"},
                {"Rôle", "role"},
        };

        for (int i = 0; i < colonnes.length; i++) {
            TableColumn<Utilisateur, String> maCol = new TableColumn<>(colonnes[i][0]);
            maCol.setCellValueFactory(
                    new PropertyValueFactory<Utilisateur, String>(colonnes[i][1]));
            maCol.setPrefWidth(150);
            tableauUser.getColumns().add(maCol);
        }

        chargerUtilisateurs();

        messageLabel.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                new Thread(() -> {
                    try {
                        Thread.sleep(3000);
                        javafx.application.Platform.runLater(() -> messageLabel.setText(""));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });
    }

    private void chargerUtilisateurs() {
        ArrayList<Utilisateur> utilisateurs = utilisateurRepository.getAllUtilisateurs();
        listeUtilisateurs = FXCollections.observableArrayList(utilisateurs);
        tableauUser.setItems(listeUtilisateurs);
    }
    
    @FXML
    public void cliqueTableau(MouseEvent mouseEvent) throws IOException {
        Utilisateur selection = tableauUser.getSelectionModel().getSelectedItem();
        if (selection != null) {
            userSupp.setDisable(false);
            modifierBtn.setDisable(false);
            todoListBtn.setDisable(false);
        } else {
            userSupp.setDisable(true);
            modifierBtn.setDisable(true);
            todoListBtn.setDisable(true);
        }

        if (mouseEvent.getClickCount() == 2) {
            if (selection != null) {
                modifierUtilisateur();
            }
        }
    }

    @FXML
    public void supprimer(ActionEvent actionEvent) {
        Utilisateur utilisateurSelectionne = tableauUser.getSelectionModel().getSelectedItem();

        if (utilisateurSelectionne != null) {
            boolean supprime = utilisateurRepository.supprimerUtilisateurParEmail(utilisateurSelectionne.getEmail());
            
            if (supprime) {
                tableauUser.getItems().remove(utilisateurSelectionne);
                messageLabel.setText("Utilisateur supprimé avec succès !");
                messageLabel.setTextFill(javafx.scene.paint.Color.GREEN);
            } else {
                messageLabel.setText("Erreur lors de la suppression de l'utilisateur.");
                messageLabel.setTextFill(javafx.scene.paint.Color.RED);
            }

            userSupp.setDisable(true);
            modifierBtn.setDisable(true);
            todoListBtn.setDisable(true);
        }
    }
    
    @FXML
    public void modifierUtilisateur() {
        Utilisateur utilisateurSelectionne = tableauUser.getSelectionModel().getSelectedItem();
        
        if (utilisateurSelectionne != null) {
            try {
                StartApplication.changeScene("user/modificationUser");
                ModificationUserController controller = (ModificationUserController)
                        StartApplication.getControllerFromStage();
                controller.initData(utilisateurSelectionne);
            } catch (IOException e) {
                messageLabel.setText("Erreur lors de l'ouverture de la page de modification.");
                messageLabel.setTextFill(javafx.scene.paint.Color.RED);
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    public void ajouterUtilisateur() {
        try {
            StartApplication.changeScene("accueil/Inscription");
        } catch (IOException e) {
            messageLabel.setText("Erreur lors de l'ouverture de la page d'inscription.");
            messageLabel.setTextFill(javafx.scene.paint.Color.RED);
            e.printStackTrace();
        }
    }
    
    @FXML
    public void gererTodoList() {
        Utilisateur utilisateurSelectionne = tableauUser.getSelectionModel().getSelectedItem();
        
        if (utilisateurSelectionne != null) {
            try {
                StartApplication.changeScene("user/TodoList");
                TodoListController controller = (TodoListController)
                        StartApplication.getControllerFromStage();
                controller.initData(utilisateurSelectionne);
                messageLabel.setText("Chargement des tâches de l'utilisateur...");
                messageLabel.setTextFill(javafx.scene.paint.Color.GREEN);
            } catch (IOException e) {
                messageLabel.setText("Erreur lors de l'ouverture de la page des tâches.");
                messageLabel.setTextFill(javafx.scene.paint.Color.RED);
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void retour(ActionEvent actionEvent) throws IOException {
        StartApplication.changeScene("accueil/Accueil");
    }
}
