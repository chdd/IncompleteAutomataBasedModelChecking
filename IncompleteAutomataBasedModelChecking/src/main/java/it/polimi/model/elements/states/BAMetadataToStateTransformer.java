package it.polimi.model.elements.states;

import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.impl.automata.BAImpl;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.graphml.NodeMetadata;


/**
 * @author claudiomenghi
 * contains the transformer that given the metadata of a State returns the state
 * @param <BA> is the BuchiAutomaton of the state
 */
public class BAMetadataToStateTransformer<BA extends BAImpl<State, LabelledTransition>> implements Transformer<NodeMetadata, State> {
	
	protected BA ba;
	public BAMetadataToStateTransformer(BA ba){
		this.ba=ba;
	}
	
	@Override
	public State transform(NodeMetadata input) {
		
		State s=new State(input.getProperty("name"), Integer.parseInt(input.getId()));
		ba.addVertex(s);
			
		if(Boolean.parseBoolean(input.getProperty("initial"))==true){
			ba.addInitialState(s);
		}
		if(Boolean.parseBoolean(input.getProperty("accepting"))==true){
			ba.addAcceptState(s);
		}
		return s;
	}

}
