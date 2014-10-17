package it.polimi.model.elements.states;

import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.impl.BAImpl;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.graphml.DataMetadata;
import edu.uci.ics.jung.io.graphml.NodeMetadata;

public class BAStateInitialToMetadataTransformer<S extends State, T extends LabelledTransition,BA extends BAImpl<S, T>> implements Transformer<S, String>  {

	private BA ba;
	public BAStateInitialToMetadataTransformer(BA ba){
		this.ba=ba;
	}
	
	@Override
	public String transform(S input) {
		
		NodeMetadata m=new NodeMetadata();
		m.setId(input.getName());
		
		DataMetadata initial=new DataMetadata();
		initial.setKey("initial");
		return Boolean.toString(ba.isInitial(input));
	}
}
