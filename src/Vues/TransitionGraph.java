package Vues;

import ElementsDiagramme.EnumEtat;
import ElementsDiagramme.EnumTransition;
import com.mxgraph.model.mxCell;

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
        //Ihm.instance().getControleur();
    }
}
