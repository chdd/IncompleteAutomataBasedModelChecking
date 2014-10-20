package it.polimi.model.elements.states;

import it.polimi.model.impl.automata.BAImpl;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactoryInterface;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.graphml.DataMetadata;
import edu.uci.ics.jung.io.graphml.NodeMetadata;

public class BAStateAcceptingToMetadataTransformer<S extends State, T extends LabelledTransition, TFactory extends LabelledTransitionFactoryInterface<T>, BA extends BAImpl<S, T, TFactory>> implements Transformer<S, String>  {

	private BA ba;
	public BAStateAcceptingToMetadataTransformer(BA ba){
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
