package it.polimi.model.automata.ba.transition;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.transition.labeling.DNFFormula;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.io.graphml.EdgeMetadata;

public class BAMetadataToTransitionTransformer<BA extends BuchiAutomaton<State, LabelledTransition>> implements Transformer<EdgeMetadata, LabelledTransition>{

	protected BA ba;
	public BAMetadataToTransitionTransformer(BA ba){
		this.ba=ba;
	}

	
	@Override
	public LabelledTransition transform(EdgeMetadata input) {
		
		return new LabelledTransition(DNFFormula.loadFromString(input.getProperty("DNFFormula")), Integer.parseInt(input.getProperty("id")));
	}

}
