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

import java.awt.BasicStroke;
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
		IncompleteBuchiAutomatonJPanel<
		INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY,  INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY, INTERSECTIONAUTOMATON> 

{

	
	
	public IntersectionAutomatonJPanel(INTERSECTIONAUTOMATON a, ActionListener l, AbstractLayout<INTERSECTIONSTATE, INTERSECTIONTRANSITION> layout) {
		super(a, l, layout);
	}
	
	
		
	
	public void highlightPath(Stack<INTERSECTIONSTATE> states, INTERSECTIONAUTOMATON a, Stack<INTERSECTIONTRANSITION> stackViolatingTransitions){
		this.setTransformers(a);
		
		this.getRenderContext().setEdgeDrawPaintTransformer(
				new BuchiAutomatonTransitionPaintTransformer(a, stackViolatingTransitions));
		this.getRenderContext().setVertexFillPaintTransformer(
				new HighlighPathPaintTransformer(a, states));
		
		this.getGraphLayout().setGraph(a);
		this.repaint();
	}
	
	@Override
	protected Transformer<INTERSECTIONSTATE, Stroke> getStateStrokeTransformer(INTERSECTIONAUTOMATON a){
		return new IntersectionAutomatonStrokeTransformer(a);
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
			return Color.black;
		}
	}
	
	public class HighlighPathPaintTransformer extends BuchiAutomatonStatePaintTransformer {

	private Stack<INTERSECTIONSTATE> states;
	
		public HighlighPathPaintTransformer(INTERSECTIONAUTOMATON a, Stack<INTERSECTIONSTATE> states) {
			super(a);
			this.states=states;
		}
		
		@Override
		public Paint transform(INTERSECTIONSTATE input) {
			
			if(this.states!=null){
				if(this.states.contains(input)){
					return Color.RED;
				}
				if(a.isTransparent(input)){
					return Color.GRAY;
				}
			
			}
			return Color.WHITE;
		}
	}
	public class IncompleteBuchiAutomatonPaintTransformer
		extends BuchiAutomatonStatePaintTransformer {

		public IncompleteBuchiAutomatonPaintTransformer(INTERSECTIONAUTOMATON a) {
			super(a);
		}
	
		@Override
		public Paint transform(INTERSECTIONSTATE input) {
			if(a.isTransparent(input)){
				return Color.GRAY;
			}
			return Color.WHITE;
		}	
	}
	
	public class IntersectionAutomatonEdgeStrokeTransformed<S extends State, T extends ConstrainedTransition<S>> extends
	BuchiAutomatonEdgeStrokeTransormer<T> {
		@Override
		public Stroke transform(T input) {
			if(input.getConstrainedState()!=null)
			{
				float dash[]={10.0f};
				return new BasicStroke(0.5f,  BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 5.0f, dash, 0.0f);
			}
			else{
				return super.transform(input);
			}
		}
	}
	

	public class IntersectionAutomatonStrokeTransformer extends BuchiAutomatonStateStrokeTransofmer{

		public IntersectionAutomatonStrokeTransformer(INTERSECTIONAUTOMATON a) {
			super(a);
		}
		
		@Override
		public Stroke transform(INTERSECTIONSTATE input) {
			if(this.a.isMixed(input)){
				float dash[]={10.0f};
				return new BasicStroke(0.5f,  BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 5.0f, dash, 0.0f);
			}
			else{
				return super.transform(input);
			}
			
		}
	}
}
