package Vues;

import com.mxgraph.model.mxCell;

/**
 * Created by rémy on 06/11/2015.
 */
public class ElementGraphique extends mxCell {
    ElementGraphique parent = null;

    public ElementGraphique(ElementGraphique parent, mxCell element) {
        if(element != null) {
            this.setValue(element.getValue());
            this.setGeometry(element.getGeometry());
            this.setStyle(element.getStyle());
            this.setVertex(element.isVertex());
            this.setEdge(element.isEdge());
            this.setConnectable(element.isConnectable());
            this.setVisible(element.isVisible());
            this.setCollapsed(element.isCollapsed());
            this.setId(element.getId());
            this.setParent(element.getParent());
            this.setSource(element.getSource());
            this.setTarget(element.getTarget());
            //this.children = element.children;
        }
    }

    public String getNom(){
        return (String)this.getValue();
    }

    public void setNom(String nom){
        this.setValue((Object)nom);
    }
}
