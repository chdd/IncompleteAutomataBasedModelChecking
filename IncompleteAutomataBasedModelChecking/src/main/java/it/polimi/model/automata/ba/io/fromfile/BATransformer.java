package it.polimi.model.automata.ba.io.fromfile;

import it.polimi.model.impl.automata.BAImpl;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactoryInterface;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.graphml.GraphMetadata;

public class BATransformer<Metadata extends GraphMetadata, BA extends BAImpl<State, LabelledTransition, LabelledTransitionFactoryInterface<LabelledTransition>>> implements Transformer<Metadata, BA> {
	private BA ba;
	public BATransformer(BA ba){
		this.ba=ba;
		
	}
	@Override
	public BA transform(GraphMetadata input) {
		return ba;
	}

}
