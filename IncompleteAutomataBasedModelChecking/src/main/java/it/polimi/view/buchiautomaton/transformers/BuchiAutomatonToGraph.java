package it.polimi.view.buchiautomaton.transformers;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.graph.Graph;

public class BuchiAutomatonToGraph implements Transformer<BuchiAutomaton<State, Transition<State>>, Graph<State,Transition<State>>> {

	@Override
	public Graph<State, Transition<State>> transform(
			BuchiAutomaton<State, Transition<State>> input) {
		// TODO Auto-generated method stub
		return null;
	}

	

	
}
