package it.polimi.model.automata.ba.io.fromfile;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.LabelledTransition;
import it.polimi.model.automata.ba.state.State;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.graphml.GraphMetadata;

public class BATransformer<Metadata extends GraphMetadata, BA extends BuchiAutomaton<State, LabelledTransition<State>>> implements Transformer<Metadata, BA> {
	private BA ba;
	public BATransformer(BA ba){
		this.ba=ba;
		
	}
	@Override
	public BA transform(GraphMetadata input) {
		return ba;
	}

}
