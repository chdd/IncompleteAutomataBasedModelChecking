package it.polimi.automata.io.in.propositions;

import it.polimi.automata.AutomataIOConstants;

import java.util.HashSet;
import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import rwth.i2.ltl2ba4j.model.impl.SigmaProposition;

import com.google.common.base.Preconditions;

public class StringToClaimPropositions {

	/**
	 * Starting from the string computes the corresponding proposition. The
	 * string must satisfy the regular expression {@link AutomataIOConstants#APREGEX} or
	 * the regular expression {@link AutomataIOConstants#NOTAPREGEX}
	 * 
	 * @param input
	 *            is the input to be computed
	 * @throws NullPointerException
	 *             if the input is null
	 * @throws IllegalArgumentException
	 *             if the input does not match the regular expression
	 *             {@link AutomataIOConstants#APREGEX} or the regular expression
	 *             {@link AutomataIOConstants#NOTAPREGEX}
	 */
	public Set<IGraphProposition> computePropositions(String input) {

		Preconditions.checkNotNull("The input must be not null");
		Preconditions.checkArgument(!input.matches(AutomataIOConstants.CLAIM_PROPOSITIONAL_FORMULA), "The input " + input
				+ " must match the regular expression: "
				+ AutomataIOConstants.CLAIM_PROPOSITIONAL_FORMULA);
		Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
		
		if (input.equals("("+AutomataIOConstants.SIGMA+")")) {
			propositions.add(new SigmaProposition());
		} else {
			String[] apsStrings = input.split(AutomataIOConstants.AND);

			for (String ap : apsStrings) {
				ap=ap.substring(1, ap.length()-1);
				if (ap.startsWith(AutomataIOConstants.NOT)) {
					propositions.add(new GraphProposition(ap
							.substring(AutomataIOConstants.NOT.length()), true));
				} else {
					propositions.add(new GraphProposition(ap, false));
				}
			}
		}

		return propositions;
	}
}