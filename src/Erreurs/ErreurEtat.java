package Erreurs;

import Vues.ObservateurVue;
import ElementsDiagramme.Etat;
import ElementsDiagramme.Sujet;

/**
 * Created by rémy on 05/11/2015.
 */
public class ErreurEtat extends Erreur{
    private Etat etatParent;

    public ErreurEtat(String err, Etat etat, int importance, ObservateurVue zoneErreur){
        super(err,importance, zoneErreur);
        System.out.println("Details : etat problematique : "+etat.getNom());
        
        this.etatParent = etat;
    }

    public Etat getEtatParent() {
        return etatParent;
    }

	@Override
	public String getMessage() {
    	return this.getNom()+" - "+getEtatParent().getNom();
	}
}
