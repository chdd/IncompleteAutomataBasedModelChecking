package it.polimi.constraints.reachability;

import it.polimi.constraints.transitions.ColoredPluggingTransition;

public class ReachabilityEntry {

	@Override
	public String toString() {
		return "ReachabilityEntry [incomingTransition=" + incomingTransition
				+ ", outgoingTransition=" + outgoingTransition + ", accepting="
				+ accepting + ", claimAccepting=" + claimAccepting
				+ ", modelAccepting=" + modelAccepting + "]";
	}

	private final ColoredPluggingTransition incomingTransition;
	private final ColoredPluggingTransition outgoingTransition;
	private final boolean accepting;
	private final boolean claimAccepting;
	private final boolean modelAccepting;
	
	public ReachabilityEntry( ColoredPluggingTransition incomingTransition, ColoredPluggingTransition outgoingTransition, boolean accepting, boolean modelAccepting, boolean claimAccepting){
		this.incomingTransition=incomingTransition;
		this.outgoingTransition=outgoingTransition;
		this.accepting=accepting;
		this.claimAccepting=claimAccepting;
		this.modelAccepting=modelAccepting;
		
	}

	/**
	 * @return the incomingTransition
	 */
	public ColoredPluggingTransition getIncomingTransition() {
		return incomingTransition;
	}

	/**
	 * @return the accepting
	 */
	public boolean isAccepting() {
		return accepting;
	}

	/**
	 * @return the claimAccepting
	 */
	public boolean isClaimAccepting() {
		return claimAccepting;
	}

	/**
	 * @return the outgoingTransition
	 */
	public ColoredPluggingTransition getOutgoingTransition() {
		return outgoingTransition;
	}

	/**
	 * @return the modelAccepting
	 */
	public boolean isModelAccepting() {
		return modelAccepting;
	}
	
}
