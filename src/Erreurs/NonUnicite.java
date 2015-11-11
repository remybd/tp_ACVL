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
        this.etatMemeNom = etatMemeNom;
    }

    public Etat getEtatMemeNom() {
        return etatMemeNom;
    }
}
