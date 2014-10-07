package it.polimi.view.intersectionautomaton;

import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.transition.ConstrainedTransition;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.automata.intersection.IntersectionAutomaton;
import it.polimi.model.automata.intersection.IntersectionState;
import it.polimi.view.buchiautomaton.transformers.BuchiAutomatonStrokeTransformer;
import it.polimi.view.incompleteautomaton.IncompleteBuchiAutomatonJPanel;
import it.polimi.view.intersectionautomaton.transformers.IntersectionAutomatonEdgeStrokeTransformed;
import it.polimi.view.intersectionautomaton.transformers.IntersectionAutomatonStrokeTransformer;

import java.awt.Dimension;

@SuppressWarnings("serial")
public class IntersectionAutomatonJPanel<S extends State, T extends LabelledTransition, S1 extends IntersectionState<S>, T1 extends ConstrainedTransition<S>, A  extends IntersectionAutomaton<S, T, S1, T1>> extends
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