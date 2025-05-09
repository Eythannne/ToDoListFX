package model;

public class Type {
    private int idType;
    private String nom;
    private String codeCouleur;

    public Type(int idType, String nom, String codeCouleur) {
        this.idType = idType;
        this.nom = nom;
        this.codeCouleur = codeCouleur;
    }

    public Type(String nom, String codeCouleur) {
        this.nom = nom;
        this.codeCouleur = codeCouleur;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCodeCouleur() {
        return codeCouleur;
    }

    public void setCodeCouleur(String codeCouleur) {
        this.codeCouleur = codeCouleur;
    }

    @Override
    public String toString() {
        return nom;
    }
}