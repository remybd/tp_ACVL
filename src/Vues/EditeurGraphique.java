package Vues;

import Controleurs.ControleurDiagramme;
import ElementsDiagramme.EnumEtat;

import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Hashtable;

/**
 * Created by Jerem on 11/10/2015.
 */

public class EditeurGraphique extends JFrame implements ObservateurVue {

    private final static String etatInitialStyle = mxConstants.STYLE_SHAPE + "="+ mxConstants.SHAPE_ELLIPSE;
    private final static String etatFinalStyle = mxConstants.STYLE_SHAPE + "="+ mxConstants.SHAPE_DOUBLE_ELLIPSE;

    private ZoneErreur zone_erreur;
    private JPanel content = new JPanel();

    final private static EditeurGraphique instanceUnique = new EditeurGraphique();

    Toolkit tool = getToolkit();

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

    //Partie gestionnaire de graphe
    //  private ArrayList<mxCell>
    private mxGraph graph = new mxGraph();
    private mxGraphComponent graphComponent = null;

    private JTextField filename = new JTextField();
    private JTextField dir = new JTextField();

    private EditeurGraphique() {

        this.setTitle("Editeur Graphique");
        this.setSize(tool.getScreenSize());

        createMenu();

        JPanel mainPanel = new JPanel(); //Panel

        FlowLayout bl = new FlowLayout(FlowLayout.CENTER);   //layoutManager
        mainPanel.setLayout(bl);    //attache le layoutManager au panel           

        Object parent = graph.getDefaultParent();
        Object v1 = null;
        mxCell vertex = null;
        graph.getModel().beginUpdate();
        try {

            mxGeometry geometry = new mxGeometry(20, 20, 80, 30);
            geometry.setRelative(false);

            /*vertex = new mxCell("Hello", geometry, "ROUNDED");
            vertex.setId(null);
            vertex.setVertex(true);
            vertex.setConnectable(true);
            graph.addCell(vertex, parent);
            System.out.println(vertex.isVertex());*/

            //v1 = (Object) vertex;
            graph.insertVertex(parent, null, "Initial", 20, 20, 30, 30, etatInitialStyle);

            /*Object v2 = graph.insertVertex(parent, null, "Final!", 240, 150, 30, 30, etatFinalStyle);
            graph.insertEdge(parent, null, "Edge", v1, v2);*/

        } finally {
            graph.getModel().endUpdate();
        }
        graphComponent = new mxGraphComponent(graph);
        // graph.getModel().get
        //  graphComponent.getCellAt(MouseEvent.gCursor.getDefaultCursor().g, arg1)
        graphComponent.getGraphControl().addMouseListener(new MenuContextuelListener(graphComponent));
        //     graphComponent.getComponentAt(System, arg1)
        //   graphComponent.getComponents()[0]..addMouseListener(new MenuContextuelListener(graphComponent.getComponent(0)));
        ///       graphComponent.getGraphControl().getComponentListeners()[0].
        //     graphComponent.

        mxStylesheet stylesheet = graph.getStylesheet();
        Hashtable<String, Object> style = new Hashtable<String, Object>();
        style.put(mxConstants.STYLE_OPACITY, 50);
        //style.replace(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_CONNECTOR);
        style.put(mxConstants.STYLE_FONTCOLOR, "#774400");
        stylesheet.putCellStyle("ROUNDED", style);

        System.out.println("hsit" + graphComponent.getCellAt(19,19));
        System.out.println("hzesit");
        System.out.println(graphComponent.getCellAt(20,20).equals(vertex) + "HOURAAAA");
        mainPanel.add(graphComponent);

        setContentPane(mainPanel);  //defini le panel de la JFrame
        setVisible(true);  //affiche la JFrame

        setSize(400, 320);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //  mxConstants.STYLE_SOURCE_PORT;

    }

    private void createMenu() {
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
    }

    public ControleurDiagramme getControleur(){
        return ihm.getControleur();
    }

    @Override
    public void miseAJour() {
        // TODO Auto-generated method stub
    }

