package it.polimi.view.buchiautomaton.transformers;

import it.polimi.model.ba.BuchiAutomaton;
import it.polimi.model.ba.LabelledTransition;
import it.polimi.model.graph.State;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.graph.Graph;

public class BuchiAutomatonToGraph implements Transformer<BuchiAutomaton<State, LabelledTransition<State>>, Graph<State,LabelledTransition<State>>> {

	@Override
	public Graph<State, LabelledTransition<State>> transform(
			BuchiAutomaton<State, LabelledTransition<State>> input) {
		// TODO Auto-generated method stub
		return null;
	}

	

	
}
