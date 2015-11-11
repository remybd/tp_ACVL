package Vues;

import javax.swing.*;

/**
 * Created by Jerem on 06/11/2015.
 */
public class ZoneErreur extends JPanel {

    private static ZoneErreur instanceUnique = new ZoneErreur();

    private ZoneErreur(){}

    static public ZoneErreur instance() {
        return instanceUnique;
    }

}
