package Vues;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;


/**
 * Created by Jerem on 11/10/2015.
 */
public class EditeurGraphique extends JFrame {

    Toolkit tool = getToolkit();

    public final EditeurGraphique editeur_graphique = this;

    //  Partie Barre de menu
    private JMenuBar menu = new JMenuBar();

    private JMenu fichier = new JMenu("Fichier");
    private JMenu aide = new JMenu("Aide");

    private JMenuItem ouvrir = new JMenuItem("Ouvrir");
    private JMenuItem enregistrer = new JMenuItem("Enregistrer");
    private JMenuItem fermer = new JMenuItem("Fermer");

    private JMenuItem consulter_manuel = new JMenuItem("Consulter Manuel");
    private JMenuItem a_propos = new JMenuItem("A Propos");

    public EditeurGraphique(){
        this.setTitle("Editeur Graphique");
        this.setVisible(true);
        this.setSize(tool.getScreenSize());

        // Ajout des sous-items aux items de la barre de menu
        fichier.add(ouvrir);
        fichier.add(enregistrer);
        fichier.addSeparator();
        fermer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fichier.add(fermer);

        aide.add(consulter_manuel);
        aide.add(a_propos);

        menu.add(fichier);
        menu.add(aide);

        this.setJMenuBar(menu);

        this.addMouseListener(new MenuContextuelListener());

    }

}
