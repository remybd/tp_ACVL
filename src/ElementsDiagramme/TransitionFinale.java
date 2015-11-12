package ElementsDiagramme;

import java.util.ArrayList;

/**
 *  TODO
 * @author Thibaut
 *
 */
public class TransitionFinale extends Transition {
	private String _etiquette;
	private PseudoFinal _etatFinal;
	private EtatIntermediaire _etatSource;
	
	public TransitionFinale(Conteneur parent, String etiquette, EtatIntermediaire s, PseudoFinal d){
		super(parent);
		this.setEtiquette(etiquette);
		_etatFinal = d;
		_etatSource = s;
	}
	

	/**
	 * Converti la TransitionIntermediaire spécifiée en TransitionFinale
	 * @param trans
	 * @param pseudoFinal
	 */
	public TransitionFinale(TransitionIntermediaire trans, PseudoFinal pseudoFinal) {
		super(trans.getConteneurParent());
		this.attache(trans.getObservateur());
		
		this.setEtiquette(trans.getEtiquette());
		this.setEtatSource((EtatIntermediaire)trans.getEtatSource());
		this.setPseudoFinal(pseudoFinal);
	}

	/**
	 * Converti la TransitionInitiale spécifiée en TransitionFinale ; l'état destination est perdu
	 * @param trans
	 * @param pseudoFinal
	 */
	public TransitionFinale(TransitionInitiale trans, PseudoFinal pseudoFinal) {
		super(trans.getConteneurParent());
		this.attache(trans.getObservateur());
		
		this.setEtiquette("");
		this.setPseudoFinal(pseudoFinal);
	}

	@Override
	public String getEtiquette(){
		return _etiquette;
	}

	/**
	 * Retourne la garde indiquée dans l'étiquette
	 * @return
	 */
	@Override
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
	@Override
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
	@Override
	public String getAction(){
		int slash = _etiquette.indexOf('/');
		if(slash<0)
			return _etiquette;

		return _etiquette.substring(slash, _etiquette.length());
	}

	public void setEtiquette(String etiquette){
		_etiquette = etiquette;
		informe();
	}
	
	public PseudoFinal getPseudoFinal(){
		return this._etatFinal;
	}
	
	public void setPseudoFinal(PseudoFinal etatFinal){
		this._etatFinal = etatFinal;
		informe();
	}

	@Override
	public ArrayList<Element> supprimer() {
		ArrayList<Element> elmtsSupr = new ArrayList<Element>();
		elmtsSupr.add(this);
		
		if(_etatFinal == null)
			return elmtsSupr;
		
		_etatFinal.unLinkTransition(this);
		this.getConteneurParent().supprimerElmt(this);
		return elmtsSupr;
	}


	@Override
	public boolean isTransitionFinale() {
		return true;
	}

	@Override
	public boolean isTransitionInitiale() {
		return false;
	}

	@Override
	public boolean isTransitionIntermediaire() {
		return false;
	}

	@Override
	public Etat getEtatSource() {
		return _etatSource;
	}

	@Override
	public Etat getEtatDestination() {
		return _etatFinal;
	}

	public void setEtatSource(EtatIntermediaire etat) {
		this._etatSource = etat;
		informe();
	}

	public void setEtatDest(PseudoFinal etat) {
		this._etatFinal = etat;
		informe();
	}

	public PseudoFinal getEtatDest() {
		return _etatFinal;
	}
}
