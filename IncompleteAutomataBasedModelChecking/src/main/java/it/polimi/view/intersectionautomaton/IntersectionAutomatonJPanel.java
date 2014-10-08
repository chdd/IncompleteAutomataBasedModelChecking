package it.polimi.view.intersectionautomaton;

import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.transition.ConstrainedTransition;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.automata.intersection.IntersectionAutomaton;
import it.polimi.model.automata.intersection.IntersectionState;
import it.polimi.view.automaton.IncompleteBuchiAutomatonJPanel;
import it.polimi.view.intersectionautomaton.transformers.IntersectionAutomatonEdgeStrokeTransformed;
import it.polimi.view.intersectionautomaton.transformers.IntersectionAutomatonStrokeTransformer;

import java.awt.Dimension;
import java.awt.Stroke;
import java.awt.event.ActionListener;

import org.apache.commons.collections15.Transformer;

@SuppressWarnings("serial")
public class IntersectionAutomatonJPanel<S extends State, T extends LabelledTransition, S1 extends IntersectionState<S>, T1 extends ConstrainedTransition<S>, A  extends IntersectionAutomaton<S, T, S1, T1>> extends
		IncompleteBuchiAutomatonJPanel<S1, T1, A> {

	public IntersectionAutomatonJPanel(Dimension d, A a, ActionListener l) {
		super(d,a, l);
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
