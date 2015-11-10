package ElementsDiagramme;

/**
 * Created by rémy on 04/11/2015.
 */
public interface Element {
	public void supprimer(); //suppression de l'élément this
    boolean isEtatIntermediaire();
    boolean isTransition();
    boolean isEtatComposite();
    boolean isEtatSimple();
    boolean isEtatPseudoInitial();
    boolean isEtatPseudoFinal();
}
