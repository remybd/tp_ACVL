package ElementsDiagramme;

import Vues.ObservateurVue;

/**
 *  TODO : Surveiller la bonne construction de l'étiquette ?
 * @author Thibaut
 *
 */
public class TransitionIntermediaire extends Transition {
	private String _etiquette;
	private EtatIntermediaire _source;
	private EtatIntermediaire _dest;
	
	public TransitionIntermediaire(ObservateurVue obs, String etiquette, EtatIntermediaire etatSource, EtatIntermediaire etatDest){
		super(obs);
		this.setEtiquette(etiquette);
		this.setSource(etatSource);
		this.setDestination(etatDest);
	}
	
	public String getEtiquette(){
		return _etiquette;
	}
	
	public void setEtiquette(String etiquette){
		_etiquette = etiquette;
	}

	public EtatIntermediaire getSource() {
		return _source;
	}

	public void setSource(EtatIntermediaire _source) {
		this._source = _source;
	}

	public EtatIntermediaire getDestination() {
		return _dest;
	}

	public void setDestination(EtatIntermediaire _dest) {
		this._dest = _dest;
	}
	
	/**
	 * Retourne la garde indiquée dans l'étiquette
	 * @return
	 */
	public String getGarde(){
		int premCrochet = _etiquette.indexOf('[');
		if(premCrochet<0) //pas de crochet => pas de garde
			return "";
		
		int secCrochet = _etiquette.indexOf(']', premCrochet);
		if(secCrochet<0) //pas de fermeture de garde => on considère que tout le reste de la chaîne est la garde TODO : Avertir l'utilisateur ?
			secCrochet = _etiquette.length();
		
		return _etiquette.substring(premCrochet, secCrochet);
	}
	
	/**
	 * Retourne l'événement indiqué dans l'étiquette
	 * L'événement se trouve avant le premier '[', à défaut avant le premier '/', sinon ce n'est rien
	 * @return
	 */
	public String getEvt(){
		int delimiteur = _etiquette.indexOf('[');
		if(delimiteur<0){
			delimiteur = _etiquette.indexOf('/');
			
			if(delimiteur<0)
				return "";
		}
		
		return _etiquette.substring(0, delimiteur);
	}
	
	/**
	 * Retourne l'action indiquée dans l'étiquette
	 * L'action se trouve après le slash, c'est toute l'étiquette s'il n'y en a pas
	 * @return
	 */
	public String getAction(){
		int slash = _etiquette.indexOf('/');
		if(slash<0)
			return _etiquette;
		
		return _etiquette.substring(slash, _etiquette.length());
	}


	@Override
	public void supprimer() {

	}

	@Override
	public boolean isEtatIntermediaire() {
		return false;
	}

	@Override
	public boolean isTransition() {
		return true;
	}
}
