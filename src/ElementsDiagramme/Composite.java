package ElementsDiagramme;

import Vues.Observateur;

/**
 *  TODO
 * @author Thibaut
 *
 */
public class Composite extends EtatIntermediaire {
	private Conteneur _fils;

	public Composite(String nom, Conteneur fils){
		super(nom);
		this.setFils(fils);
	}
	
	public Conteneur getFils() {
		return _fils;
	}

	public void setFils(Conteneur _fils) {
		this._fils = _fils;
	}

	@Override
	public void attache(Observateur observateur) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void detache(Observateur observateur) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void informe() {
		// TODO Auto-generated method stub
		
	}	
}
