package it.polimi.automata.io.out;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.Transformer;

import java.util.Set;

import com.google.common.base.Preconditions;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.SigmaProposition;

public class IGraphPropositionsToStringTransformer implements Transformer<Set<IGraphProposition>, String> {

	@Override
	public String transform(Set<IGraphProposition> input) {
		Preconditions.checkNotNull(input, "The set of the atomic propositions cannot be null");
		
		String ret = "";
		for (IGraphProposition label : input) {
			if (label instanceof SigmaProposition) {
				ret = ret + AutomataIOConstants.SIGMA;
			} else {
				ret = ret + label.toString() + AutomataIOConstants.AND_NOT_ESCAPED;
			}
		}
		if (ret.endsWith(AutomataIOConstants.AND_NOT_ESCAPED)) {
			ret = ret.substring(0,
					ret.length() - AutomataIOConstants.AND_NOT_ESCAPED.length());
		}
		return ret;
	}

}