    public EtatGraph ajouterEtatSimple(EtatGraph parent, String label, EnumEtat type){
        this.getGraphComponent().getGraph().getModel().beginUpdate();
        Object newEtatParent = ((parent == null) ? graph.getDefaultParent() : parent);
        Object etat_graph;
        try {
            etat_graph = this.getGraphComponent().getGraph().insertVertex(newEtatParent, null, label, 50, 50, 80, 30);
            //this.getListe_elements_graphiques().put(label, new ElementGraphique(null,(mxCell)etat_graph));
        } finally {
            graph.getModel().endUpdate();
        }
        return new EtatGraph(parent, (mxCell) etat_graph, type);
    }

    public EtatGraph ajouterEtatPseudoInitial(EtatGraph parent, String label, EnumEtat type){
        this.getGraphComponent().getGraph().getModel().beginUpdate();
        Object newEtatParent = ((parent == null) ? graph.getDefaultParent() : parent);
        Object etat_graph = null;
        try {
            etat_graph = this.getGraphComponent().getGraph().insertVertex(parent, null, label, 50, 50, 80, 30);
            //this.getListe_elements_graphiques().put(label, new ElementGraphique(null,(mxCell)etat_graph));
        } finally {
            graph.getModel().endUpdate();
        }
        return new EtatGraph(parent, (mxCell) etat_graph, type);
    }

    public EtatGraph ajouterEtatPseudoFinal(EtatGraph parent, String label, EnumEtat type){
        this.getGraphComponent().getGraph().getModel().beginUpdate();
        Object newEtatParent = ((parent == null) ? graph.getDefaultParent() : parent);
        Object etat_graph;
        try {
            etat_graph = this.getGraphComponent().getGraph().insertVertex(parent, null, label, 50, 50, 80, 30, etatFinalStyle);
            //this.getListe_elements_graphiques().put(label, new ElementGraphique(null,(mxCell)etat_graph));
        } finally {
            graph.getModel().endUpdate();
        }
        return new EtatGraph(parent, (mxCell) etat_graph, type);
    }

    public EtatGraph ajouterEtatComposite(EtatGraph parent, String label, EnumEtat type){
        this.getGraphComponent().getGraph().getModel().beginUpdate();
        Object newEtatParent = ((parent == null) ? graph.getDefaultParent() : parent);
        Object etat_graph;
        try {
            etat_graph = this.getGraphComponent().getGraph().insertVertex(parent, null, label, 50, 50, 80, 30);
            //this.getListe_elements_graphiques().put(label, new ElementGraphique(null,(mxCell)etat_graph));
        } finally {
            graph.getModel().endUpdate();
        }
        return new EtatGraph(parent, (mxCell) etat_graph, type);
    }

	/*	JPanel mainPanel = new JPanel();
	  mainPanel.addMouseListener(new MouseAdapter() {
	     @Override
	     public void mousePressed(MouseEvent e) {
	       System.out.println("yeah");
	     }
	  });*/

    //  this.add(mainPanel);
/*     mxGraph graph = new mxGraph();
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
//  graphComponent.addMouseListener(new MenuContextuelListener());
  graphComponent.addMouseListener(new MouseAdapter() {
	     @Override
	     public void mousePressed(MouseEvent e) {
	       System.out.println("yeah");
	     }
	  });
  graphComponent.setBackground(Color.BLACK);
  graphComponent.setSize(400, 320);
  graphComponent.setVisible(true);
  getContentPane().add(graphComponent);
	this.addMouseListener(new MouseAdapter() {
	     @Override
	     public void mousePressed(MouseEvent e) {
	       System.out.println("yeah");
	     }
	  });
  graphComponent.repaint();
  graphComponent.
  LayoutManager.pack();
*/
    public static EditeurGraphique instance() {
        return instanceUnique;
    }

    /*public HashMap<String, ElementGraphique> getListe_elements_graphiques() {
        return liste_elements_graphiques;
    }

    public void setListe_elements_graphiques(HashMap<String, ElementGraphique> liste_elements_graphiques) {
        this.liste_elements_graphiques = liste_elements_graphiques;
    }

    public ElementGraphique getElement_from_liste(String label){
        return liste_elements_graphiques.get(label);
    }*/

    public mxGraphComponent getGraphComponent(){
        return graphComponent;
    }
}
