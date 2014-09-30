package it.polimi.view.buchiautomaton.transformers;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.LabelledTransition;

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
