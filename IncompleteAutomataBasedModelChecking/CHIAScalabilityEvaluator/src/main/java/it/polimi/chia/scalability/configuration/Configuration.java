package it.polimi.chia.scalability.configuration;

import it.polimi.automata.BA;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

import com.google.common.base.Preconditions;

/**
 * contains a configuration test. The configuration test is identified by: the
 * number of states of the BA, the density of the transitions of the BA, the
 * density of the accepting states of the BA, the density of the transparent
 * states of the IBA, the replacement density.
 * 
 * @author Claudio Menghi
 *
 */
public class Configuration {

	
	private final int testNumber;
	
	private final int claimNumber;
	/**
	 * is the identifier of the configuration
	 */
	private final int configurationId;
	/**
	 * the number of transitions of the BA
	 */
	private final double transitionDensity;
	/**
	 * the number of accepting states of the BA
	 */
	private final double acceptingDensity;
	/**
	 * the number of states of the BA
	 */
	private final int nStates;
	/**
	 * the density of the transparent states of the IBA
	 */
	private final double transparentDensity;
	/**
	 * the density of the states to be inserted into the replacement
	 */
	private final double replacementDensity;
	
	/**
	 * The set of propositions to be considered in the generation of the random
	 * automaton
	 */
	private final Set<IGraphProposition> propositions;

	private final BA currentClaim;

	/**
	 * creates a new test configuration
	 * 
	 * @param nStates
	 *            the number of the states of the BA to be considered
	 * @param transitionDensity
	 *            the density of the transitions of the BA
	 * @param acceptingDensity
	 *            the density of the accepting states of the BA
	 * @param transparentDensity
	 *            the density of the transparent states of the IBA
	 * @param replacementDensity
	 *            the density of the states to be inserted into the replacement
	 * @throws IllegalArgumentException
	 *             if the number of states, the density of the transitions, the
	 *             accepting or the transparent states or the replacement
	 *             density is less than zero
	 */
	public Configuration(int configurationId,int nStates, double transitionDensity,
			double acceptingDensity, double transparentDensity,
			double replacementDensity, BA currentClaim, int testNumber, int claimNumber) {
		Preconditions.checkArgument(claimNumber>=0, "The claim number: "+claimNumber+"must be grater or equal to 1");
		Preconditions.checkArgument(nStates >= 0,
				"The number of the states must be grather or equal to zero");
		Preconditions.checkArgument(transitionDensity >= 0,
				"The transition density must be grather or equal to zero");
		Preconditions
				.checkArgument(acceptingDensity >= 0,
						"The density of the accepting states must be grather or equal to zero");
		Preconditions
				.checkArgument(transparentDensity >= 0,
						"The density of the transparent states must be grather or equal to zero");
		Preconditions.checkArgument(replacementDensity >= 0,
				"The replacement density must be grather or equal to zero");

		this.configurationId=configurationId;
		this.propositions = new HashSet<IGraphProposition>();
		this.propositions.add(new GraphProposition("a", false));
		this.propositions.add(new GraphProposition("b", false));

		this.transitionDensity = transitionDensity;
		this.acceptingDensity = acceptingDensity;
		this.nStates = nStates;
		this.transparentDensity = transparentDensity;
		this.replacementDensity = replacementDensity;
		this.currentClaim=currentClaim;
		this.testNumber=testNumber;
		this.claimNumber=claimNumber;
	}

	/**
	 * @return the transitionDensity
	 */
	public double getTransitionDensity() {
		return transitionDensity;
	}

	/**
	 * @return the acceptingDensity
	 */
	public double getAcceptingDensity() {
		return acceptingDensity;
	}

	/**
	 * @return the nStates
	 */
	public int getnStates() {
		return nStates;
	}

	/**
	 * @return the replacementDensity
	 */
	public double getReplacementDensity() {
		return replacementDensity;
	}

	/**
	 * @return the transparentDensity
	 */
	public double getTransparentDensity() {
		return transparentDensity;
	}

	/**
	 * returns the set of the propositions to be used in the generation of the
	 * automaton
	 * 
	 * @return the set of the propositions to be used in the generation of the
	 * automaton
	 */
	public Set<IGraphProposition> getPropositions() {
		return Collections.unmodifiableSet(propositions);
	}

	

	@Override
	public String toString() {
		return "Configuration [testNumber=" + testNumber + ", claimNumber="
				+ claimNumber + ", configurationId=" + configurationId
				+ ", transitionDensity=" + transitionDensity
				+ ", acceptingDensity=" + acceptingDensity + ", nStates="
				+ nStates + ", transparentDensity=" + transparentDensity
				+ ", replacementDensity=" + replacementDensity
				+ ", propositions=" + propositions +"]";
	}

	/**
	 * @return the configurationId
	 */
	public int getConfigurationId() {
		return configurationId;
	}

	/**
	 * @return the currentClaim
	 */
	public BA getCurrentClaim() {
		return currentClaim;
	}

	/**
	 * @return the testNumber
	 */
	public int getTestNumber() {
		return testNumber;
	}

	/**
	 * @return the claimNumber
	 */
	public int getClaimNumber() {
		return claimNumber;
	}

}
