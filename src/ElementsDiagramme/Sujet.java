package ElementsDiagramme;

import java.io.Serializable;

import Vues.ObservateurVue;

/**
 * Created by rémy on 05/11/2015.
 */
public abstract class Sujet implements Serializable {
	private static final long serialVersionUID = 43L;
	
	private ObservateurVue _obs;
	
	public Sujet(){
	}

	public ObservateurVue getObservateur() {
		return _obs;
	}

	public void attache(ObservateurVue _obs) {
		this._obs = _obs;
	}

	public void detache(ObservateurVue _obs) {this._obs = null;}

	public void informe(){
		if(_obs != null)
			this._obs.miseAJour();
	}
}
