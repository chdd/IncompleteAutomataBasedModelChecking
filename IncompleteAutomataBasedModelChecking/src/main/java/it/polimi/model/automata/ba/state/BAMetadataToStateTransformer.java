package it.polimi.model.automata.ba.state;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.LabelledTransition;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.graphml.NodeMetadata;

public class BAMetadataToStateTransformer<BA extends BuchiAutomaton<State, LabelledTransition<State>>> implements Transformer<NodeMetadata, State> {
	
	protected BA ba;
	public BAMetadataToStateTransformer(BA ba){
		this.ba=ba;
	}

	@Override
	public State transform(NodeMetadata input) {
		
		State s=new State(input.getProperty("name"), Integer.parseInt(input.getId()));
		ba.addVertex(s);
			
		if(Boolean.parseBoolean(input.getProperty("initial"))==true){
			ba.addInitialState(s);
		}
		if(Boolean.parseBoolean(input.getProperty("accepting"))==true){
			ba.addAcceptState(s);
		}
		return s;
	}

}