package model;

public class Liste {

    public Liste(int idListe) {
        this.idListe = idListe;
    }

    private int idListe;

    public int getIdListe() {
        return idListe;
    }

    public void setIdListe(int idListe) {
        this.idListe = idListe;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Liste(String nom) {
        this.nom = nom;
    }

    private String nom;
}


