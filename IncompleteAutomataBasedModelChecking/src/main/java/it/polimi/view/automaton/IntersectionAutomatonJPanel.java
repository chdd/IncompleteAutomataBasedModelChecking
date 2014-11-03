package it.polimi.view.automaton;

import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableIntBA;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.trasformers.HighlighPathPaintTransformer;
import it.polimi.view.trasformers.IntersectionAutomatonEdgeStrokeTransformed;
import it.polimi.view.trasformers.IntersectionAutomatonStrokeTransformer;

import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.event.ActionListener;
import java.util.Stack;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;

@SuppressWarnings("serial")
public class IntersectionAutomatonJPanel
	<STATE extends State, 
	STATEFACTORY extends StateFactory<STATE>,
	TRANSITION extends LabelledTransition, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>,
	INTERSECTIONSTATE extends IntersectionState<STATE>, 
	INTERSECTIONSTATEFACTORY extends IntersectionStateFactory<STATE, INTERSECTIONSTATE>,
	INTERSECTIONTRANSITION extends ConstrainedTransition<STATE>,
	INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<STATE, INTERSECTIONTRANSITION>,
	INTERSECTIONAUTOMATON  extends DrawableIntBA<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY>>
	extends
		IncompleteBuchiAutomatonJPanel<INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY,  INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY, INTERSECTIONAUTOMATON> 

{

	
	
	public IntersectionAutomatonJPanel(INTERSECTIONAUTOMATON a, ActionListener l, AbstractLayout<INTERSECTIONSTATE, INTERSECTIONTRANSITION> layout) {
		super(a, l, layout);
	}
	
	
		
	
	public void highlightPath(Stack<INTERSECTIONSTATE> states, INTERSECTIONAUTOMATON a, Stack<INTERSECTIONTRANSITION> stackViolatingTransitions){
		this.setTransformers(a);
		
		this.getRenderContext().setEdgeFillPaintTransformer(
				new BuchiAutomatonTransitionPaintTransformer(a, stackViolatingTransitions));
		this.getRenderContext().setVertexFillPaintTransformer(
				new HighlighPathPaintTransformer<STATE, 
									TRANSITION, 
									TRANSITIONFACTORY, 
									INTERSECTIONSTATE, 
									INTERSECTIONTRANSITION, 
									INTERSECTIONTRANSITIONFACTORY, 
									INTERSECTIONAUTOMATON>(a, states));
		
		this.getGraphLayout().setGraph(a);
		this.repaint();
	}
	
	@Override
	protected Transformer<INTERSECTIONSTATE, Stroke> getStateStrokeTransformer(INTERSECTIONAUTOMATON a){
		return new IntersectionAutomatonStrokeTransformer<STATE,TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY, INTERSECTIONAUTOMATON>(a);
	}
	@Override
	protected Transformer<INTERSECTIONTRANSITION, Stroke> getStrokeEdgeStrokeTransformer(){
		return new IntersectionAutomatonEdgeStrokeTransformed<STATE,INTERSECTIONTRANSITION>();
	}
	
	@Override
	public void setEditingMode() {
		this.setTranformingMode();
	}
	
	
	
	public class BuchiAutomatonTransitionPaintTransformer implements Transformer<INTERSECTIONTRANSITION, Paint> {

	protected INTERSECTIONAUTOMATON a;
	protected Stack<INTERSECTIONTRANSITION> stackOfHighLightedTransitions;
	
	public BuchiAutomatonTransitionPaintTransformer(INTERSECTIONAUTOMATON a, Stack<INTERSECTIONTRANSITION> stackOfHighLightedTransitions){
		this.a=a;
		this.stackOfHighLightedTransitions=stackOfHighLightedTransitions;
	}
	@Override
	public Paint transform(INTERSECTIONTRANSITION input) {
		if(this.stackOfHighLightedTransitions.contains(input)){
			return Color.red;
		}
		return Color.WHITE;
	}

}
}
