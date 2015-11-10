package Vues;

import Controleurs.ControleurDiagramme;
import ElementsDiagramme.Conteneur;
import ElementsDiagramme.EnumEtat;
import ElementsDiagramme.Etat;
import ElementsDiagramme.Transition;

import javax.naming.ldap.Control;


/**
 * Created by r�my on 06/11/2015.
 */
public class Ihm {
	final private static Ihm instanceUnique = new Ihm();
	private EditeurGraphique edGraphique = EditeurGraphique.instance();
    private ControleurDiagramme controleur;
    private Conteneur conteneur_principal;

	private Ihm() {	}
	
	static public Ihm instance() {
		return instanceUnique;
	}

	public EditeurGraphique getEdGraphique() {
		return this.edGraphique;
	}

    public ControleurDiagramme getControleur(){
        return controleur;
    }

    public void setControleur(ControleurDiagramme controleur){
        this.controleur = controleur;
    }
	
    public TransitionGraph createTransitionGraph(EtatGraph parent, Transition t){
        return null;
    }

    public EtatGraph createEtatGraph(EtatGraph parent, Etat e) {
    	EtatGraph res = null;
        if(e.isEtatSimple()){
        	res = edGraphique.ajouterEtatSimple(parent, e.getNom(), EnumEtat.SIMPLE);
        } else if(e.isEtatPseudoInitial()){
           	res = edGraphique.ajouterEtatPseudoInitial(parent, e.getNom(),EnumEtat.INIT);
        } else if (e.isEtatPseudoFinal()){
           	res = edGraphique.ajouterEtatPseudoFinal(parent, e.getNom(), EnumEtat.FINAL);
        } else {
           	res = edGraphique.ajouterEtatComposite(parent, e.getNom(), EnumEtat.COMPOSITE);
        }
        return res;
    }
}
