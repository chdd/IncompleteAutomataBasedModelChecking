package it.polimi.model.elements.states;

import it.polimi.model.impl.automata.IBAImpl;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactoryInterface;
import edu.uci.ics.jung.io.graphml.NodeMetadata;

public class IBAMetadataToStateTransformer<BA extends IBAImpl<State, LabelledTransition, LabelledTransitionFactoryInterface<LabelledTransition>>> extends BAMetadataToStateTransformer<BA>{

	
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
