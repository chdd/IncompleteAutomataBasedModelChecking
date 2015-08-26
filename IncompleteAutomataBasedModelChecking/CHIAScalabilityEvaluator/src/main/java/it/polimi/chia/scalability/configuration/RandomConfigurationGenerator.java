package it.polimi.chia.scalability.configuration;

import it.polimi.automata.BA;

import java.util.Iterator;
import java.util.List;

import com.google.common.base.Preconditions;

public class RandomConfigurationGenerator implements Iterator<Configuration> {

	// N STATES
	// protected static final int INIT_NSTATES = 10;
	// protected static final int FINAL_NSTATES = 100;
	// protected static final int INCREMENT_NSTATES = 10;
	protected static final int INIT_NSTATES = 10;
	protected static final int FINAL_NSTATES = 100;
	protected static final int INCREMENT_NSTATES = 10;

	
	// TRANSITIONS DENSITIES
	protected static final double INIT_TRANSITION_DENSITY = 1.0;
	protected static final double FINAL_TRANSITION_DENSITY = 4.0;
	protected static final double INCREMENT_TRANSITION_DENSITY = 1.0;

	// ACCEPTING DENSITIES
	protected static final double INIT_ACCEPTING_DENSITY = 0.2;
	protected static final double FINAL_ACCEPTING_DENSITY = 0.5;
	protected static final double INCREMENT_ACCEPTING_DENSITY = 0.1;

	// TRANSPARENT DENSITIES
	protected static final double INIT_TRANSPARENT_DENSITY = 0.1;
	protected static final double FINAL_TRANSPARENT_DENSITY = 0.5;
	protected static final double INCREMENT_TRANSPARENT_DENSITY = 0.1;

	// REPLACEMENT DENSITIES
	protected static final double INIT_REPLACEMENT_DENSITY = 0.1;
	protected static final double FINAL_REPLACEMENT_DENSITY = 0.5;
	protected static final double INCREMENT_REPLACEMENT_DENSITY = 0.1;

	protected static final int N_TESTS = 20;
	private int configurationNumber;
	private int totalCondigurations;
	private int currentClaim;

	private int currentNumberOfStates;
	private double transitionDensity;
	private double acceptingDensity;
	private double transparentDensity;
	private double replacementDensity;
	private int testNumber;

	protected List<BA> claims;

	public RandomConfigurationGenerator(List<BA> claims) {
		Preconditions.checkNotNull(claims,
				"The list of the claims cannot be null");
		Preconditions.checkArgument(claims.size() >= 1,
				"There must be at least a claim in the list");
		this.claims = claims;

		this.testNumber = 1;
		this.configurationNumber = 1;
		this.currentNumberOfStates = INIT_NSTATES;
		this.transitionDensity = INIT_TRANSITION_DENSITY;
		this.acceptingDensity = INIT_ACCEPTING_DENSITY;
		this.transparentDensity = INIT_TRANSPARENT_DENSITY;
		this.replacementDensity = INIT_REPLACEMENT_DENSITY;
		this.currentClaim=0;

		this.totalCondigurations = this.getNumberOfPossibleConfigurations();

	}

	@Override
	public boolean hasNext() {
		return configurationNumber <= totalCondigurations;
	}


	@Override
	public Configuration next() {

		Configuration testConfiguration = new Configuration(
				configurationNumber, currentNumberOfStates, transitionDensity,
				acceptingDensity, transparentDensity, replacementDensity,
				this.claims.get(currentClaim), testNumber, currentClaim);

		this.configurationNumber++;
		if (this.hasNext()) {

			testNumber++;
			if (testNumber > N_TESTS) {
				testNumber = 1;
				currentClaim++;
				if(currentClaim>=claims.size()){
					currentClaim=0;

					this.transitionDensity = roundDouble(transitionDensity
							+ INCREMENT_TRANSITION_DENSITY);

					if (this.transitionDensity > FINAL_TRANSITION_DENSITY) {
						this.transitionDensity = INIT_TRANSITION_DENSITY;
						this.acceptingDensity = roundDouble(this.acceptingDensity
								+ INCREMENT_ACCEPTING_DENSITY);
						if (this.acceptingDensity > FINAL_ACCEPTING_DENSITY) {
							this.acceptingDensity = INIT_ACCEPTING_DENSITY;
							this.transparentDensity = roundDouble(this.transparentDensity
									+ INCREMENT_TRANSPARENT_DENSITY);
							if (this.transparentDensity > FINAL_TRANSPARENT_DENSITY) {
								this.transparentDensity = INIT_TRANSPARENT_DENSITY;
								this.replacementDensity = roundDouble(this.replacementDensity
										+ INCREMENT_REPLACEMENT_DENSITY);
								if (this.replacementDensity > FINAL_REPLACEMENT_DENSITY) {
									this.replacementDensity = INIT_REPLACEMENT_DENSITY;
									currentNumberOfStates = currentNumberOfStates
											+ INCREMENT_NSTATES;

									if (currentNumberOfStates > FINAL_NSTATES) {
										System.out.println(testNumber + "\t"
												+ transitionDensity + "\t"
												+ acceptingDensity + "\t"
												+ transparentDensity + "\t"
												+ replacementDensity + "\t"
												+ currentNumberOfStates + "\t");
										throw new InternalError(
												"The number of configurations generated is too high");

									}

								}
							}
						}
					}
				}
			}

		}

		return testConfiguration;

	}

	private double roundDouble(double number) {
		return Math.abs((number * 100.0) / 100.0);
	}

	public int getNumberOfPossibleConfigurations() {

		return N_TESTS 
				* this.claims.size() 
				* this.numberStatesConfigurations()
				* this.transitionDensity()
				* this.numberAcceptingStatesConfigurations()
				* this.transparentStatesConfigurations()
				* this.replacementStatesConfigurations();

	}

	private int numberStatesConfigurations() {
		return ((FINAL_NSTATES - INIT_NSTATES + INCREMENT_NSTATES) / INCREMENT_NSTATES);
	}

	private int transitionDensity() {
		return (int) ((FINAL_TRANSITION_DENSITY - INIT_TRANSITION_DENSITY + INCREMENT_TRANSITION_DENSITY) / INCREMENT_TRANSITION_DENSITY);
	}

	private int numberAcceptingStatesConfigurations() {
		return (int) ((FINAL_ACCEPTING_DENSITY - INIT_ACCEPTING_DENSITY + INCREMENT_ACCEPTING_DENSITY) / INCREMENT_ACCEPTING_DENSITY);
	}

	private int transparentStatesConfigurations() {
		return (int) ((FINAL_TRANSPARENT_DENSITY - INIT_TRANSPARENT_DENSITY + INCREMENT_TRANSPARENT_DENSITY) / INCREMENT_TRANSPARENT_DENSITY);
	}

	private int replacementStatesConfigurations() {
		return (int) ((FINAL_REPLACEMENT_DENSITY - INIT_REPLACEMENT_DENSITY + INCREMENT_REPLACEMENT_DENSITY) / INCREMENT_REPLACEMENT_DENSITY);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return "RandomConfigurationGenerator [configurationNumber=" + configurationNumber
				+ ", totalCondigurations=" + totalCondigurations
				+ ", currentNumberOfStates=" + currentNumberOfStates
				+ ", transitionDensity=" + transitionDensity
				+ ", acceptingDensity=" + acceptingDensity
				+ ", transparentDensity=" + transparentDensity
				+ ", replacementDensity=" + replacementDensity
				+ ", testNumber=" + testNumber + ", claim="
				+ currentClaim + "]";
	}

	

}
