package it.polimi.chia.scalability.results;

import it.polimi.checker.SatisfactionValue;
import it.polimi.chia.scalability.configuration.Configuration;

import com.google.common.base.Preconditions;

public class Record {

	private final Configuration configuration;
	private final SatisfactionValue initialSatisfactioValue;
	private final SatisfactionValue finalSatisfactioValue;
	private final int sizeOfTheRefinementVerification;
	private final int sizeOfTheReplacementVerification; 
	private final long replacementVerificationTime;
	private final long refinementVerificationTime;
	private final boolean triviallySatisfied;


	public Record(Configuration configuration, SatisfactionValue initialSatisfactioValue) {
		this(configuration, initialSatisfactioValue, null, false, 0, 0, 0, 0);
	}
	
	public Record(Configuration configuration, SatisfactionValue initialSatisfactioValue, SatisfactionValue finalSatisfactioValue,  boolean triviallySatisfied,
			int sizeOfTheRefinementVerification, int sizeOfTheReplacementVerification, long replacementVerificationTime, long refinementVerificationTime){
		Preconditions.checkNotNull(configuration,
				"The condiguration under analysis cannot be null");
		Preconditions.checkNotNull(initialSatisfactioValue, "The initial satisfaction value cannot be null");
		Preconditions.checkArgument(finalSatisfactioValue!=null || initialSatisfactioValue!=SatisfactionValue.POSSIBLYSATISFIED, "The final satisfaction value cannot be null");
		Preconditions.checkArgument(this.sizeOfTheRefinementVerification>=0, "The size of the refinement verification must be >=0");
		Preconditions.checkArgument(this.sizeOfTheReplacementVerification>=0, "The size of the replacement verification must be >=0");
		Preconditions.checkArgument(this.refinementVerificationTime>=0, "The verification of the refinement must take a time >=0");
		Preconditions.checkArgument(this.sizeOfTheReplacementVerification>=0, "The verification of the replacement must take a time  >=0");
		this.configuration=configuration;
		this.initialSatisfactioValue=initialSatisfactioValue;
		this.finalSatisfactioValue=finalSatisfactioValue;
		this.sizeOfTheRefinementVerification=sizeOfTheRefinementVerification;
		this.sizeOfTheReplacementVerification=sizeOfTheReplacementVerification;
		this.replacementVerificationTime=replacementVerificationTime;
		this.refinementVerificationTime=refinementVerificationTime;
		this.triviallySatisfied=triviallySatisfied;
				
	}

	/**
	 * @return the configuration
	 */
	public Configuration getConfiguration() {
		return configuration;
	}

	/**
	 * @return the initialSatisfactioValue
	 */
	public SatisfactionValue getInitialSatisfactioValue() {
		return initialSatisfactioValue;
	}

	/**
	 * @return the finalSatisfactioValue
	 */
	public SatisfactionValue getFinalSatisfactioValue() {
		return finalSatisfactioValue;
	}

	/**
	 * @return the replacementVerificationTime
	 */
	public long getReplacementVerificationTime() {
		return replacementVerificationTime;
	}
	
	public int getSizeOfTheRefinementVerification() {
		return sizeOfTheRefinementVerification;
	}

	public int getSizeOfTheReplacementVerification() {
		return sizeOfTheReplacementVerification;
	}

	public long getRefinementVerificationTime() {
		return refinementVerificationTime;
	}

	/**
	 * @return the triviallySatisfied
	 */
	public boolean isTriviallySatisfied() {
		return triviallySatisfied;
	}

}
