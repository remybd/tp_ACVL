package ElementsDiagramme;

import Vues.Observateur;

/**
 *  TODO
 * @author Thibaut
 *
 */
public abstract class Transition extends Sujet implements Element {

	public Transition(Observateur obs){
		super(obs);
	}
}
