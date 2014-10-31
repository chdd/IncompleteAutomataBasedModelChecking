package it.polimi.view.automaton;

import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableIntBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.trasformers.HighlighPathPaintTransformer;
import it.polimi.view.trasformers.IntersectionAutomatonEdgeStrokeTransformed;
import it.polimi.view.trasformers.IntersectionAutomatonStrokeTransformer;

import java.awt.Stroke;
import java.awt.event.ActionListener;
import java.util.Stack;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;

@SuppressWarnings("serial")
public class IntersectionAutomatonJPanel<
	STATE extends State, 
	TRANSITION extends LabelledTransition,
	INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONTRANSITION extends ConstrainedTransition<STATE>, 
	LABELLEDTRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>,
	CONSTRAINEDTRANSITIONFACTORY extends ConstrainedTransitionFactory<STATE, INTERSECTIONTRANSITION>,
	INTERSECTIONAUTOMATON  extends DrawableIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, CONSTRAINEDTRANSITIONFACTORY>> extends
		IncompleteBuchiAutomatonJPanel<INTERSECTIONSTATE, INTERSECTIONTRANSITION, CONSTRAINEDTRANSITIONFACTORY, INTERSECTIONAUTOMATON> 

{

	public IntersectionAutomatonJPanel(INTERSECTIONAUTOMATON a, ActionListener l, AbstractLayout<INTERSECTIONSTATE, INTERSECTIONTRANSITION> layout) {
		super(a, l, layout);
	}
	
	public void highlightPath(Stack<INTERSECTIONSTATE> states, INTERSECTIONAUTOMATON a){
		this.setTransformers(a);
		this.getRenderContext().setVertexFillPaintTransformer(
				new HighlighPathPaintTransformer<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, LABELLEDTRANSITIONFACTORY, CONSTRAINEDTRANSITIONFACTORY, INTERSECTIONAUTOMATON>(a, states));
		
		this.getGraphLayout().setGraph(a);
		this.repaint();
	}
	
	@Override
	protected Transformer<INTERSECTIONSTATE, Stroke> getStateStrokeTransformer(INTERSECTIONAUTOMATON a){
		return new IntersectionAutomatonStrokeTransformer<STATE,TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, LABELLEDTRANSITIONFACTORY ,CONSTRAINEDTRANSITIONFACTORY, INTERSECTIONAUTOMATON>(a);
	}
	@Override
	protected Transformer<INTERSECTIONTRANSITION, Stroke> getStrokeEdgeStrokeTransformer(){
		return new IntersectionAutomatonEdgeStrokeTransformed<STATE,INTERSECTIONTRANSITION>();
	}
	
	@Override
	public void setEditingMode() {
		this.setTranformingMode();
	}
}
