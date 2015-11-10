package Vues;

import Controleurs.ControleurDiagramme;
import ElementsDiagramme.Conteneur;
import ElementsDiagramme.EnumEtat;
import ElementsDiagramme.Etat;
import ElementsDiagramme.Transition;


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
	
    public TransitionGraph createTransitionGraph(Transition t){

        return null;
    }

    public EtatGraph createEtatGraph(Etat e){
        if(e.isEtatSimple()){
            edGraphique.ajouterEtatSimple(e.getNom(), EnumEtat.SIMPLE);
        } else if(e.isEtatPseudoInitial()){
            edGraphique.ajouterEtatPseudoInitial(e.getNom(),EnumEtat.INIT);
        } else if (e.isEtatPseudoFinal()){
            edGraphique.ajouterEtatPseudoFinal(e.getNom(), EnumEtat.FINAL);
        } else {
            edGraphique.ajouterEtatComposite(e.getNom(), EnumEtat.COMPOSITE);
        }
        return null;
    }

    public static void main(String[] args){
        Ihm frame = Ihm.instance();
    }
}
