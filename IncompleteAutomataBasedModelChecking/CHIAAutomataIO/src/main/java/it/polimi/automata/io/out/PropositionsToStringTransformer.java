package it.polimi.automata.io.out;

import it.polimi.automata.AutomataIOConstants;

import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.SigmaProposition;

public class PropositionsToStringTransformer {

	public String transform(Set<IGraphProposition> propositions){
		String ret = "";
		for (IGraphProposition label : propositions) {
			if (label instanceof SigmaProposition) {
				ret = ret + AutomataIOConstants.SIGMA;
			} else {
				ret = ret + label.toString()
						+ AutomataIOConstants.AND_NOT_ESCAPED;
			}
		}
		if (ret.endsWith(AutomataIOConstants.AND_NOT_ESCAPED)) {
			ret = ret.substring(0,
					ret.length() - AutomataIOConstants.AND_NOT_ESCAPED.length());
		}
		return ret;
	}
}
