package it.polimi.model.io.ba.tofile;

import it.polimi.model.automata.ba.state.State;

import org.apache.commons.collections15.Transformer;

public class StateToIdTransformer<S extends State> implements Transformer<S, String>  {

	@Override
	public String transform(S input) {
		return Integer.toString(input.getId());
	}
}
