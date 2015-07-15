package it.polimi.replacementchecker;

import it.polimi.automata.IntersectionBA;
import it.polimi.automata.state.IntersectionStateFactory;
import it.polimi.checker.intersection.IntersectionTransitionBuilder;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;

import com.google.common.base.Preconditions;

public class OverApproximationBuilder {

	/**
	 * contains the replacement to be verified
	 */
	private final Replacement replacement;

	/**
	 * the sub-property to be considered
	 */
	private final SubProperty subproperty;

	private final AcceptingPolicy acceptingPolicy;
	
	private final ReplacementIntersectionBuilder replacementIntersectionBuilder;
	
	/**
	 * creates a new {@link UnderApproximationBuilder}. The over-approximation
	 * builder computes an over approximation of the intersection automaton
	 * between the replacement and the sub-property
	 * 
	 * @param replacement
	 *            the replacement to be considered
	 * @param subproperty
	 *            the sub-property under analysis
	 * @param acceptingPolicy
	 *            the accepting policy to be used in the computation of the
	 *            intersection
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 */
	public OverApproximationBuilder(Replacement replacement,
			SubProperty subproperty, AcceptingPolicy acceptingPolicy) {
		Preconditions.checkNotNull(replacement,
				"The replacement to be considered cannot be null");
		Preconditions.checkNotNull(subproperty,
				"The sub=property to be considered cannot be null");
		Preconditions.checkNotNull(acceptingPolicy,
				"The accepting policy to be considered cannot be null");

		this.replacement = replacement;
		this.subproperty = subproperty;
		this.acceptingPolicy = acceptingPolicy;
		replacementIntersectionBuilder=new ReplacementIntersectionBuilder(replacement, subproperty, new IntersectionStateFactory(), new IntersectionTransitionBuilder(), this.subproperty.getUpperReachabilityRelation(),false);
	}

	public IntersectionBA perform() {
		return replacementIntersectionBuilder.perform(); 
	}
	
	
}


