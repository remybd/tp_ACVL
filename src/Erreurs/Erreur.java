package Erreurs;

import ElementsDiagramme.Sujet;
import Vues.ObservateurVue;

/**
 * Created by r�my on 05/11/2015.
 */
public abstract class Erreur extends Sujet{
	public final static int ERR_UNICITE_ETAT = 1;
	public final static int ERR_ETAT_BLOQUANT = 1;
	public final static int ERR_TRANSITION_NON_DETERM = 1;
	
    private String nom;
    private int importance;

    public Erreur(String nom, int importance, ObservateurVue zoneErreur){
    	System.out.println("Erreur - Erreur detectee : "+nom);
    	
        this.nom = nom;
        this.importance = importance;
        this.attache(zoneErreur);
    }
    public String getNom() {
        return nom;
    }
    public int getImportance() {
        return importance;
    }
    
    public abstract String getMessage();
}
