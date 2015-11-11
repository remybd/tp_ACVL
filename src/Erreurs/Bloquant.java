package Erreurs;

import Vues.ObservateurVue;
import ElementsDiagramme.Etat;

/**
 * Created by r�my on 05/11/2015.
 */
public abstract class Bloquant extends ErreurEtat{

    public Bloquant(Etat etat, int importance, ObservateurVue zoneErreur){
        super("Etat Bloquant",etat, importance, zoneErreur);
    }

	@Override
	public String getMessage() {
    	return this.getNom()+" - L'�tat simple suivant doit avoir au moins une transition sortante : "+getEtatParent().getNom();
	}
}
