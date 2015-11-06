package Vues;

import ElementsDiagramme.Etat;
import ElementsDiagramme.Transition;

/**
 * Created by r�my on 06/11/2015.
 */
public class Ihm {
	final private static Ihm instanceUnique = new Ihm();
	private EditeurGraphique edGraphique = EditeurGraphique.instance();


	private Ihm() {	}
	
	static public Ihm instance() {
		return instanceUnique;
	}

	public EditeurGraphique getEdGraphique() {
		return this.edGraphique;
	}
	
    public TransitionGraph createTransitionGraph(Transition t){

        return null;
    }

    public EtatGraph createEtatGraph(Etat e){

        return null;
    }
}
