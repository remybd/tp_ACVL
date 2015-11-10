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
	
	public TransitionIntermediaire(Conteneur parent, String etiquette, EtatIntermediaire etatSource, EtatIntermediaire etatDest){
		super(parent);
		this.setEtiquette(etiquette);
		this.setEtatSource(etatSource);
		this.setEtatDest(etatDest);
	}
	
	public String getEtiquette(){
		return _etiquette;
	}
	
	public void setEtiquette(String etiquette){
		_etiquette = etiquette;
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
		if(_source != null)
			_source.unLinkDestination(this);
		
		if(_dest != null)
			_dest.unLinkSource(this);
	}
	


	@Override
	public boolean isTransitionFinale() {
		return false;
	}

	@Override
	public boolean isTransitionInitiale() {
		return false;
	}

	@Override
	public boolean isTransitionIntermediaire() {
		return true;
	}

	@Override
	public void setEtatSource(Etat etat) {
		this._source = etat;
	}

	@Override
	public void setEtatDest(Etat etat) {
		this._dest = etat;		
	}

	@Override
	public Etat getEtatSource() {
		return _source;
	}

	@Override
	public Etat getEtatDest() {
		return _dest;
	}
}
