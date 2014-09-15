package it.polimi.view.stylesheets;

import it.polimi.model.BuchiAutomaton;

import java.awt.Color;
import java.util.Hashtable;

import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxStylesheet;

/**
 * @author claudiomenghi
 * Is the {@link mxStylesheet} that corresponds to a {@link BuchiAutomaton}
 */
public class BuchiAutomatonStyleSheet extends mxStylesheet {

	/**
	 * creates a new {@link mxStylesheet} of a {@link BuchiAutomaton}
	 */
	public BuchiAutomatonStyleSheet(){
		
		// sets the style of regular states
		Hashtable<String, Object> styleRegularState = new Hashtable<String, Object>();
        styleRegularState.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(Color.WHITE));
        styleRegularState.put(mxConstants.STYLE_STROKEWIDTH, 1.5);
        styleRegularState.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.BLACK));
        styleRegularState.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        styleRegularState.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_ELLIPSE);
        
        // sets the style of initial regular states
        Hashtable<String, Object> styleInitialState = new Hashtable<String, Object>();
        styleInitialState.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(Color.GREEN));
        styleInitialState.put(mxConstants.STYLE_STROKEWIDTH, 1.5);
        styleInitialState.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.BLACK));
        styleInitialState.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        styleInitialState.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_ELLIPSE);
       
        // sets the style of regular final states
        Hashtable<String, Object> styleRegularFinalState = new Hashtable<String, Object>();
        styleRegularFinalState.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(Color.WHITE));
        styleRegularFinalState.put(mxConstants.STYLE_STROKEWIDTH, 1.5);
        styleRegularFinalState.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.BLACK));
        styleRegularFinalState.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_DOUBLE_ELLIPSE);
        styleRegularFinalState.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_ELLIPSE);
        
        // sets the style of the edges
        Hashtable<String, Object> styleEdges = new Hashtable<String, Object>();
        styleEdges.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.BLACK));
       
        // put the styles in the current styleSheet
        this.putCellStyle("InitialState", styleInitialState);
        this.putCellStyle("RegularState", styleRegularState);
        this.putCellStyle("RegularFinalState", styleRegularFinalState);
        this.putCellStyle("EdgesStyle", styleEdges);
	}
}
