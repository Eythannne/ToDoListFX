package repository;

import database.Database;
import model.Tache;
import model.Type;

import java.sql.*;
import java.util.ArrayList;

public class TacheRepository {
    private Connection connection;

    public TacheRepository() {
        connection = Database.getConnexion();
    }

    /**
     * Ajoute une nouvelle tâche
     * @param tache La tâche à ajouter
     * @return L'ID de la tâche créée, ou -1 en cas d'erreur
     */
    public int ajouterTache(Tache tache) {
        String sql = "INSERT INTO tache (nom, etat, ref_liste, ref_type) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, tache.getNom());
            stmt.setInt(2, tache.getEtat());
            stmt.setInt(3, tache.getRefListe());
            stmt.setInt(4, tache.getRefType());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la tâche : " + e.getMessage());
        }
        return -1;
    }

    /**
     * Récupère toutes les tâches d'une liste
     * @param idListe L'ID de la liste
     * @return Une liste de tâches
     */
    public ArrayList<Tache> getTachesParListe(int idListe) {
        ArrayList<Tache> taches = new ArrayList<>();
        String sql = "SELECT t.*, l.nom as nom_liste, ty.nom as nom_type, ty.code_couleur " +
                     "FROM tache t " +
                     "JOIN liste l ON t.ref_liste = l.id_liste " +
                     "JOIN type ty ON t.ref_type = ty.id_type " +
                     "WHERE t.ref_liste = ?";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idListe);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Tache tache = new Tache(
                        rs.getInt("id_tache"),
                        rs.getString("nom"),
                        rs.getInt("etat"),
                        rs.getInt("ref_liste"),
                        rs.getInt("ref_type")
                );
                tache.setNomListe(rs.getString("nom_liste"));
                tache.setNomType(rs.getString("nom_type"));
                tache.setCodeCouleur(rs.getString("code_couleur"));
                taches.add(tache);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des tâches : " + e.getMessage());
        }
        return taches;
    }

    /**
     * Récupère toutes les tâches d'un utilisateur
     * @param idUtilisateur L'ID de l'utilisateur
     * @return Une liste de tâches
     */
    public ArrayList<Tache> getTachesParUtilisateur(int idUtilisateur) {
        ArrayList<Tache> taches = new ArrayList<>();
        String sql = "SELECT t.*, l.nom as nom_liste, ty.nom as nom_type, ty.code_couleur " +
                     "FROM tache t " +
                     "JOIN liste l ON t.ref_liste = l.id_liste " +
                     "JOIN type ty ON t.ref_type = ty.id_type " +
                     "JOIN utilisateur_liste ul ON l.id_liste = ul.ref_liste " +
                     "WHERE ul.ref_utilisateur = ?";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idUtilisateur);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Tache tache = new Tache(
                        rs.getInt("id_tache"),
                        rs.getString("nom"),
                        rs.getInt("etat"),
                        rs.getInt("ref_liste"),
                        rs.getInt("ref_type")
                );
                tache.setNomListe(rs.getString("nom_liste"));
                tache.setNomType(rs.getString("nom_type"));
                tache.setCodeCouleur(rs.getString("code_couleur"));
                taches.add(tache);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des tâches : " + e.getMessage());
        }
        return taches;
    }

    /**
     * Récupère une tâche par son ID
     * @param idTache L'ID de la tâche
     * @return La tâche ou null si non trouvée
     */
    public Tache getTacheParId(int idTache) {
        String sql = "SELECT t.*, l.nom as nom_liste, ty.nom as nom_type, ty.code_couleur " +
                     "FROM tache t " +
                     "JOIN liste l ON t.ref_liste = l.id_liste " +
                     "JOIN type ty ON t.ref_type = ty.id_type " +
                     "WHERE t.id_tache = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idTache);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Tache tache = new Tache(
                        rs.getInt("id_tache"),
                        rs.getString("nom"),
                        rs.getInt("etat"),
                        rs.getInt("ref_liste"),
                        rs.getInt("ref_type")
                );
                tache.setNomListe(rs.getString("nom_liste"));
                tache.setNomType(rs.getString("nom_type"));
                tache.setCodeCouleur(rs.getString("code_couleur"));
                return tache;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la tâche : " + e.getMessage());
        }
        return null;
    }

    /**
     * Met à jour une tâche existante
     * @param tache La tâche à mettre à jour
     * @return true si la mise à jour a réussi, false sinon
     */
    public boolean mettreAJourTache(Tache tache) {
        String sql = "UPDATE tache SET nom = ?, etat = ?, ref_liste = ?, ref_type = ? WHERE id_tache = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, tache.getNom());
            stmt.setInt(2, tache.getEtat());
            stmt.setInt(3, tache.getRefListe());
            stmt.setInt(4, tache.getRefType());
            stmt.setInt(5, tache.getIdTache());
            
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la tâche : " + e.getMessage());
            return false;
        }
    }

    /**
     * Supprime une tâche
     * @param idTache L'ID de la tâche à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    public boolean supprimerTache(int idTache) {
        String sql = "DELETE FROM tache WHERE id_tache = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idTache);
            
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la tâche : " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Recherche des tâches par mot-clé pour un utilisateur spécifique
     * @param idUtilisateur L'ID de l'utilisateur
     * @param motCle Le mot-clé à rechercher
     * @return Une liste de tâches correspondant à la recherche
     */
    public ArrayList<Tache> rechercherTaches(int idUtilisateur, String motCle) {
        ArrayList<Tache> taches = new ArrayList<>();
        String sql = "SELECT t.*, l.nom as nom_liste, ty.nom as nom_type, ty.code_couleur " +
                     "FROM tache t " +
                     "JOIN liste l ON t.ref_liste = l.id_liste " +
                     "JOIN type ty ON t.ref_type = ty.id_type " +
                     "JOIN utilisateur_liste ul ON l.id_liste = ul.ref_liste " +
                     "WHERE ul.ref_utilisateur = ? AND t.nom LIKE ?";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idUtilisateur);
            stmt.setString(2, "%" + motCle + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Tache tache = new Tache(
                        rs.getInt("id_tache"),
                        rs.getString("nom"),
                        rs.getInt("etat"),
                        rs.getInt("ref_liste"),
                        rs.getInt("ref_type")
                );
                tache.setNomListe(rs.getString("nom_liste"));
                tache.setNomType(rs.getString("nom_type"));
                tache.setCodeCouleur(rs.getString("code_couleur"));
                taches.add(tache);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche des tâches : " + e.getMessage());
        }
        return taches;
    }
}