package Vues;

import ElementsDiagramme.EnumEtat;

import com.mxgraph.model.mxCell;

import javax.swing.*;


/**
 * Created by Jerem on 03/11/2015.
 */
public class EtatGraph extends ElementGraphique {

    private EnumEtat type;

    public EtatGraph(EtatGraph parent, mxCell objet_graphique, EnumEtat type){
        super(parent, objet_graphique);
        this.type = type;
        if(this.type.equals(EnumEtat.INIT))
            super.supprimable = false;
        else
            super.supprimable = true;
    }

    public EnumEtat getType() {
        return type;
    }

    public void setType(EnumEtat type) {
        this.type = type;
    }

	@Override
	public void miseAJour() {
        try {
            EditeurGraphique.instance().getGraphComponent().getGraph().getModel().beginUpdate();
            this.getObjet_graphique().setValue(Ihm.instance().getControleur().getNom(this));
            EditeurGraphique.instance().getGraph().refresh();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            EditeurGraphique.instance().getGraphComponent().getGraph().getModel().endUpdate();
        }
    }
}
