package it.polimi.chia.scalability.configuration;

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
public class RandomConfiguration {

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
	public RandomConfiguration(int nStates, double transitionDensity,
			double acceptingDensity, double transparentDensity,
			double replacementDensity) {
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
		
		this.transitionDensity = transitionDensity;
		this.acceptingDensity = acceptingDensity;
		this.nStates = nStates;
		this.transparentDensity = transparentDensity;
		this.replacementDensity = replacementDensity;
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

	@Override
	public String toString() {
		return "TestConfiguration [nStates=" + nStates + ", transitionDensity="
				+ transitionDensity + ", acceptingDensity=" + acceptingDensity
				+ ", transparentDensity=" + transparentDensity
				+ ", replacementDensity=" + replacementDensity + "]";
	}

}
