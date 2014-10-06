package it.polimi.model.io.ba;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.LabelledTransition;
import it.polimi.model.automata.ba.State;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.graphml.DataMetadata;
import edu.uci.ics.jung.io.graphml.NodeMetadata;

public class StateAcceptingToMetadataTransformer<S extends State, T extends LabelledTransition<S>,BA extends BuchiAutomaton<S, T>> implements Transformer<S, String>  {

	private BA ba;
	public StateAcceptingToMetadataTransformer(BA ba){
		this.ba=ba;
	}
	
	@Override
	public String transform(S input) {
		
		NodeMetadata m=new NodeMetadata();
		m.setId(input.getName());
		
		DataMetadata initial=new DataMetadata();
		initial.setKey("accepting");
		return Boolean.toString(ba.isAccept(input));
	}
}
