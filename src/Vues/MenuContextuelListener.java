package Vues;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Jerem on 03/11/2015.
 */
public class MenuContextuelListener extends MouseAdapter {

        public void mousePressed(MouseEvent e){
            if (e.isPopupTrigger())
                doPop(e);
        }

        public void mouseReleased(MouseEvent e){
            if (e.isPopupTrigger())
                doPop(e);
        }

        private void doPop(MouseEvent e){
            MenuContextuel menu = new MenuContextuel();
            menu.show(e.getComponent(), e.getX(), e.getY());
        }

}
