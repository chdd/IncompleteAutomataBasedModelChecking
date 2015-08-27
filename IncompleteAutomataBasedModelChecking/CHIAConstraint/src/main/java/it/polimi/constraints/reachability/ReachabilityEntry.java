package it.polimi.constraints.reachability;

import com.google.common.base.Preconditions;

import it.polimi.constraints.transitions.LabeledPluggingTransition;

/**
 * Contains a reachability entry. The reachability entry specifies the
 * reachability between an incoming and an outgoing transition of a
 * sub-property, i.e., if an incoming transition transition can be reached from
 * an outgoing transition in the intersection automaton
 * 
 * @author Claudio Menghi
 *
 */
public class ReachabilityEntry {

	/**
	 * is the outgoing transition
	 */
	private final LabeledPluggingTransition outgoingTransition;

	/**
	 * is the incoming transition reachable from the outgoing transition of the
	 * sub-property
	 */
	private final LabeledPluggingTransition incomingTransition;

	/**
	 * is true if there is an accepting state for the model in one of the runs
	 * that connect the incoming and outgoing transition
	 */
	private final boolean modelAccepting;

	/**
	 * is true if there is an accepting state for the claim in one of the runs
	 * that connect the incoming and outgoing transition
	 */
	private final boolean claimAccepting;

	/**
	 * creates a new reachability entry
	 * 
	 * @param incomingTransition
	 *            is the incoming transition reachable from the outgoing
	 *            transition of the sub-property
	 * @param outgoingTransition
	 *            is the outgoing transition
	 * @param modelAccepting
	 *            is true if there is an accepting state for the model in one of
	 *            the runs that connect the incoming and outgoing transition
	 * @param claimAccepting
	 *            is true if there is an accepting state for the claim in one of
	 *            the runs that connect the incoming and outgoing transition
	 * @throws NullPointerException
	 *             if the incoming or the outgoing transition is null
	 */
	public ReachabilityEntry(LabeledPluggingTransition incomingTransition,
			LabeledPluggingTransition outgoingTransition,
			boolean modelAccepting, boolean claimAccepting) {
		Preconditions.checkNotNull(incomingTransition,
				"The incoming transition cannot be null");
		Preconditions.checkNotNull(outgoingTransition,
				"The outgoing transition cannot be null");
		this.incomingTransition = incomingTransition;
		this.outgoingTransition = outgoingTransition;
		this.claimAccepting = claimAccepting;
		this.modelAccepting = modelAccepting;

	}

	/**
	 * @return the incomingTransition of the reachability entry
	 */
	public LabeledPluggingTransition getIncomingTransition() {
		return incomingTransition;
	}

	/**
	 * @return the outgoingTransition of the reachability entry
	 */
	public LabeledPluggingTransition getOutgoingTransition() {
		return outgoingTransition;
	}
	
	/**
	 * @return the true if there is an accepting state of the model in one of the runs that connect the outgoing and incoming transition of the sub-property
	 */
	public boolean isModelAccepting() {
		return modelAccepting;
	}

	/**
	 * @return the true if there is an accepting state of the claim in one of the runs that connect the outgoing and incoming transition of the sub-property
	 */
	public boolean isClaimAccepting() {
		return claimAccepting;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "ReachabilityEntry [incomingTransition=" + incomingTransition
				+ ", outgoingTransition=" + outgoingTransition
				+ ", claimAccepting=" + claimAccepting + ", modelAccepting="
				+ modelAccepting + "]";
	}
}
