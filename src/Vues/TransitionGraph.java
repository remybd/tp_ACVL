package Vues;

import ElementsDiagramme.EnumEtat;
import ElementsDiagramme.EnumTransition;
import com.mxgraph.model.mxCell;

import java.util.ArrayList;

/**
 * Created by Jerem on 03/11/2015.
 */
public class TransitionGraph extends ElementGraphique {

    private EnumTransition type;

    public TransitionGraph(EtatGraph parent, mxCell objet_graphique, EnumTransition type) {
        super(parent, objet_graphique);
        this.type = type;
        this.supprimable = true;
    }

    public EnumTransition getType() {
        return type;
    }

    public void setType(EnumTransition type) {
        this.type = type;
    }

    public String getSourceTransition(){
        return (String)this.getObjet_graphique().getSource().getValue();
    }

    public String getDestinationTransition(){
        return (String)this.getObjet_graphique().getTarget().getValue();
    }

    @Override
    public void miseAJour() {
        try {
            ArrayList<EtatGraph> etats_source_dest = Ihm.instance().getControleur().getSourceAndDestination(this);
            EditeurGraphique.instance().getGraph().getModel().beginUpdate();
            this.getObjet_graphique().setSource(etats_source_dest.get(0).getObjet_graphique());
            this.getObjet_graphique().setTarget(etats_source_dest.get(1).getObjet_graphique());
            this.getObjet_graphique().setValue(Ihm.instance().getControleur().getEtiquette(this));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            EditeurGraphique.instance().getGraph().getModel().endUpdate();
            EditeurGraphique.instance().setVisible(true);
        }
        //this.getObjet_graphique().setValue();
    }
}
