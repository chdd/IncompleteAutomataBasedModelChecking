package it.polimi.model.io.iba;

import it.polimi.model.automata.ba.LabelledTransition;
import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.iba.IncompleteBuchiAutomaton;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.graphml.DataMetadata;
import edu.uci.ics.jung.io.graphml.NodeMetadata;

public class StateTransparentToMetadataTransformer <S extends State, T extends LabelledTransition<S>,BA extends IncompleteBuchiAutomaton<S, T>> implements Transformer<S, String> {

	private BA ba;
	public StateTransparentToMetadataTransformer(BA ba){
		this.ba=ba;
	}
	
	@Override
	public String transform(S input) {
		
		NodeMetadata m=new NodeMetadata();
		m.setId(input.getName());
		
		DataMetadata initial=new DataMetadata();
		initial.setKey("transparent");
		return Boolean.toString(ba.isTransparent(input));
	}
}
