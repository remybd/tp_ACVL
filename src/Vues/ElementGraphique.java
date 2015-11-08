package Vues;

import com.mxgraph.model.mxCell;

import Controleurs.ControleurDiagramme;

/**
 * Created by r�my on 06/11/2015.
 */
public abstract class ElementGraphique extends mxCell {
	ElementGraphique parent = null;
	
	public ElementGraphique(ElementGraphique parent) {
		
	}
}
