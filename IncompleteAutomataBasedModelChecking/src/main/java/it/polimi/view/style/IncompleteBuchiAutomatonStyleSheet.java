package it.polimi.view.style;

import it.polimi.model.IncompleteBuchiAutomaton;

import java.awt.Color;
import java.util.Hashtable;

import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxUtils;
import com.mxgraph.view.mxStylesheet;

/**
 * @author claudiomenghi
 * Is the {@link mxStylesheet} that corresponds to a {@link IncompleteBuchiAutomaton}
 */
public class IncompleteBuchiAutomatonStyleSheet extends BuchiAutomatonStyleSheet {
	
	/**
	 * creates the {@link mxStylesheet} of an {@link IncompleteBuchiAutomaton}
	 */
	public IncompleteBuchiAutomatonStyleSheet(){
		 super();
		 
		 // set the style of the transparent states
		 Hashtable<String, Object> styleTransparentState = new Hashtable<String, Object>();
	     styleTransparentState.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(Color.GRAY));
	     styleTransparentState.put(mxConstants.STYLE_STROKEWIDTH, 1.5);
	     styleTransparentState.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.BLACK));
	     styleTransparentState.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
	     styleTransparentState.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_ELLIPSE);
	     
	     // set the style of the transparent final states
	     Hashtable<String, Object> styleTransparentFinalState = new Hashtable<String, Object>();
	     styleTransparentFinalState.put(mxConstants.STYLE_FILLCOLOR, mxUtils.getHexColorString(Color.GRAY));
	     styleTransparentFinalState.put(mxConstants.STYLE_STROKEWIDTH, 1.5);
	     styleTransparentFinalState.put(mxConstants.STYLE_STROKECOLOR, mxUtils.getHexColorString(Color.BLACK));
	     styleTransparentFinalState.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_DOUBLE_ELLIPSE);
	     styleTransparentFinalState.put(mxConstants.STYLE_PERIMETER, mxConstants.PERIMETER_ELLIPSE);
	     
	     this.putCellStyle("TransparentState", styleTransparentState);
	     this.putCellStyle("TransparentFinalState", styleTransparentFinalState);
	}
}
