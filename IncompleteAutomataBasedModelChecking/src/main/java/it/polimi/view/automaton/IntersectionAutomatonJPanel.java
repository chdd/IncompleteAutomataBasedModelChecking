package it.polimi.view.automaton;

import it.polimi.model.automata.ba.transition.ConstrainedTransition;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.elements.states.IntersectionState;
import it.polimi.model.elements.states.State;
import it.polimi.model.interfaces.drawable.DrawableIntBA;
import it.polimi.view.trasformers.HighlighPathPaintTransformer;
import it.polimi.view.trasformers.IntersectionAutomatonEdgeStrokeTransformed;
import it.polimi.view.trasformers.IntersectionAutomatonStrokeTransformer;

import java.awt.Stroke;
import java.awt.event.ActionListener;
import java.util.Stack;

import org.apache.commons.collections15.Transformer;

@SuppressWarnings("serial")
public class IntersectionAutomatonJPanel<S extends State, T extends LabelledTransition, S1 extends IntersectionState<S>, T1 extends ConstrainedTransition<S>, A  extends DrawableIntBA<S, T, S1, T1>> extends
		IncompleteBuchiAutomatonJPanel<S1, T1, A> {

	public IntersectionAutomatonJPanel(A a, ActionListener l) {
		super(a, l);
	}
	
	public void highlightPath(Stack<S1> states, A a){
		this.setTransformers(a);
		this.getRenderContext().setVertexFillPaintTransformer(
				new HighlighPathPaintTransformer<S, T, S1, T1, A>(a, states));
		
		this.getGraphLayout().setGraph(a);
		this.repaint();
	}
	
	@Override
	protected Transformer<S1, Stroke> getStateStrokeTransformer(A a){
		return new IntersectionAutomatonStrokeTransformer<S,T, S1, T1, A>(a);
	}
	@Override
	protected Transformer<T1, Stroke> getStrokeEdgeStrokeTransformer(){
		return new IntersectionAutomatonEdgeStrokeTransformed<S,T1>();
	}
	
	@Override
	public void setEditingMode() {
		this.setTranformingMode();
	}
}
