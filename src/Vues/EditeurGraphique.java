package Vues;

import Controleurs.ControleurDiagramme;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jerem on 11/10/2015.
 */

public class EditeurGraphique extends JFrame implements ObservateurVue {

    private ZoneErreur zone_erreur;
    private JPanel content = new JPanel();

    final private static EditeurGraphique instanceUnique = new EditeurGraphique();

    Toolkit tool = getToolkit();

    private mxGraph graph = new mxGraph();
    private Ihm ihm = Ihm.instance();

    //-------------------------------------------
    //  Partie Barre de menu
    private JMenuBar menu = new JMenuBar();

    private JMenu fichier = new JMenu("Fichier");
    private JMenu aide = new JMenu("Aide");

    private JMenuItem ouvrir = new JMenuItem("Ouvrir");
    private JMenuItem enregistrer = new JMenuItem("Enregistrer");
    private JMenuItem fermer = new JMenuItem("Fermer");

    private JMenuItem consulter_manuel = new JMenuItem("Consulter Manuel");
    private JMenuItem a_propos = new JMenuItem("A Propos");

    private JTextField filename = new JTextField(), dir = new JTextField();
	//-------------------------------------

	private EditeurGraphique() {		
		this.setTitle("Editeur Graphique");
		this.setSize(tool.getScreenSize());
		
		// Ajout des sous-items aux items de la barre de menu
        ouvrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser c = new JFileChooser();
                int rVal = c.showSaveDialog(EditeurGraphique.this);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    filename.setText(c.getSelectedFile().getName());
                    dir.setText(c.getCurrentDirectory().toString());
                }
                if (rVal == JFileChooser.CANCEL_OPTION) {
                    filename.setText("");
                    dir.setText("");
                }
            }
        });
        enregistrer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser c = new JFileChooser();
                // Demonstrate "Save" dialog:
                int rVal = c.showSaveDialog(EditeurGraphique.this);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    filename.setText(c.getSelectedFile().getName());
                    dir.setText(c.getCurrentDirectory().toString());
                }
                if (rVal == JFileChooser.CANCEL_OPTION) {
                    filename.setText("You pressed cancel");
                    dir.setText("");
                }
            }
        });
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

        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        graph.getModel().beginUpdate();
        try {
            Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80, 30);
            Object v2 = graph.insertVertex(parent, null, "World!", 240, 150, 80, 30);
            graph.insertEdge(parent, null, "Edge", v1, v2);
        } finally {
            graph.getModel().endUpdate();
        }

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        content.add(graphComponent);
        content.addMouseListener(new MenuContextuelListener());

        this.setContentPane(content);

        this.setVisible(true);

	}

    public ControleurDiagramme getControleur(){
        return ihm.getControleur();
    }

	@Override
	public void miseAJour() {
		// TODO Auto-generated method stub
		
	}

    public static EditeurGraphique instance() {
        return instanceUnique;
    }

}
