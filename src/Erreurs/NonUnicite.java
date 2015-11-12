package Erreurs;

import ElementsDiagramme.Etat;
import Vues.ObservateurVue;

/**
 * Created by r�my on 05/11/2015.
 */
public class NonUnicite extends ErreurEtat{
    private Etat etatMemeNom;

    public NonUnicite(Etat etat, Etat etatMemeNom, int importance, ObservateurVue zoneErreur){
        super("Etat Non Unique",etat, importance, zoneErreur);
        System.out.println("Details : etat problematique : "+etat.getNom());
        this.etatMemeNom = etatMemeNom;
    }

    public Etat getEtatMemeNom() {
        return etatMemeNom;
    }

	@Override
	public String getMessage() {
    	return this.getNom()+" - L'etat suivant porte le meme nom qu'un autre : "+getEtatParent().getNom();
	}
}
