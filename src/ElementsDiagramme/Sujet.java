package ElementsDiagramme;

import Vues.Observateur;

/**
 * Created by rémy on 05/11/2015.
 */
public abstract class Sujet {
	private Observateur _obs;
	
	public Sujet(Observateur obs){
		this.setObservateur(obs);
	}

	public Observateur getObservateur() {
		return _obs;
	}

	public void setObservateur(Observateur _obs) {
		this._obs = _obs;
	}
}
