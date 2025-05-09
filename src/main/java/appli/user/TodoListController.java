package appli.user;

import appli.StartApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Liste;
import model.Tache;
import model.Type;
import model.Utilisateur;
import repository.ListeRepository;
import repository.TacheRepository;
import repository.TypeRepository;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TodoListController implements Initializable {

    @FXML
    private Label utilisateurLabel;

    @FXML
    private ListView<Liste> listesListView;

    @FXML
    private TextField nomListeField;

    @FXML
    private Button ajouterListeBtn;

    @FXML
    private Button supprimerListeBtn;

    @FXML
    private Label listeSelectionneeLabel;
    @FXML
    private TableView<Tache> tachesTableView;

    @FXML
    private TextField nomTacheField;

    @FXML
    private ComboBox<Type> typeComboBox;

    @FXML
    private ComboBox<String> etatComboBox;

    @FXML
    private Button ajouterTacheBtn;


    @FXML
    private Button supprimerTacheBtn;

    @FXML
    private Label messageLabel;

    private Utilisateur utilisateur;
    private Liste listeSelectionnee;
    private Tache tacheSelectionnee;
    
    private ListeRepository listeRepository;
    private TacheRepository tacheRepository;
    private TypeRepository typeRepository;
    
    private ObservableList<Liste> listes;
    private ObservableList<Tache> taches;
    private ObservableList<Type> types;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listeRepository = new ListeRepository();
        tacheRepository = new TacheRepository();
        typeRepository = new TypeRepository();

        TableColumn<Tache, Boolean> colCheckbox = new TableColumn<>("");
        colCheckbox.setCellFactory(column -> new javafx.scene.control.cell.CheckBoxTableCell<>());
        colCheckbox.setCellValueFactory(cellData -> new javafx.beans.property.SimpleBooleanProperty(false));
        colCheckbox.setPrefWidth(30);
        colCheckbox.setEditable(true);
        colCheckbox.setResizable(false);
        
        TableColumn<Tache, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colNom.setPrefWidth(170);
        
        TableColumn<Tache, String> colEtat = new TableColumn<>("État");
        colEtat.setCellValueFactory(new PropertyValueFactory<>("etatString"));
        colEtat.setPrefWidth(100);
        
        TableColumn<Tache, String> colType = new TableColumn<>("Type");
        colType.setCellValueFactory(new PropertyValueFactory<>("nomType"));
        colType.setPrefWidth(100);
        
        tachesTableView.getColumns().addAll(colCheckbox, colNom, colEtat, colType);
        tachesTableView.setEditable(true);

        etatComboBox.setItems(FXCollections.observableArrayList("À faire", "En cours", "Terminée"));

        chargerTypes();

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

    public void initData(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        utilisateurLabel.setText(utilisateur.getNom() + " " + utilisateur.getPrenom());
        chargerListes();
    }

    private void chargerListes() {
        ArrayList<Liste> listesUtilisateur = listeRepository.getListesParUtilisateur(utilisateur.getIdUtilisateur());
        listes = FXCollections.observableArrayList(listesUtilisateur);

        listesListView.setCellFactory(param -> new ListCell<Liste>() {
            @Override
            protected void updateItem(Liste item, boolean empty) {
                super.updateItem(item, empty);
                
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNom());
                }
            }
        });
        
        listesListView.setItems(listes);
        
        if (listes.isEmpty()) {
            listeSelectionneeLabel.setText("Aucune liste disponible");
            tachesTableView.getItems().clear();
        }
    }

    private void chargerTypes() {
        ArrayList<Type> typesDisponibles = typeRepository.getAllTypes();
        types = FXCollections.observableArrayList(typesDisponibles);
        typeComboBox.setItems(types);
        
        if (types.isEmpty()) {
            Type typeUrgent = new Type("Urgent", "#e74c3c");
            Type typeImportant = new Type("Important", "#f39c12");
            Type typeNormal = new Type("Normal", "#3498db");
            
            int idUrgent = typeRepository.ajouterType(typeUrgent);
            int idImportant = typeRepository.ajouterType(typeImportant);
            int idNormal = typeRepository.ajouterType(typeNormal);
            
            if (idUrgent > 0) {
                typeUrgent.setIdType(idUrgent);
                types.add(typeUrgent);
            }
            
            if (idImportant > 0) {
                typeImportant.setIdType(idImportant);
                types.add(typeImportant);
            }
            
            if (idNormal > 0) {
                typeNormal.setIdType(idNormal);
                types.add(typeNormal);
            }
        }
    }

    private void chargerTaches(int idListe) {
        ArrayList<Tache> tachesListe = tacheRepository.getTachesParListe(idListe);
        taches = FXCollections.observableArrayList(tachesListe);
        tachesTableView.setItems(taches);
    }

    @FXML
    public void selectionnerListe(MouseEvent event) {
        listeSelectionnee = listesListView.getSelectionModel().getSelectedItem();
        
        if (listeSelectionnee != null) {
            listeSelectionneeLabel.setText(listeSelectionnee.getNom());
            supprimerListeBtn.setDisable(false);

            chargerTaches(listeSelectionnee.getIdListe());

            nomTacheField.clear();
            etatComboBox.getSelectionModel().clearSelection();
            typeComboBox.getSelectionModel().clearSelection();
            supprimerTacheBtn.setDisable(true);
        } else {
            listeSelectionneeLabel.setText("Aucune liste sélectionnée");
            supprimerListeBtn.setDisable(true);
            tachesTableView.getItems().clear();
        }
    }

    @FXML
    public void selectionnerTache(MouseEvent event) {
        tacheSelectionnee = tachesTableView.getSelectionModel().getSelectedItem();
        
        if (tacheSelectionnee != null) {
            nomTacheField.setText(tacheSelectionnee.getNom());

            switch (tacheSelectionnee.getEtat()) {
                case 0:
                    etatComboBox.getSelectionModel().select("À faire");
                    break;
                case 1:
                    etatComboBox.getSelectionModel().select("En cours");
                    break;
                case 2:
                    etatComboBox.getSelectionModel().select("Terminée");
                    break;
            }

            for (Type type : types) {
                if (type.getIdType() == tacheSelectionnee.getRefType()) {
                    typeComboBox.getSelectionModel().select(type);
                    break;
                }
            }
            
            supprimerTacheBtn.setDisable(false);
        } else {
            nomTacheField.clear();
            etatComboBox.getSelectionModel().clearSelection();
            typeComboBox.getSelectionModel().clearSelection();
            supprimerTacheBtn.setDisable(true);
        }
    }

    @FXML
    public void ajouterListe() {
        String nomListe = nomListeField.getText().trim();
        
        if (nomListe.isEmpty()) {
            messageLabel.setText("Veuillez saisir un nom pour la liste.");
            messageLabel.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }
        
        Liste nouvelleListe = new Liste(nomListe);
        int idListe = listeRepository.ajouterListe(nouvelleListe);
        
        if (idListe > 0) {
            nouvelleListe.setIdListe(idListe);

            boolean associationReussie = listeRepository.associerListeUtilisateur(idListe, utilisateur.getIdUtilisateur());
            
            if (associationReussie) {
                listes.add(nouvelleListe);
                nomListeField.clear();
                messageLabel.setText("Liste ajoutée avec succès !");
                messageLabel.setTextFill(javafx.scene.paint.Color.GREEN);
            } else {
                messageLabel.setText("Erreur lors de l'association de la liste à l'utilisateur.");
                messageLabel.setTextFill(javafx.scene.paint.Color.RED);
            }
        } else {
            messageLabel.setText("Erreur lors de l'ajout de la liste.");
            messageLabel.setTextFill(javafx.scene.paint.Color.RED);
        }
    }

    @FXML
    public void supprimerListe() {
        if (listeSelectionnee == null) {
            messageLabel.setText("Aucune liste sélectionnée.");
            messageLabel.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }
        
        boolean succes = listeRepository.supprimerListe(listeSelectionnee.getIdListe());
        
        if (succes) {
            listes.remove(listeSelectionnee);
            listeSelectionnee = null;
            listeSelectionneeLabel.setText("Aucune liste sélectionnée");
            tachesTableView.getItems().clear();
            nomListeField.clear();
            supprimerListeBtn.setDisable(true);
            messageLabel.setText("Liste supprimée avec succès !");
            messageLabel.setTextFill(javafx.scene.paint.Color.GREEN);
        } else {
            messageLabel.setText("Erreur lors de la suppression de la liste.");
            messageLabel.setTextFill(javafx.scene.paint.Color.RED);
        }
    }

    @FXML
    public void ajouterTache() {
        if (listeSelectionnee == null) {
            messageLabel.setText("Veuillez sélectionner une liste.");
            messageLabel.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }
        
        String nomTache = nomTacheField.getText().trim();
        Type typeSelectionne = typeComboBox.getValue();
        String etatSelectionne = etatComboBox.getValue();
        
        if (nomTache.isEmpty() || typeSelectionne == null || etatSelectionne == null) {
            messageLabel.setText("Veuillez remplir tous les champs.");
            messageLabel.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }

        int etat;
        switch (etatSelectionne) {
            case "En cours":
                etat = 1;
                break;
            case "Terminée":
                etat = 2;
                break;
            default:
                etat = 0;
        }
        
        Tache nouvelleTache = new Tache(nomTache, etat, listeSelectionnee.getIdListe(), typeSelectionne.getIdType());
        int idTache = tacheRepository.ajouterTache(nouvelleTache);
        
        if (idTache > 0) {
            nouvelleTache.setIdTache(idTache);
            nouvelleTache.setNomListe(listeSelectionnee.getNom());
            nouvelleTache.setNomType(typeSelectionne.getNom());
            nouvelleTache.setCodeCouleur(typeSelectionne.getCodeCouleur());
            
            taches.add(nouvelleTache);
            nomTacheField.clear();
            etatComboBox.getSelectionModel().clearSelection();
            typeComboBox.getSelectionModel().clearSelection();
            messageLabel.setText("Tâche ajoutée avec succès !");
            messageLabel.setTextFill(javafx.scene.paint.Color.GREEN);
        } else {
            messageLabel.setText("Erreur lors de l'ajout de la tâche.");
            messageLabel.setTextFill(javafx.scene.paint.Color.RED);
        }
    }

    @FXML
    public void supprimerTache() {
        if (tacheSelectionnee == null) {
            messageLabel.setText("Aucune tâche sélectionnée.");
            messageLabel.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }
        
        boolean succes = tacheRepository.supprimerTache(tacheSelectionnee.getIdTache());
        
        if (succes) {
            taches.remove(tacheSelectionnee);
            tacheSelectionnee = null;
            nomTacheField.clear();
            etatComboBox.getSelectionModel().clearSelection();
            typeComboBox.getSelectionModel().clearSelection();
            supprimerTacheBtn.setDisable(true);
            messageLabel.setText("Tâche supprimée avec succès !");
            messageLabel.setTextFill(javafx.scene.paint.Color.GREEN);
        } else {
            messageLabel.setText("Erreur lors de la suppression de la tâche.");
            messageLabel.setTextFill(javafx.scene.paint.Color.RED);
        }
    }

    @FXML
    public void retour() throws IOException {
        StartApplication.changeScene("accueil/GestionUser");
    }
}