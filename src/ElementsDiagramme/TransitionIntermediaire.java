package ElementsDiagramme;

import java.util.ArrayList;

import Vues.ObservateurVue;

/**
 *  TODO : Surveiller la bonne construction de l'�tiquette ?
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
	
	/**
	 * Converti le TransitionInitiale sp�cifi� en TransitionIntermediaire
	 * @param transInit
	 * @param EtatIntermediaire : la source
	 */
	public TransitionIntermediaire(TransitionInitiale transInit, EtatIntermediaire etatSource) {
		super(transInit.getConteneurParent());
		this.setObservateur(transInit.getObservateur());
		
		this.setEtiquette("");
		this.setEtatSource(etatSource);
		this.setEtatDest(transInit.getEtatDest());
	}

	/**
	 * Converti le TransitionFinale sp�cifi� en TransitionIntermediaire
	 * @param trans
	 * @param etatIntermediaire : la destination
	 */
	public TransitionIntermediaire(TransitionFinale trans, EtatIntermediaire etatIntermediaire) {
		super(trans.getConteneurParent());
		this.setObservateur(trans.getObservateur());
		
		this.setEtiquette(trans.getEtiquette());
		this.setEtatSource((EtatIntermediaire)trans.getEtatSource());
		this.setEtatDest(etatIntermediaire);
	}

	@Override
	public String getEtiquette(){
		return _etiquette;
	}
	
	public void setEtiquette(String etiquette){
		_etiquette = etiquette;
	}


	
	/**
	 * Retourne la garde indiqu�e dans l'�tiquette
	 * @return
	 */
	public String getGarde(){
		int premCrochet = _etiquette.indexOf('[');
		if(premCrochet<0) //pas de crochet => pas de garde
			return "";
		
		int secCrochet = _etiquette.indexOf(']', premCrochet);
		if(secCrochet<0) //pas de fermeture de garde => on consid�re que tout le reste de la cha�ne est la garde TODO : Avertir l'utilisateur ?
			secCrochet = _etiquette.length();
		
		return _etiquette.substring(premCrochet, secCrochet);
	}
	
	/**
	 * Retourne l'�v�nement indiqu� dans l'�tiquette
	 * L'�v�nement se trouve avant le premier '[', � d�faut avant le premier '/', sinon ce n'est rien
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
	 * Retourne l'action indiqu�e dans l'�tiquette
	 * L'action se trouve apr�s le slash, c'est toute l'�tiquette s'il n'y en a pas
	 * @return
	 */
	public String getAction(){
		int slash = _etiquette.indexOf('/');
		if(slash<0)
			return _etiquette;
		
		return _etiquette.substring(slash, _etiquette.length());
	}

	@Override
	public ArrayList<Element> supprimer() {
		ArrayList<Element> elmtsSupr = new ArrayList<Element>();
		elmtsSupr.add(this);
		
		if(_source != null)
			_source.unLinkDestination(this);
		
		if(_dest != null)
			_dest.unLinkSource(this);
		
		return elmtsSupr;
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

	public void setEtatSource(EtatIntermediaire etat) {
		this._source = etat;
	}

	public void setEtatDest(EtatIntermediaire etat) {
		this._dest = etat;		
	}

	@Override
	public Etat getEtatSource() {
		return _source;
	}

	@Override
	public Etat getEtatDestination() {
		return _dest;
	}

	public EtatIntermediaire getEtatDest() {
		return _dest;
	}
}
