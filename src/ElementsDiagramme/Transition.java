package ElementsDiagramme;

import Vues.ObservateurVue;

/**
 *  TODO
 * @author Thibaut
 *
 */
public abstract class Transition extends Sujet implements Element {

	public Transition(ObservateurVue obs){
		super(obs);
	}
}
