package it.polimi.view.incompleteautomaton.editing;

import edu.uci.ics.jung.visualization.VisualizationViewer;

public interface StateMenuListener<V, E> {
    void setVertexAndView(V v, VisualizationViewer<V,E> visView);    
}