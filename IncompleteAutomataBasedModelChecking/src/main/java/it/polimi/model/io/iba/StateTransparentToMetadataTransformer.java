package it.polimi.model.io.iba;

import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.automata.impl.IBAImpl;
import it.polimi.model.elements.states.State;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.graphml.DataMetadata;
import edu.uci.ics.jung.io.graphml.NodeMetadata;

public class StateTransparentToMetadataTransformer <S extends State, T extends LabelledTransition,BA extends IBAImpl<S, T>> implements Transformer<S, String> {

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
