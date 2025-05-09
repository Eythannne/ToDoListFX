package repository;

import database.Database;
import model.Liste;
import model.Utilisateur;

import java.sql.*;
import java.util.ArrayList;

public class ListeRepository {
    private Connection connection;

    public ListeRepository() {
        connection = Database.getConnexion();
    }

    /**
     * Ajoute une nouvelle liste
     * @param liste La liste à ajouter
     * @return L'ID de la liste créée, ou -1 en cas d'erreur
     */
    public int ajouterListe(Liste liste) {
        String sql = "INSERT INTO liste (nom) VALUES (?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, liste.getNom());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la liste : " + e.getMessage());
        }
        return -1;
    }

    /**
     * Associe une liste à un utilisateur
     * @param idListe L'ID de la liste
     * @param idUtilisateur L'ID de l'utilisateur
     * @return true si l'association a réussi, false sinon
     */
    public boolean associerListeUtilisateur(int idListe, int idUtilisateur) {
        String sql = "INSERT INTO utilisateur_liste (ref_utilisateur, ref_liste) VALUES (?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idUtilisateur);
            stmt.setInt(2, idListe);
            
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'association liste-utilisateur : " + e.getMessage());
            return false;
        }
    }

    /**
     * Récupère toutes les listes d'un utilisateur
     * @param idUtilisateur L'ID de l'utilisateur
     * @return Une liste de listes
     */
    public ArrayList<Liste> getListesParUtilisateur(int idUtilisateur) {
        ArrayList<Liste> listes = new ArrayList<>();
        String sql = "SELECT l.* FROM liste l " +
                     "JOIN utilisateur_liste ul ON l.id_liste = ul.ref_liste " +
                     "WHERE ul.ref_utilisateur = ?";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idUtilisateur);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Liste liste = new Liste(rs.getInt("id_liste"));
                liste.setNom(rs.getString("nom"));
                listes.add(liste);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des listes : " + e.getMessage());
        }
        return listes;
    }

    /**
     * Récupère une liste par son ID
     * @param idListe L'ID de la liste
     * @return La liste ou null si non trouvée
     */
    public Liste getListeParId(int idListe) {
        String sql = "SELECT * FROM liste WHERE id_liste = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idListe);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Liste liste = new Liste(rs.getInt("id_liste"));
                liste.setNom(rs.getString("nom"));
                return liste;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la liste : " + e.getMessage());
        }
        return null;
    }

    /**
     * Met à jour une liste existante
     * @param liste La liste à mettre à jour
     * @return true si la mise à jour a réussi, false sinon
     */
    public boolean mettreAJourListe(Liste liste) {
        String sql = "UPDATE liste SET nom = ? WHERE id_liste = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, liste.getNom());
            stmt.setInt(2, liste.getIdListe());
            
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour de la liste : " + e.getMessage());
            return false;
        }
    }

    /**
     * Supprime une liste
     * @param idListe L'ID de la liste à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    public boolean supprimerListe(int idListe) {
        // D'abord supprimer les associations avec les utilisateurs
        String sqlAssoc = "DELETE FROM utilisateur_liste WHERE ref_liste = ?";
        try {
            PreparedStatement stmtAssoc = connection.prepareStatement(sqlAssoc);
            stmtAssoc.setInt(1, idListe);
            stmtAssoc.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression des associations : " + e.getMessage());
            return false;
        }
        
        // Ensuite supprimer la liste
        String sql = "DELETE FROM liste WHERE id_liste = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idListe);
            
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la liste : " + e.getMessage());
            return false;
        }
    }
}