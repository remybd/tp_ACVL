package Vues;

import Controleurs.ControleurDiagramme;
import ElementsDiagramme.EnumEtat;
import ElementsDiagramme.EnumTransition;
import ElementsDiagramme.Etat;
import ElementsDiagramme.PseudoInitial;
import ElementsDiagramme.Transition;

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
import java.io.File;
import java.util.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by Jerem on 11/10/2015.
 */

public class EditeurGraphique extends JFrame implements ObservateurVue {

    private final static String etatInitialStyle = mxConstants.STYLE_SHAPE + "="+ mxConstants.SHAPE_ELLIPSE;
    private final static String etatFinalStyle = mxConstants.STYLE_SHAPE + "="+ mxConstants.SHAPE_DOUBLE_ELLIPSE;

    private static HashMap<mxCell, ElementGraphique> liste_elements_graphiques = new HashMap<>();

    private ZoneErreur zone_erreur = new ZoneErreur();
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
    private JMenuItem aplatir = new JMenuItem("Aplatir");
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

        JPanel mainPanel = new JPanel();
        JPanel panelGraph = new JPanel(); //Panel

        BorderLayout bl = new BorderLayout();
        BorderLayout b2 = new BorderLayout();

        mainPanel.setLayout(bl);
        panelGraph.setLayout(b2);

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

            // Création du diagramme initial
            //graph.insertVertex(parent, null, "Initial", 20, 20, 30, 30, etatInitialStyle);

