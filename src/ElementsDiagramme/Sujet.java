package ElementsDiagramme;

import Vues.ObservateurVue;

/**
 * Created by rémy on 05/11/2015.
 */
public abstract class Sujet {
	private ObservateurVue _obs;
	
	public Sujet(ObservateurVue obs){
		this.setObservateur(obs);
	}

	public ObservateurVue getObservateur() {
		return _obs;
	}

	public void setObservateur(ObservateurVue _obs) {
		this._obs = _obs;
	}
}
