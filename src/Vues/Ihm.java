package Vues;

import Controleurs.ControleurDiagramme;
import ElementsDiagramme.*;
import com.mxgraph.model.mxCell;

import javax.naming.ldap.Control;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by r�my on 06/11/2015.
 */
public class Ihm {
	final private static Ihm instanceUnique = new Ihm();
	private EditeurGraphique edGraphique = EditeurGraphique.instance();
    private ControleurDiagramme controleur;

	private Ihm() {	}
	
	static public Ihm instance() {
		return instanceUnique;
	}

	public EditeurGraphique getEdGraphique() {
		return this.edGraphique;
	}

    public ControleurDiagramme getControleur(){
        return controleur;
    }

    public void setControleur(ControleurDiagramme controleur){
        this.controleur = controleur;
    }
	
    public TransitionGraph createTransitionGraph(EtatGraph s, EtatGraph d, Transition t){
        TransitionGraph res = null;
        if(t.isTransitionInitiale()){
            res = edGraphique.ajouterTransition(s.getParent(), s, d, "", EnumTransition.INIT);
        } else if (t.isTransitionIntermediaire()){
            res = edGraphique.ajouterTransition(s.getParent(), s, d, ((TransitionIntermediaire) t).getEtiquette(), EnumTransition.INTER);
        } else {
            res = edGraphique.ajouterTransition(s.getParent(), s, d, ((TransitionFinale) t).getEtiquette(), EnumTransition.FINAL);
        }
        return res;
    }

    public EtatGraph createEtatGraph(EtatGraph parent, Etat e) {
    	EtatGraph res = null;
        if(e.isEtatSimple()){
        	res = edGraphique.ajouterEtatSimple(parent, e.getNom(), EnumEtat.SIMPLE);
        } else if(e.isEtatPseudoInitial()){
           	res = edGraphique.ajouterEtatPseudoInitial(parent, e.getNom(),EnumEtat.INIT);
        } else if (e.isEtatPseudoFinal()){
           	res = edGraphique.ajouterEtatPseudoFinal(parent, e.getNom(), EnumEtat.FINAL);
        } else {
           	res = edGraphique.ajouterEtatComposite(parent, e.getNom(), EnumEtat.COMPOSITE);
        }
        return res;
    }

    public void removeElem(mxCell m) {
    	System.out.println(m);
        ElementGraphique eg = this.getEdGraphique().getElement_from_liste(m);

        if (eg.isSupprimable()){
            HashMap<mxCell, ElementGraphique> liste_elements_graphiques = EditeurGraphique.instance().getListe_elements_graphiques();
       
            //A MODIFIER
            for (Map.Entry<mxCell, ElementGraphique> entry : liste_elements_graphiques.entrySet()) {
            	if(entry.getKey().isEdge() && ( ((TransitionGraph)entry.getValue()).getDestinationTransition().equals(eg) || ((TransitionGraph)entry.getValue()).getSourceTransition().equals(eg)) ) {
            		liste_elements_graphiques.remove(entry.getKey());
            	}
                System.out.println(entry.getKey() + " " + entry.getValue().getNom());
            }
            
            removeElemFromListeEditeurGraphique(m);
            removeElemFromGraph(m);


        } else {
            JOptionPane message_erreur = new JOptionPane();
            message_erreur.showMessageDialog(null, "On ne peut pas supprimé un etat initial", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void removeElemFromListeEditeurGraphique(mxCell m){
        HashMap<mxCell, ElementGraphique> liste_elements_graphiques = EditeurGraphique.instance().getListe_elements_graphiques();
        Object[] tabCells = {(Object)m};

        System.out.println("Element supprimé :"  + liste_elements_graphiques.get(m).getNom());

        if (m.getChildCount() > 0)
        for(int i=0; i<m.getChildCount(); i++){
            removeElemFromListeEditeurGraphique((mxCell) m.getChildAt(i));
        }

        liste_elements_graphiques.remove(m);
    }

    public void removeElemFromGraph(mxCell m){
        Object[] tabCells = {(Object)m};
        EditeurGraphique.instance().getGraph().removeCells(tabCells);
    }

}
