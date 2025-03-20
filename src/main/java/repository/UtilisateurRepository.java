package repository;

import com.mysql.cj.protocol.Resultset;
import database.Database;
import model.Utilisateur;
import java.sql.*;

public class UtilisateurRepository {
    private Connection connection;

    public UtilisateurRepository() {
        connection = Database.getConnexion();
    }


    public void ajouterUtilisateur(Utilisateur utilisateur) {
        String sql = "INSERT INTO utilisateur (nom, prenom, email, mot_de_passe) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, utilisateur.getNom());
            stmt.setString(2, utilisateur.getPrenom());
            stmt.setString(3, utilisateur.getEmail());
            stmt.setString(4, utilisateur.getMdp());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public Utilisateur getUtilisateurParEmail(String email) {
        String sql = "SELECT * FROM utilisateur WHERE email = ?";
        try {
            PreparedStatement rsql = connection.prepareStatement(sql);
            rsql.setString(1, email);
            ResultSet resultat = rsql.executeQuery();

            if (resultat.next()) {
                return new Utilisateur(
                        resultat.getInt("id_utilisateur"),
                        resultat.getString("nom"),
                        resultat.getString("prenom"),
                        resultat.getString("email"),
                        resultat.getString("mot_de_passe"));
            }
        } catch (SQLException e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        return null;
    }

    public void supprimerUtilisateurParEmail(String email) {
        String sql = "DELETE FROM utilisateur WHERE email = ?";
        try {
            PreparedStatement rsql = connection.prepareStatement(sql);
            rsql.setString(1, email);
        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    public void mettreAJourUtilisateur(Utilisateur utilisateur) {
        String sql = "UPDATE utilisateur SET nom = ?, prenom = ?, mdp = ?, role = ? WHERE email = ?";
        try {
            PreparedStatement rsql = connection.prepareStatement(sql);
            rsql.setString(1, utilisateur.getNom());
            rsql.setString(2, utilisateur.getPrenom());
            rsql.setString(3, utilisateur.getMdp());
            rsql.setString(4, utilisateur.getRole());
            rsql.executeUpdate();
            System.out.println("l'utilisateur à bien été modifié !");
        } catch (SQLException e) {
            System.out.println("Erreur, les modifications n'ont pas été faites : " + e.getMessage());
        }
    }
}
