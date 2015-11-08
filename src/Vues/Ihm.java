package Vues;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import ElementsDiagramme.Etat;
import ElementsDiagramme.Transition;

/**
 * Created by r�my on 06/11/2015.
 */
public class Ihm {
	final private static Ihm instanceUnique = new Ihm();
	private EditeurGraphique edGraphique = EditeurGraphique.instance();


	private Ihm() {	}
	
	static public Ihm instance() {
		return instanceUnique;
	}

	public EditeurGraphique getEdGraphique() {
		return this.edGraphique;
	}
	
    public TransitionGraph createTransitionGraph(Transition t){

        return null;
    }

    public EtatGraph createEtatGraph(Etat e){

        return null;
    }
    
	public static void main(String[] args) {
	    Ihm frame = Ihm.instance();

	  /*  JGraph graph = new JGraph();
	    JFrame frame = new JFrame();
	    frame.getContentPane().add(new JScrollPane(graph));
	    frame.pack();
	    frame.setVisible(true);*/
	    
	/*    frame.getEdGraphique().addMouseListener(new MouseAdapter() { 
	    	public void mousePressed(MouseEvent e) {
	    		System.out.println("test");
	    	}
	    	
	    });*/
	    
	  }
}
