package it.polimi.model.io.iba;

import it.polimi.model.automata.ba.LabelledTransition;
import it.polimi.model.automata.ba.state.BAMetadataToStateTransformer;
import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.iba.IncompleteBuchiAutomaton;
import edu.uci.ics.jung.io.graphml.NodeMetadata;

public class IBAMetadataToStateTransformer<BA extends IncompleteBuchiAutomaton<State, LabelledTransition<State>>> extends
		BAMetadataToStateTransformer<BA> {

	public IBAMetadataToStateTransformer(BA ba) {
		super(ba);
	}

	@Override
	public State transform(NodeMetadata input) { 
		State s=super.transform(input);
		
		if(Boolean.parseBoolean(input.getProperty("transparent"))==true){
			ba.addTransparentState(s);
		}
		return s;
	}
}
