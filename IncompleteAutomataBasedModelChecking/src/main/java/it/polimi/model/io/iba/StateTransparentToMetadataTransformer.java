package it.polimi.model.io.iba;

import it.polimi.model.elements.states.State;
import it.polimi.model.impl.automata.IBAImpl;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactoryInterface;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.graphml.DataMetadata;
import edu.uci.ics.jung.io.graphml.NodeMetadata;

public class StateTransparentToMetadataTransformer <S extends State, T extends LabelledTransition, TFactory extends LabelledTransitionFactoryInterface<T>, BA extends IBAImpl<S, T, TFactory>> implements Transformer<S, String> {

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
