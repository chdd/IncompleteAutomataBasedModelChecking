package it.polimi.view.trasformers;

import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.elements.states.State;

import org.apache.commons.collections15.Predicate;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Context;

public class ShowEdgeArrowsPredicate<S extends State, T extends LabelledTransition> implements Predicate<Context<Graph<S, T>, T>> {

	protected boolean show_d;
	protected boolean show_u;
	
	public ShowEdgeArrowsPredicate(boolean show_d, boolean show_u)
	{
		this.show_d = show_d;
		this.show_u = show_u;
	}
	
	@Override
	public boolean evaluate(Context<Graph<S, T>, T> context) {
		if (show_d) {
			 return true;
		}
		return false;
	}
}