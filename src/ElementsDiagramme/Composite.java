package ElementsDiagramme;

import Vues.Observateur;

/**
 *  TODO
 * @author Thibaut
 *
 */
public class Composite extends EtatIntermediaire {
	private Conteneur _fils;

	public Composite(Observateur obs, String nom, Conteneur fils){
		super(obs, nom);
		this.setFils(fils);
	}
	
	public Conteneur getFils() {
		return _fils;
	}

	public void setFils(Conteneur _fils) {
		this._fils = _fils;
	}
}
