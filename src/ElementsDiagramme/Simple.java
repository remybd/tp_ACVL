package ElementsDiagramme;

import Vues.ObservateurVue;

/**
 *  TODO
 * @author Thibaut
 *
 */
public class Simple extends EtatIntermediaire{

	public Simple(Conteneur parent, String nom){
		super(parent, nom);
	}

	@Override
	public boolean isEtatComposite() {
		return false;
	}

	@Override
	public boolean isEtatSimple() {
		return true;
	}
	
}
