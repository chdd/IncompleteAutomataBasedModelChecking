package it.polimi.model.io.iba;

import it.polimi.model.automata.ba.LabelledTransition;
import it.polimi.model.automata.ba.State;
import it.polimi.model.automata.iba.IncompleteBuchiAutomaton;
import it.polimi.model.io.ba.BAMetadataToStateTransformer;
import edu.uci.ics.jung.io.graphml.NodeMetadata;

public class IBAMetadataToStateTransformer<BA extends IncompleteBuchiAutomaton<State, LabelledTransition<State>>> extends
		BAMetadataToStateTransformer<BA> {

	public IBAMetadataToStateTransformer(BA ba) {
		super(ba);
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
		if(Boolean.parseBoolean(input.getProperty("transparent"))==true){
			ba.addTransparentState(s);
		}
		return s;
	}
}
