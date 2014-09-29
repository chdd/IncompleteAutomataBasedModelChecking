package it.polimi.view.intersectionautomaton;

import it.polimi.model.IntersectionAutomaton;
import it.polimi.model.IntersectionState;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.view.automaton.transformers.BuchiAutomatonStrokeTransformer;
import it.polimi.view.incompleteautomaton.IncompleteBuchiAutomatonJPanel;
import it.polimi.view.intersectionautomaton.transformers.IntersectionAutomatonEdgeStrokeTransformed;
import it.polimi.view.intersectionautomaton.transformers.IntersectionAutomatonStrokeTransformer;

import java.awt.Dimension;

@SuppressWarnings("serial")
public class IntersectionAutomatonJPanel<S extends State, T extends Transition<S>, S1 extends IntersectionState<S>, T1 extends Transition<S1>, A  extends IntersectionAutomaton<S, T, S1, T1>> extends
		IncompleteBuchiAutomatonJPanel<S1, T1, A> {

	public IntersectionAutomatonJPanel(Dimension d) {
		super(d);
	}
	@Override
	protected BuchiAutomatonStrokeTransformer<S1, T1, A> getStrokeTransformer(A a){
		return new IntersectionAutomatonStrokeTransformer<S,T, S1, T1, A>(a);
	}
	@Override
	protected IntersectionAutomatonEdgeStrokeTransformed<S, T, S1,T1> getStrokeEdgeTransformer(A a){
		return new IntersectionAutomatonEdgeStrokeTransformed<S, T, S1,T1>();
	}
}