            /*Object v2 = graph.insertVertex(parent, null, "Final!", 240, 150, 30, 30, etatFinalStyle);
            graph.insertEdge(parent, null, "Edge", v1, v2);*/

        } finally {
            graph.getModel().endUpdate();
        }
        graphComponent = new mxGraphComponent(graph);
        graphComponent.setBackground(Color.black);
        graphComponent.setAutoExtend(false);
        // graph.getModel().get
        //  graphComponent.getCellAt(MouseEvent.gCursor.getDefaultCursor().g, arg1)
        graphComponent.getGraphControl().addMouseListener(new MenuContextuelListener(graphComponent));
        graphComponent.getGraphControl().setSize(this.getSize());
    //    graphComponent.getGraph().setEdgeLabelsMovable(false);
    //    graphComponent.getGraph().setAllowDanglingEdges(false);
        graphComponent.getGraphHandler().setRemoveCellsFromParent(false);
        graphComponent.setConnectable(false);
        graphComponent.getGraph().setVertexLabelsMovable(false);
        graphComponent.getGraph().setEdgeLabelsMovable(false);
        graphComponent.getGraph().setAllowDanglingEdges(false);
        graphComponent.setExportEnabled(false);
        graphComponent.getGraph().setCellsDisconnectable(false);
        graphComponent.getGraph().setCellsEditable(false);
        graphComponent.setEnterStopsCellEditing(false);
     //   graphComponent.getGraphHandler().set;
        graphComponent.getGraph().setDropEnabled(false);
     //   graphComponent.setImportEnabled(false);

    //    graphComponent.getGraph().setExtendParents(false);

       // graphComponent.getGraphHandler().set;

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

        //System.out.println("hsit" + graphComponent.getCellAt(19,19));
        //System.out.println(graphComponent.getCellAt(20,20).equals(vertex) + "HOURAAAA");

        graphComponent.setMaximumSize(new Dimension(400,400));

        panelGraph.setMaximumSize(new Dimension(400, 400));
        panelGraph.add(graphComponent);

        mainPanel.setMaximumSize(new Dimension(400, 400));
        mainPanel.add(panelGraph);
        mainPanel.add(zone_erreur, BorderLayout.NORTH);

        this.setContentPane(mainPanel);
        setVisible(true); //affiche la JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //  mxConstants.STYLE_SOURCE_PORT;
    }

    private void createMenu() {
        // Ajout des sous-items aux items de la barre de menu
        ouvrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser c = new JFileChooser();
                int rVal = c.showOpenDialog(EditeurGraphique.this);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                	File f = c.getSelectedFile();
                    filename.setText(f.getName());
                    dir.setText(f.getAbsolutePath().toString());
                    System.out.println(dir.getText());
                 
						try {
							Ihm.instance().getControleurFichier().chargerFichier(f.getAbsolutePath().toString());
						} catch (ClassNotFoundException
								| IOException e1) {
							new FenetreErreur("Une erreur survenue pendant l'ouverture du fichier ; il n'existe peut-�tre pas.");
							e1.printStackTrace();
						}
					
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
                	File f = c.getSelectedFile();
                    filename.setText(f.getName());
                    dir.setText(f.getAbsolutePath().toString());
                    try {
						Ihm.instance().getControleurFichier().sauvegarderFichier(dir.getText());
					} catch (FileNotFoundException e1) {
						new FenetreErreur("Une erreur est survenue pendant la sauvegarde du fichier : il n'existe pas.");
						e1.printStackTrace();
					} catch (IOException e1) {
						new FenetreErreur("Une erreur d'�criture est survenue pendant la sauvegarde du fichier.");
						e1.printStackTrace();
					}
                }
                if (rVal == JFileChooser.CANCEL_OPTION) {
                    filename.setText("You pressed cancel");
                    dir.setText("");
                }
            }
        });
        aplatir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
           
                    try {
						Ihm.instance().getControleur().applatir();
					} catch (Exception e1) {
						new FenetreErreur("Une erreur non geree est survenue pendant l'applatissement.");
						e1.printStackTrace();
					}
                
            }
        });
        fichier.add(ouvrir);
        fichier.add(enregistrer);
        fichier.add(aplatir);
        fichier.addSeparator();

        fermer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fichier.add(fermer);

        consulter_manuel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ihm.instance().getControleurFichier().ouvrirManuel();
            }
        });
        aide.add(consulter_manuel);
        a_propos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String a_propos_texte = "TransUML \n RAKOTOARISOA Jérémy \n BATAL Thibaut \n BEULE DAUZAT Rémy \n ROUSSEAU Benjamin";
                JOptionPane jop = new JOptionPane();
                jop.showMessageDialog(null, a_propos_texte, "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        aide.add(a_propos);

        menu.add(fichier);
        menu.add(aide);

        this.setJMenuBar(menu);
    }

    public ZoneErreur getZoneErreur(){
        return zone_erreur;
    }

    @Override
    public void miseAJour() {
        // TODO Auto-generated method stub
    }

    public EtatGraph ajouterEtatSimple(EtatGraph parent, String label, EnumEtat type){
        this.getGraphComponent().getGraph().getModel().beginUpdate();
        Object newEtatParent = ((parent == null) ? graph.getDefaultParent() : parent.getObjet_graphique());
        mxCell etat_graph;
        EtatGraph eg;
        try {
            etat_graph = (mxCell)this.getGraphComponent().getGraph().insertVertex(newEtatParent, null, label, 50, 50, 80, 30);
            System.out.println("Creation etat simple: " + etat_graph.getParent());
            eg = new EtatGraph(parent,etat_graph, type);
            this.getListe_elements_graphiques().put(etat_graph, eg);
        } finally {
            graph.getModel().endUpdate();
        }
        return eg;
    }

    public EtatGraph ajouterEtatPseudoInitial(EtatGraph parent, String label, EnumEtat type){
        this.getGraphComponent().getGraph().getModel().beginUpdate();
        Object newEtatParent = ((parent == null) ? graph.getDefaultParent() : parent.getObjet_graphique());
        mxCell etat_mxcell = null;
        EtatGraph eg;
        try {
            etat_mxcell = (mxCell)this.getGraphComponent().getGraph().insertVertex(newEtatParent, null, label, 50, 50, 80, 30, etatInitialStyle);
            System.out.println("Creation etat initial : " + etat_mxcell.getParent());
            eg = new EtatGraph(parent, (mxCell) etat_mxcell, type);
            this.getListe_elements_graphiques().put(etat_mxcell, eg);
        } finally {
            graph.getModel().endUpdate();
        }
        return eg;
    }

    public EtatGraph ajouterEtatPseudoFinal(EtatGraph parent, String label, EnumEtat type){
        this.getGraphComponent().getGraph().getModel().beginUpdate();
        Object newEtatParent = ((parent == null) ? graph.getDefaultParent() : parent.getObjet_graphique());
        mxCell etat_mxcell;
        EtatGraph eg;
        try {
            etat_mxcell = (mxCell)this.getGraphComponent().getGraph().insertVertex(newEtatParent, null, label, 50, 50, 80, 30, etatFinalStyle);
            eg = new EtatGraph(parent,etat_mxcell, type);
            this.getListe_elements_graphiques().put(etat_mxcell, eg);
        } finally {
            graph.getModel().endUpdate();
        }
        return eg;
    }

    public EtatGraph ajouterEtatComposite(EtatGraph parent, String label, EnumEtat type){
        this.getGraphComponent().getGraph().getModel().beginUpdate();
        Object newEtatParent = ((parent == null) ? graph.getDefaultParent() : parent.getObjet_graphique());
        mxCell etat_mxcell;
        EtatGraph eg;
        try {
            etat_mxcell = (mxCell)this.getGraphComponent().getGraph().insertVertex(newEtatParent, null, label, 50, 50, 80, 30);
            System.out.println("Creation etat composite: " + etat_mxcell.getParent());
            eg = new EtatGraph(parent,etat_mxcell, type);
            this.getListe_elements_graphiques().put(etat_mxcell,eg);
        } finally {
            graph.getModel().endUpdate();
        }
        return eg;
    }

    public TransitionGraph ajouterTransition(EtatGraph parent, EtatGraph source, EtatGraph destination, String etiquette, EnumTransition type){
        this.getGraphComponent().getGraph().getModel().beginUpdate();
        Object newEtatParent = ((parent == null) ? graph.getDefaultParent() : parent.getObjet_graphique());
        mxCell transition_mxcell;
        TransitionGraph tg;
        try {
            transition_mxcell = (mxCell)this.getGraphComponent().getGraph().insertEdge(newEtatParent, null, etiquette,
                    (Object)source.getObjet_graphique(),(Object)destination.getObjet_graphique());
            if(parent == null)
                tg = new TransitionGraph(parent, source, destination, transition_mxcell, type);
            else
                tg = new TransitionGraph(parent.getParent(), source, destination, transition_mxcell, type);
            this.getListe_elements_graphiques().put(transition_mxcell,tg);
        } finally {
            graph.getModel().endUpdate();
        }
        return tg;
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

    public HashMap<mxCell, ElementGraphique> getListe_elements_graphiques() {
        return liste_elements_graphiques;
    }

    public void setListe_elements_graphiques(HashMap<mxCell, ElementGraphique> liste_elements_graphiques) {
        this.liste_elements_graphiques = liste_elements_graphiques;
    }

    public ElementGraphique getElement_from_liste(mxCell objet){
        return liste_elements_graphiques.get(objet);
    }

    public mxGraphComponent getGraphComponent(){
        return graphComponent;
    }

    public mxGraph getGraph(){
        return graph;
    }

	public void updateListeElementGraphiqueAndDisplay(
			HashSet<ElementGraphique> listAllElementsGraphique) {
        EditeurGraphique.instance().getGraph().removeCells();

		graph.getModel().beginUpdate();
		try {
			reset();
            ArrayList<ElementGraphique> eg_array = new ArrayList<ElementGraphique>();

			for(ElementGraphique e : listAllElementsGraphique) {
                if(!eg_array.contains(e)) {
                	if(e.getObjet_graphique().isVertex()) {
                        addParent(e,eg_array);
                        liste_elements_graphiques.put(e.getObjet_graphique(), e);
                	}
                }
			}
			
			for(ElementGraphique e : listAllElementsGraphique) {
                if(!eg_array.contains(e)) {
                    addParent(e,eg_array);
                    liste_elements_graphiques.put(e.getObjet_graphique(), e);
                }
			}
			
		} finally {
            graph.getModel().endUpdate();
		}
	}

    public Object addParent(ElementGraphique eg, ArrayList<ElementGraphique> eg_array) {
    	Object res = null;
        if(eg != null) {
            if (eg.getObjet_graphique().getParent() != null) {
               Object parent = addParent(eg.getParent(), eg_array);
                res = graph.addCell(eg.getObjet_graphique(), parent);
            } else {
                res = graph.addCell(eg.getObjet_graphique());
            }
            eg_array.add(eg);
        }
        return res;
    }
	
    private void reset() {
        for (Map.Entry<mxCell, ElementGraphique> entry : liste_elements_graphiques.entrySet()) {
        	Object[] tab = {entry.getKey()};
            EditeurGraphique.instance().getGraph().removeCells(tab);
        }
        liste_elements_graphiques = new HashMap<>();

        //EditeurGraphique.instance().getGraph().removeCells();
        //EditeurGraphique.instance().getGraph().removeCells(tabCells);
    }
}
