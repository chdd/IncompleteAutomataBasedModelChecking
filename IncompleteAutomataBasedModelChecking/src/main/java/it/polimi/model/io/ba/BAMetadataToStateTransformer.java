package it.polimi.model.io.ba;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.LabelledTransition;
import it.polimi.model.automata.ba.State;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.graphml.NodeMetadata;

public class BAMetadataToStateTransformer<BA extends BuchiAutomaton<State, LabelledTransition<State>>> implements Transformer<NodeMetadata, State> {
	
	protected BA ba;
	public BAMetadataToStateTransformer(BA ba){
		this.ba=ba;
	}

	@Override
	public State transform(NodeMetadata input) { 
		State s=new State(input.getId());
		
		if(Boolean.parseBoolean(input.getProperty("initial"))==true){
			ba.addInitialState(s);
		}
		if(Boolean.parseBoolean(input.getProperty("accepting"))==true){
			ba.addAcceptState(s);
		}
		return s;
	}

}
