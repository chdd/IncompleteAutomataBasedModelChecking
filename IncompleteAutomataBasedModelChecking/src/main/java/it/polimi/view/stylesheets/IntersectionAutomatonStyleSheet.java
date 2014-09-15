package it.polimi.view.stylesheets;

import it.polimi.model.IncompleteBuchiAutomaton;

import java.awt.Color;
import java.util.Hashtable;

import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxStylesheet;

/**
 * @author claudiomenghi
 * Is the {@link mxStylesheet} that corresponds to a {@link IntersectionAutomatonStyleSheet}
 */
public class IntersectionAutomatonStyleSheet extends IncompleteBuchiAutomatonStyleSheet {
	
	/**
	 * creates the {@link mxStylesheet} of an {@link IntersectionAutomaton}
	 */
	public IntersectionAutomatonStyleSheet(){
		super();
		
		// creates the style for a mixed states
		Hashtable<String, Object> stylemixedState = new Hashtable<String, Object>();
		stylemixedState.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(Color.WHITE));
		stylemixedState.put(mxConstants.STYLE_STROKEWIDTH, 1.5);
		stylemixedState.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.BLACK));
		stylemixedState.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
		stylemixedState.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_ELLIPSE);
		stylemixedState.put(mxConstants.STYLE_DASHED, true);
		 
		// creates a style for the constrained transition 
		Hashtable<String, Object> styleMixedEdges = new Hashtable<String, Object>();
		styleMixedEdges.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.BLACK));
		styleMixedEdges.put(mxConstants.STYLE_DASHED, true);
		 
		// creates the style for the mixed final states 
		Hashtable<String, Object> styleMixedFinalState = new Hashtable<String, Object>();
		styleMixedFinalState.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(Color.WHITE));
		styleMixedFinalState.put(mxConstants.STYLE_STROKEWIDTH, 1.5);
		styleMixedFinalState.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.BLACK));
		styleMixedFinalState.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_DOUBLE_ELLIPSE);
		styleMixedFinalState.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_ELLIPSE);
		styleMixedFinalState.put(mxConstants.STYLE_DASHED, true);
		 
		 
		this.putCellStyle("StyleMixedEdges", styleMixedEdges);
		this.putCellStyle("MixedState", stylemixedState);
		this.putCellStyle("MixedFinalState", styleMixedFinalState);
	}
}
