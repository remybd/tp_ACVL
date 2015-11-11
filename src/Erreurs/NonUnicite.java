package Erreurs;

import Vues.ObservateurVue;
import ElementsDiagramme.Etat;

/**
 * Created by rémy on 05/11/2015.
 */
public class NonUnicite extends ErreurEtat{
    private Etat etatMemeNom;

    public NonUnicite(Etat etat, Etat etatMemeNom, int importance, ObservateurVue zoneErreur){
        super("Etat Non Unique",etat, importance, zoneErreur);
        System.out.println("Détails : état problématique : "+etat.getNom());
        this.etatMemeNom = etatMemeNom;
    }

    public Etat getEtatMemeNom() {
        return etatMemeNom;
    }

	@Override
	public String getMessage() {
    	return this.getNom()+" - L'état suivant porte le même nom qu'un autre : "+getEtatParent().getNom();
	}
}
