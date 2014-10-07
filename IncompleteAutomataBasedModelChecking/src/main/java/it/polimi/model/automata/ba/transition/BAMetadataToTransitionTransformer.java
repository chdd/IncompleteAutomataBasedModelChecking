package it.polimi.model.automata.ba.transition;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.state.State;

import java.util.Arrays;
import java.util.HashSet;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.graphml.EdgeMetadata;

public class BAMetadataToTransitionTransformer<BA extends BuchiAutomaton<State, LabelledTransition>> implements Transformer<EdgeMetadata, LabelledTransition>{

	protected BA ba;
	public BAMetadataToTransitionTransformer(BA ba){
		this.ba=ba;
	}

	
	@Override
	public LabelledTransition transform(EdgeMetadata input) {
		
		 ba.addCharacters(new HashSet<String>(Arrays.asList(input.getProperty("labels").split(","))));
		 LabelledTransition e = new LabelledTransition(new HashSet<String>(Arrays.asList(input.getProperty("labels").split(","))), Integer.parseInt(input.getId()));
         return e;
	}

}
