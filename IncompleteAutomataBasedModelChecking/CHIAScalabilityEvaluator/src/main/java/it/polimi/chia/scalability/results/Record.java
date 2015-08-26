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
	private final int numReplacementStates;
	private final int numReplacementIncomingTransitions;
	private final int numReplacementOutgoingTransitions;
	private final int subPropertyStates;
	private final int subPropertyGreenIncomingTransitions;
	private final int subPropertyYellowIncomingTransitions;
	private final int subPropertyNumIncomingTransitions;
	private final int subPropertyRedOutgoingTransitions;
	private final int subPropertyYellowOutgingTransition;
	private final int subPropertyNumOutgoingTransition;
	private final int sizeModel;
	private final int numTransparentStatesModel;


	public Record(Configuration configuration, SatisfactionValue initialSatisfactioValue) {
		this(configuration, initialSatisfactioValue, null, false, 0, 0, 0, 0, 0, 0,0,0,0,0,0,0,0,0,0,0);
	}
	
	public Record(Configuration configuration, SatisfactionValue initialSatisfactioValue, SatisfactionValue finalSatisfactioValue,  boolean triviallySatisfied,
			int sizeOfTheRefinementVerification, int sizeOfTheReplacementVerification, 
			long refinementVerificationTime,
			long replacementVerificationTime, 
			int numReplacementStates,
	int numReplacementIncomingTransitions,
	 int numReplacementOutgoingTransitions,
	int subPropertyStates,
	 int subPropertyGreenIncomingTransitions,
	 int subPropertyYellowIncomingTransitions,
	 int subPropertyNumIncomingTransitions,
	int subPropertyRedOutgoingTransitions,
	int subPropertyYellowOutgingTransition,
	 int subPropertyNumOutgoingTransition,
	 int sizeModel,
	 int numTransparentStatesModel){
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
		this.numReplacementStates=numReplacementStates;
		this.numReplacementIncomingTransitions=numReplacementIncomingTransitions;
		this.numReplacementOutgoingTransitions=numReplacementOutgoingTransitions;
		this.subPropertyStates=subPropertyStates;
		
		if(!(subPropertyGreenIncomingTransitions+subPropertyYellowIncomingTransitions<=subPropertyNumIncomingTransitions)){
			throw new InternalError("The sum of the green and yellow incoming transition cannot exceed the number of incoming transitions");
		}
		this.subPropertyGreenIncomingTransitions=subPropertyGreenIncomingTransitions;
		this.subPropertyYellowIncomingTransitions=subPropertyYellowIncomingTransitions;
		this.subPropertyNumIncomingTransitions=subPropertyNumIncomingTransitions;
		
		if(!(subPropertyRedOutgoingTransitions+subPropertyYellowOutgingTransition<=subPropertyNumOutgoingTransition)){
			throw new InternalError("The sum of the red and yellow outgoing transition cannot exceed the number of outgoing transitions");
		}
		this.subPropertyRedOutgoingTransitions=subPropertyRedOutgoingTransitions;
		this.subPropertyYellowOutgingTransition=subPropertyYellowOutgingTransition;
		this.subPropertyNumOutgoingTransition=subPropertyNumOutgoingTransition;
		this.sizeModel=sizeModel;
		this.numTransparentStatesModel=numTransparentStatesModel;
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

	/**
	 * @return the numReplacementStates
	 */
	public int getReplacementAutomataSize() {
		return numReplacementStates;
	}

	/**
	 * @return the numReplacementIncomingTransitions
	 */
	public int getReplacementIncomingTransitions() {
		return numReplacementIncomingTransitions;
	}

	/**
	 * @return the subPropertyStates
	 */
	public int getSubPropertyAutomataSize() {
		return subPropertyStates;
	}

	/**
	 * @return the subPropertyYellowIncomingTransitions
	 */
	public int getSubPropertyYellowIncomingTransitions() {
		return subPropertyYellowIncomingTransitions;
	}

	/**
	 * @return the numReplacementOutgoingTransitions
	 */
	public int getReplacementOutgoingTransitions() {
		return numReplacementOutgoingTransitions;
	}

	/**
	 * @return the subPropertyGreenIncomingTransitions
	 */
	public int getSubPropertyGreenIncomingTransitions() {
		return subPropertyGreenIncomingTransitions;
	}

	/**
	 * @return the subPropertyNumIncomingTransitions
	 */
	public int getSubPropertyIncomingTransitions() {
		return subPropertyNumIncomingTransitions;
	}

	/**
	 * @return the subPropertyRedOutgoingTransitions
	 */
	public int getSubPropertyRedOutgoingTransitions() {
		return subPropertyRedOutgoingTransitions;
	}

	/**
	 * @return the subPropertyYellowOutgingTransition
	 */
	public int getSubPropertyYellowOutgingTransition() {
		return subPropertyYellowOutgingTransition;
	}

	/**
	 * @return the subPropertyNumOutgoingTransition
	 */
	public int getSubPropertyOutgoingTransitions() {
		return subPropertyNumOutgoingTransition;
	}

	/**
	 * @return the sizeModel
	 */
	public int getSizeModel() {
		return sizeModel;
	}

	/**
	 * @return the numTransparentStatesModel
	 */
	public int getNumTransparentStatesModel() {
		return numTransparentStatesModel;
	}

}
