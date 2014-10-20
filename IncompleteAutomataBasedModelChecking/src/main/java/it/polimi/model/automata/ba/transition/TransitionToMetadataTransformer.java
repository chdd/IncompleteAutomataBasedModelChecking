package it.polimi.model.automata.ba.transition;

import it.polimi.model.impl.automata.BAImpl;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactoryInterface;

import org.apache.commons.collections15.Transformer;

public class TransitionToMetadataTransformer<S extends State, T extends LabelledTransition, TFactory extends LabelledTransitionFactoryInterface<T> ,BA extends BAImpl<S, T, TFactory>> implements Transformer<T, String>  {


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
