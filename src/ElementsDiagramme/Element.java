package ElementsDiagramme;

/**
 * Created by r�my on 04/11/2015.
 */
public interface Element {
	public void supprimer(); //suppression de l'�l�ment this
    boolean isEtatIntermediaire();
    boolean isTransition();
    boolean isEtatComposite();
    boolean isEtatSimple();
    boolean isEtatPseudoInitial();
    boolean isEtatPseudoFinal();
}
