package Erreurs;

import ElementsDiagramme.Sujet;

/**
 * Created by rémy on 05/11/2015.
 */
public abstract class Erreur extends Sujet{
    private String nom;
    private int importance;

    public Erreur(String nom, int importance){
        this.nom = nom;
        this.importance = importance;
    }
    public String getNom() {
        return nom;
    }
    public int getImportance() {
        return importance;
    }
}
