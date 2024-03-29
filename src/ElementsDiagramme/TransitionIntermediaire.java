package ElementsDiagramme;

import java.util.ArrayList;

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
	 * Converti le TransitionInitiale spécifié en TransitionIntermediaire
	 * @param transInit
	 * @param EtatIntermediaire : la source
	 */
	public TransitionIntermediaire(TransitionInitiale transInit, EtatIntermediaire etatSource) {
		super(transInit.getConteneurParent());
		this.attache(transInit.getObservateur());
		
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
		this.attache(trans.getObservateur());
		
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
		informe();
	}

	/**
	 * Retourne la garde indiquée dans l'étiquette
	 * @return
	 */
	@Override
	public String getGarde(){
		return this.getGarde(_etiquette);
	}
	
	/**
	 * Retourne la garde indiquée dans l'étiquette
	 * @return
	 */
	public static String getGarde(String etiquette){
		int premCrochet = etiquette.indexOf('[');
		if(premCrochet<0) //pas de crochet => pas de garde
			return "";
		
		int secCrochet = etiquette.indexOf(']', premCrochet);
		if(secCrochet<0) //pas de fermeture de garde => on consid�re que tout le reste de la cha�ne est la garde TODO : Avertir l'utilisateur ?
			secCrochet = etiquette.length();
		
		return etiquette.substring(premCrochet+1, secCrochet);
	}
	
	/**
	 * Retourne l'évènement indiqué dans l'étiquette
	 * L'évènement se trouve avant le premier '[', à défaut avant le premier '/', sinon ce n'est rien
	 * @return
	 */
	@Override
	public String getEvt(){
		return this.getEvt(_etiquette);
	}
	
	/**
	 * Retourne l'évènement indiqué dans l'étiquette
	 * L'évènement se trouve avant le premier '[', à défaut avant le premier '/', sinon ce n'est rien
	 * @return
	 */
	public static String getEvt(String etiquette){
		int delimiteur = etiquette.indexOf('[');
		if(delimiteur<0){
			delimiteur = etiquette.indexOf('/');
			
			if(delimiteur<0)
				return "";
		}
		
		return etiquette.substring(0, delimiteur);
	}
	
	/**
	 * Retourne l'action indiquée dans l'étiquette
	 * L'action se trouve après le slash, c'est toute l'étiquette s'il n'y en a pas
	 * @return
	 */
	@Override
	public String getAction(){
		return this.getAction(_etiquette);
	}
	
	/**
	 * Retourne l'action indiquée dans l'étiquette
	 * L'action se trouve après le slash, c'est toute l'étiquette s'il n'y en a pas
	 * @return
	 */
	public static String getAction(String etiquette){
		int slash = etiquette.indexOf('/');
		if(slash<0)
			return etiquette;
		
		return etiquette.substring(slash+1, etiquette.length());
	}

	@Override
	public ArrayList<Element> supprimer() {
		ArrayList<Element> elmtsSupr = new ArrayList<Element>();
		elmtsSupr.add(this);
		
		if(_source != null)
			_source.unLinkDestination(this);
		
		if(_dest != null)
			_dest.unLinkSource(this);

		this.getConteneurParent().supprimerElmt(this);
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
		informe();
	}

	public void setEtatDest(EtatIntermediaire etat) {
		this._dest = etat;
		informe();
	}

	@Override
	public Etat getEtatSource() {
		return _source;
	}

	@Override
	public Etat getEtatDestination() {
		return _dest;
	}

}