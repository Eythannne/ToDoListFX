package repository;

import database.Database;
import model.Type;

import java.sql.*;
import java.util.ArrayList;

public class TypeRepository {
    private Connection connection;

    public TypeRepository() {
        connection = Database.getConnexion();
    }

    /**
     * Ajoute un nouveau type
     * @param type Le type à ajouter
     * @return L'ID du type créé, ou -1 en cas d'erreur
     */
    public int ajouterType(Type type) {
        String sql = "INSERT INTO type (nom, code_couleur) VALUES (?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, type.getNom());
            stmt.setString(2, type.getCodeCouleur());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du type : " + e.getMessage());
        }
        return -1;
    }

    /**
     * Récupère tous les types
     * @return Une liste de types
     */
    public ArrayList<Type> getAllTypes() {
        ArrayList<Type> types = new ArrayList<>();
        String sql = "SELECT * FROM type";
        
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Type type = new Type(
                        rs.getInt("id_type"),
                        rs.getString("nom"),
                        rs.getString("code_couleur")
                );
                types.add(type);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des types : " + e.getMessage());
        }
        return types;
    }

    /**
     * Récupère un type par son ID
     * @param idType L'ID du type
     * @return Le type ou null si non trouvé
     */
    public Type getTypeParId(int idType) {
        String sql = "SELECT * FROM type WHERE id_type = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idType);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Type(
                        rs.getInt("id_type"),
                        rs.getString("nom"),
                        rs.getString("code_couleur")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du type : " + e.getMessage());
        }
        return null;
    }

    /**
     * Met à jour un type existant
     * @param type Le type à mettre à jour
     * @return true si la mise à jour a réussi, false sinon
     */
    public boolean mettreAJourType(Type type) {
        String sql = "UPDATE type SET nom = ?, code_couleur = ? WHERE id_type = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, type.getNom());
            stmt.setString(2, type.getCodeCouleur());
            stmt.setInt(3, type.getIdType());
            
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du type : " + e.getMessage());
            return false;
        }
    }

    /**
     * Supprime un type
     * @param idType L'ID du type à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    public boolean supprimerType(int idType) {
        String sql = "DELETE FROM type WHERE id_type = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idType);
            
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du type : " + e.getMessage());
            return false;
        }
    }
}