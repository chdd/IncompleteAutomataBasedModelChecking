package it.polimi.model.automata.ba.io.fromfile;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.LabelledTransition;
import it.polimi.model.automata.ba.state.State;

import java.util.Arrays;
import java.util.HashSet;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.graphml.EdgeMetadata;

public class BAMetadataToTransitionTransformer<BA extends BuchiAutomaton<State, LabelledTransition<State>>> implements Transformer<EdgeMetadata, LabelledTransition<State>>{

	protected BA ba;
	public BAMetadataToTransitionTransformer(BA ba){
		this.ba=ba;
	}

	
	@Override
	public LabelledTransition<State> transform(EdgeMetadata input) {
		
		 ba.addCharacters(new HashSet<String>(Arrays.asList(input.getProperty("labels").split(","))));
		 LabelledTransition<State> e = new LabelledTransition<State>(new HashSet<String>(Arrays.asList(input.getProperty("labels").split(","))), ba.getVertex(Integer.parseInt(input.getTarget())));
         return e;
	}

}
