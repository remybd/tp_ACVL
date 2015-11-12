package Vues;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;

/**
 * Created by Jerem on 03/11/2015.
 */
public class MenuContextuelListener extends MouseAdapter {
    private mxGraphComponent GraphComponent;

    public MenuContextuelListener(mxGraphComponent GraphComponent) {
        this.GraphComponent = GraphComponent;
    }

    public void mousePressed(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    public void mouseReleased(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    private void doPop(MouseEvent e){
        mxCell element_selectionne = ((mxCell)GraphComponent.getCellAt(e.getX(), e.getY()));
        MenuContextuel menu = null;
        if(element_selectionne == null)
            menu = new MenuContextuel(EnumObjetSelectionne.AUCUN, null);
        else if(!element_selectionne.isVertex())
            menu = new MenuContextuel(EnumObjetSelectionne.TRANSITION, Ihm.instance().getEdGraphique().getElement_from_liste(element_selectionne));
        else
            menu = new MenuContextuel(EnumObjetSelectionne.ETAT, Ihm.instance().getEdGraphique().getElement_from_liste(element_selectionne));

        menu.show(e.getComponent(), e.getX(), e.getY());
    }

}
