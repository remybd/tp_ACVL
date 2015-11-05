package ElementsDiagramme;

import java.util.HashSet;

import Erreurs.Erreur;

public class Conteneur {
	private PseudoInitial _etatInit;
	private HashSet<Erreur> _erreurs = new HashSet<Erreur>();
	
	public Conteneur(PseudoInitial etatInit){
		this.setPseudoInital(etatInit);
	}
	
	public PseudoInitial getPseudoInitial() {
		return _etatInit;
	}
	public void setPseudoInital(PseudoInitial _etatInit) {
		this._etatInit = _etatInit;
	}
	public HashSet<Erreur> getErreurs() {
		return _erreurs;
	}
	public void addErreur(Erreur _erreur) {
		if(this._erreurs == null)
			_erreurs = new HashSet<Erreur>();
			
		this._erreurs.add(_erreur);
	}	
}
