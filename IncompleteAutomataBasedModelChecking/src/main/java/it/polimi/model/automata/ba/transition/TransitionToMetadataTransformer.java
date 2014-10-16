package it.polimi.model.automata.ba.transition;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.state.State;

import org.apache.commons.collections15.Transformer;

public class TransitionToMetadataTransformer<S extends State, T extends LabelledTransition,BA extends BuchiAutomaton<S, T>> implements Transformer<T, String>  {


	public TransitionToMetadataTransformer(){
	}
	
	@Override
	public String transform(T input) {
		
		return input.toString();
	}
	
	public static class TransitionIdToMetadataTransformer<T extends LabelledTransition> implements Transformer<T, String> {
		
		@Override
		public String transform(T input) {
			return Integer.toString(input.getId());
		}
	}
	public static class TransitionDNFFormulaToMetadataTransformer<T extends LabelledTransition> implements Transformer<T, String> {
		
		@Override
		public String transform(T input) {
			return input.getDnfFormula().toString();
		}
	}
}
