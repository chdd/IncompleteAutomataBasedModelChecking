package it.polimi.view.menu;

/*
 * PopupVertexEdgeMenuMousePlugin.java
 *
 * Created on March 21, 2007, 12:56 PM; Updated May 29, 2007
 *
 * Copyright March 21, 2007 Grotto Networking
 *
 */


import it.polimi.model.elements.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.JPopupMenu;

import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;


public class Plugin extends AbstractPopupGraphMousePlugin {
    private JPopupMenu edgePopup, vertexPopup;
    private ActionListener l;
    
    public Plugin(ActionListener l) {
        this(MouseEvent.BUTTON3_MASK, l);
    }
    

    
    public Plugin(int modifiers, ActionListener l) {
        super(modifiers);
        this.l=l;
    }
	public void mousePressed(MouseEvent e){
		super.mousePressed(e);
		l.actionPerformed(new ActionEvent(e.getSource(), e.getID(), e.paramString()));
	}
	
	 public void mouseReleased(MouseEvent e) {
		 super.mousePressed(e);
			l.actionPerformed(new ActionEvent(e.getSource(), e.getID(), e.paramString()));
	 }
	 public void mouseClicked(MouseEvent e) {
		 l.actionPerformed(new ActionEvent(e.getSource(), e.getID(), e.paramString()));
	    }
	    
	   
    
    protected void handlePopup(MouseEvent e) {
        @SuppressWarnings("unchecked")
		final VisualizationViewer<State,LabelledTransition> vv =
                (VisualizationViewer<State,LabelledTransition>)e.getSource();
        Point2D p = e.getPoint();
        
        GraphElementAccessor<State,LabelledTransition> pickSupport = vv.getPickSupport();
        if(pickSupport != null) {
            final State v = pickSupport.getVertex(vv.getGraphLayout(), p.getX(), p.getY());
            if(v != null) {
                updateVertexMenu(v, vv, p);
                vertexPopup.show(vv, e.getX(), e.getY());
            } else {
                final LabelledTransition edge = pickSupport.getEdge(vv.getGraphLayout(), p.getX(), p.getY());
                if(edge != null) {
                    updateEdgeMenu(edge, vv, p);
                    edgePopup.show(vv, e.getX(), e.getY());
                  
                }
            }
        }
    }
    
    @SuppressWarnings("unchecked")
	private void updateVertexMenu(State v, VisualizationViewer<State,LabelledTransition> vv, Point2D point) {
        if (vertexPopup == null) return;
        Component[] menuComps = vertexPopup.getComponents();
        for (Component comp: menuComps) {
            if (comp instanceof StateMenuListener) {
                ((StateMenuListener<State, LabelledTransition>)comp).setVertexAndView(v, vv);
            }
            if (comp instanceof MenuPointListener) {
                ((MenuPointListener)comp).setPoint(point);
            }
        }
        
    }
    
    /**
     * Getter for the edge popup.
     * @return 
     */
    public JPopupMenu getEdgePopup() {
        return edgePopup;
    }
    
    /**
     * Setter for the Edge popup.
     * @param edgePopup 
     */
    public void setEdgePopup(JPopupMenu edgePopup) {
        this.edgePopup = edgePopup;
    }
    
    /**
     * Getter for the vertex popup.
     * @return 
     */
    public JPopupMenu getVertexPopup() {
        return vertexPopup;
    }
    
    /**
     * Setter for the vertex popup.
     * @param vertexPopup 
     */
    public void setVertexPopup(JPopupMenu vertexPopup) {
        this.vertexPopup = vertexPopup;
    }
    
    @SuppressWarnings("unchecked")
	private void updateEdgeMenu(LabelledTransition edge, VisualizationViewer<State, LabelledTransition> vv, Point2D point) {
        if (edgePopup == null) return;
        Component[] menuComps = edgePopup.getComponents();
        for (Component comp: menuComps) {
            if (comp instanceof EdgeMenuListener) {
                ((EdgeMenuListener<State, LabelledTransition>)comp).setEdgeAndView(edge, vv);
            }
            if (comp instanceof MenuPointListener) {
                ((MenuPointListener)comp).setPoint(point);
            }
        }
    }
    
}