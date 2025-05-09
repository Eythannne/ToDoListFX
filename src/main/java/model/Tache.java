package model;

public class Tache {
    private int idTache;
    private String nom;
    private int etat; // 0 = à faire, 1 = en cours, 2 = terminée
    private int refListe;
    private int refType;
    
    // Propriétés supplémentaires pour l'affichage
    private String nomListe;
    private String nomType;
    private String codeCouleur;

    public Tache(int idTache, String nom, int etat, int refListe, int refType) {
        this.idTache = idTache;
        this.nom = nom;
        this.etat = etat;
        this.refListe = refListe;
        this.refType = refType;
    }

    public Tache(String nom, int etat, int refListe, int refType) {
        this.nom = nom;
        this.etat = etat;
        this.refListe = refListe;
        this.refType = refType;
    }

    public int getIdTache() {
        return idTache;
    }

    public void setIdTache(int idTache) {
        this.idTache = idTache;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public int getRefListe() {
        return refListe;
    }

    public void setRefListe(int refListe) {
        this.refListe = refListe;
    }

    public int getRefType() {
        return refType;
    }

    public void setRefType(int refType) {
        this.refType = refType;
    }
    
    public String getNomListe() {
        return nomListe;
    }

    public void setNomListe(String nomListe) {
        this.nomListe = nomListe;
    }

    public String getNomType() {
        return nomType;
    }

    public void setNomType(String nomType) {
        this.nomType = nomType;
    }

    public String getCodeCouleur() {
        return codeCouleur;
    }

    public void setCodeCouleur(String codeCouleur) {
        this.codeCouleur = codeCouleur;
    }
    
    public String getEtatString() {
        switch (etat) {
            case 0:
                return "À faire";
            case 1:
                return "En cours";
            case 2:
                return "Terminée";
            default:
                return "Inconnu";
        }
    }

    @Override
    public String toString() {
        return nom;
    }
}