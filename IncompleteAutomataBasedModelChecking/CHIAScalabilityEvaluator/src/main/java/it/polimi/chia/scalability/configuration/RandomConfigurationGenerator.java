package it.polimi.chia.scalability.configuration;

import java.util.Iterator;

public class RandomConfigurationGenerator implements
		Iterator<RandomConfiguration> {

	// N STATES
	protected static final int INIT_NSTATES = 10;
	protected static final int FINAL_NSTATES = 100;
	protected static final int INCREMENT_NSTATES = 10;

	// TRANSITIONS DENSITIES
	protected static final double INIT_TRANSITION_DENSITY = 1.0;
	protected static final double FINAL_TRANSITION_DENSITY = 4.0;
	protected static final double INCREMENT_TRANSITION_DENSITY = 1.0;

	// ACCEPTING DENSITIES
	protected static final double INIT_ACCEPTING_DENSITY = 0.2;
	protected static final double FINAL_ACCEPTING_DENSITY = 1.0;
	protected static final double INCREMENT_ACCEPTING_DENSITY = 0.2;

	// TRANSPARENT DENSITIES
	protected static final double INIT_TRANSPARENT_DENSITY = 10;
	protected static final double FINAL_TRANSPARENT_DENSITY = 100;
	protected static final double INCREMENT_TRANSPARENT_DENSITY = 10;

	// REPLACEMENT DENSITIES
	protected static final double INIT_REPLACEMENT_DENSITY = 0.1;
	protected static final double FINAL_REPLACEMENT_DENSITY = 0.5;
	protected static final double INCREMENT_REPLACEMENT_DENSITY = 0.1;

	private int configurationNumber;
	private int totalCondigurations;

	private int currentNumberOfStates;
	private double transitionDensity;
	private double acceptingDensity;
	private double transparentDensity;
	private double replacementDensity;

	public RandomConfigurationGenerator() {
		this.totalCondigurations = this.getNumberOfPossibleConfigurations();
		this.configurationNumber = 0;
		this.currentNumberOfStates = INIT_NSTATES;
		this.transitionDensity = INIT_TRANSITION_DENSITY;
		this.acceptingDensity = INIT_ACCEPTING_DENSITY;
		this.transparentDensity = INIT_TRANSPARENT_DENSITY;
		this.replacementDensity = INIT_REPLACEMENT_DENSITY;
	}

	@Override
	public boolean hasNext() {
		return configurationNumber < totalCondigurations;
	}

	@Override
	public RandomConfiguration next() {

		RandomConfiguration testConfiguration = new RandomConfiguration(
				currentNumberOfStates, transitionDensity, acceptingDensity,
				transparentDensity, replacementDensity);
		currentNumberOfStates = currentNumberOfStates + INCREMENT_NSTATES;

		this.configurationNumber++;
		if (this.hasNext()) {
			if (currentNumberOfStates > FINAL_NSTATES) {
				currentNumberOfStates = INIT_NSTATES;
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
								throw new InternalError(
										"The number of configurations generated is too high");
							}
						}
					}
				}
			}
		}

		return testConfiguration;

	}

	private double roundDouble(double number){
		return Math.round( number * 100.0 ) / 100.0;
	}

	public int getNumberOfPossibleConfigurations() {

		return this.numberStatesConfigurations() * this.transitionDensity()
				* this.numberAcceptingStatesConfigurations()
				* this.transparentStatesConfigurations()
				* this.replacementStatesConfigurations();

	}

	private int numberStatesConfigurations() {
		return ((FINAL_NSTATES - INIT_NSTATES) / INCREMENT_NSTATES + 1);
	}

	private int transitionDensity() {
		return (int) ((FINAL_TRANSITION_DENSITY - INIT_TRANSITION_DENSITY)
				/ INCREMENT_TRANSITION_DENSITY + 1);
	}

	private int numberAcceptingStatesConfigurations() {
		return (int) ((FINAL_ACCEPTING_DENSITY - INIT_ACCEPTING_DENSITY)
				/ INCREMENT_ACCEPTING_DENSITY + 1);
	}

	private int transparentStatesConfigurations() {
		return (int) ((FINAL_TRANSPARENT_DENSITY - INIT_TRANSPARENT_DENSITY)
				/ INCREMENT_TRANSPARENT_DENSITY + 1);
	}

	private int replacementStatesConfigurations() {
		return (int) ((FINAL_REPLACEMENT_DENSITY - INIT_REPLACEMENT_DENSITY)
				/ INCREMENT_REPLACEMENT_DENSITY + 1);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
