package it.polimi.model.automata.ba.transition;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.state.State;

import org.apache.commons.collections15.Transformer;

public class TransitionToMetadataTransformer<S extends State, T extends LabelledTransition,BA extends BuchiAutomaton<S, T>> implements Transformer<T, String>  {


	public TransitionToMetadataTransformer(){
	}
	
	@Override
	public String transform(T input) {
		
		
		String labels="";
		boolean first=true;
		for(String character: input.getCharacter()){
			if(first){
				labels=character;
				first=false;
			}
			else{
				labels+=","+character;
			}
		}
		return labels;
	}
}
