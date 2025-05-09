package repository;

import com.mysql.cj.protocol.Resultset;
import database.Database;
import model.Liste;
import model.Utilisateur;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                        resultat.getString("mot_de_passe"),
                        resultat.getString("role")
                );

            }
        } catch (SQLException e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        return null;
    }

    public ArrayList<Utilisateur> getAllUtilisateurs() {
        ArrayList<Utilisateur> utilisateurs = new ArrayList<>();
        String sql = "SELECT * FROM utilisateur";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                Utilisateur utilisateur = new Utilisateur(
                        rs.getInt("id_utilisateur"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("mot_de_passe"),
                        rs.getString("role")
                );
                utilisateurs.add(utilisateur);
            }
        } catch (SQLException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
        return utilisateurs;
    }

    public boolean supprimerUtilisateurParEmail(String email) {
        String sql = "DELETE FROM utilisateur WHERE email = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);
            //int rowsDeleted = stmt.executeUpdate();
            stmt.executeUpdate();
            //debut verif
            String verif = "SELECT * FROM utilisateur WHERE email = ?";
            PreparedStatement stmtverif = connection.prepareStatement(verif);
            stmtverif.setString(1, email);
            ResultSet rs = stmtverif.executeQuery();
            if (rs.next()) {
                System.out.println("deso echoé : " + email);
            }
            else{
                System.out.println("Utilisateur supprimé avec succès !");
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de l'utilisateur : " + e.getMessage());
            return false;
        }
    }

    public void mettreAJourUtilisateur(Utilisateur utilisateur) {
        System.out.println("MAJ : "+ utilisateur.getNom());
        String sql = "UPDATE utilisateur SET nom = ?, prenom = ?, email = ? WHERE email = ?";
        try {
            PreparedStatement rsql = connection.prepareStatement(sql);
            rsql.setString(1, utilisateur.getNom());
            rsql.setString(2, utilisateur.getPrenom());
            rsql.setString(3, utilisateur.getEmail());
            rsql.setString(4, utilisateur.getEmail());
            rsql.executeUpdate();
            System.out.println("l'utilisateur à bien été modifié !");

        } catch (SQLException e) {
            System.out.println("Erreur, les modifications n'ont pas été faites : " + e.getMessage());
        }
    }
}
