package Vues;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        ElementGraphique child =  new ElementGraphique(null, ((mxCell)GraphComponent.getCellAt(e.getX(), e.getY())));
        MenuContextuel menu = null;
        if(child == null)
            menu = new MenuContextuel(EnumObjetSelectionne.AUCUN, child);
        else if(!child.isVertex())
            menu = new MenuContextuel(EnumObjetSelectionne.TRANSITION, child);
        else
            menu = new MenuContextuel(EnumObjetSelectionne.ETAT, child);

        menu.show(e.getComponent(), e.getX(), e.getY());
    }

}
