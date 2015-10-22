package it.polimi.automata.io.out.propositions;

import it.polimi.automata.AutomataIOConstants;
import it.polimi.automata.io.Transformer;

import java.util.Set;

import com.google.common.base.Preconditions;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.SigmaProposition;

/**
 * transforms a set of propositions into a corresponding String
 * 
 * @author Claudio Menghi
 *
 */
public class IGraphPropositionsToStringTransformer implements
		Transformer<Set<IGraphProposition>, String> {

	/**
	 * Transforms a set of proposition into a corresponding propositional
	 * formula stored into a string
	 * 
	 * @param input
	 *            the set of propositions to be transformed
	 * @throws NullPointerException
	 *             if the set of propositions is null
	 */
	@Override
	public String transform(Set<IGraphProposition> input) {
		Preconditions.checkNotNull(input,
				"The set of the atomic propositions cannot be null");

		String ret = "";
		for (IGraphProposition label : input) {
			if (label instanceof SigmaProposition) {
				ret = ret + AutomataIOConstants.SIGMA;
			} else {
				ret = ret + label.toString()
						+ AutomataIOConstants.AND_NOT_ESCAPED;
			}
		}
		if (ret.endsWith(AutomataIOConstants.AND_NOT_ESCAPED)) {
			ret = ret.substring(0, ret.length()
					- AutomataIOConstants.AND_NOT_ESCAPED.length());
		}
		return ret;
	}

}
