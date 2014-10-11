package it.polimi.view.menu;

import edu.uci.ics.jung.visualization.VisualizationViewer;

public interface StateMenuListener<V, E> {
    void setVertexAndView(V v, VisualizationViewer<V,E> visView);    
}