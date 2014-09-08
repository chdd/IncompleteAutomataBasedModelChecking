package it.polimi.modelchecker;

/**
 * @author Claudio Menghi
 * contains the report of the model checking parameters
 */
public class ModelCheckerParameters {
	

	/**
	 * contains the result of the verification
	 */
	private int result;
	
	/**
	 * contains the time that is necessary to build the intersection of the two automata
	 */
	private double intersectionTime;
	/**
	 * contains the time that is necessary to check that the intersection automaton is empty
	 */
	private double emptyTime;
	/**
	 * contains the time that is necessary to compute the constraints
	 */
	private double constraintComputationTime;
	
	// specification
	/**
	 * contains the number of the states of the specification
	 */
	private int numStatesSpecification;
	/**
	 * contains the number of accepting states of the specification
	 */
	private int numAcceptStatesSpecification;
	/**
	 * contains the number of the transitions of the specification
	 */
	private int numTransitionSpecification;
	
	private double totalTime;
	
	// model
	/**
	 * contains the number of the states of the model
	 */
	private int numStatesModel;
	/**
	 * contains the number of accepting states of the model
	 */
	private int numAcceptStatesModel;
	/**
	 * contains the number of the transitions of the model
	 */
	private int numTransitionModel;
	/**
	 * contains the number of the transparent states of the model
	 */
	private int numTransparentStatesModel;
	
	// Intersection
	/**
	 * contains the number of the states of the intersection
	 */
	private int numStatesIntersection;
	/**
	 * contains the number of the accepting states of the intersection
	 */
	private int numAcceptingStatesIntersection;
	/**
	 * contains the number of the initial states of the intersection
	 */
	private int numInitialStatesIntersection;
	/**
	 * contains the number of the mixed states of the intersection
	 */
	private int numMixedStatesIntersection;
	
	
	public ModelCheckerParameters(){
		this.setResult(0);
		this.setIntersectionTime(0);
		this.setEmptyTime(0);
		this.setConstraintComputationTime(0);
		this.setNumStatesSpecification(0);
		this.setNumAcceptStatesSpecification(0);
		this.setNumTransitionSpecification(0);
		this.setNumStatesModel(0);
		this.setNumAcceptStatesModel(0);
		this.setNumTransitionModel(0);
		this.setNumTransparentStatesModel(0);
		this.setNumStatesIntersection(0);
		this.setNumAcceptingStatesIntersection(0);
		this.setNumInitialStatesIntersection(0);
		this.setNumMixedStatesIntersection(0);
		this.setTotalTime(0);
	}
	
	public void reset(){
		this.setResult(0);
		this.setIntersectionTime(0);
		this.setEmptyTime(0);
		this.setConstraintComputationTime(0);
		this.setNumStatesSpecification(0);
		this.setNumAcceptStatesSpecification(0);
		this.setNumTransitionSpecification(0);
		this.setNumStatesModel(0);
		this.setNumAcceptStatesModel(0);
		this.setNumTransitionModel(0);
		this.setNumTransparentStatesModel(0);
		this.setNumStatesIntersection(0);
		this.setNumAcceptingStatesIntersection(0);
		this.setNumInitialStatesIntersection(0);
		this.setNumMixedStatesIntersection(0);
	}

	/**
	 * @return the result
	 */
	public int getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(int result) {
		this.result = result;
	}

	/**
	 * @return the intersectionTime
	 */
	public double getIntersectionTime() {
		return intersectionTime;
	}

	/**
	 * @param intersectionTime the intersectionTime to set
	 */
	public void setIntersectionTime(double intersectionTime) {
		this.intersectionTime = intersectionTime;
	}

	/**
	 * @return the emptyTime
	 */
	public double getEmptyTime() {
		return emptyTime;
	}

	/**
	 * @param emptyTime the emptyTime to set
	 */
	public void setEmptyTime(double emptyTime) {
		this.emptyTime = emptyTime;
	}

	/**
	 * @return the constraintComputationTime
	 */
	public double getConstraintComputationTime() {
		return constraintComputationTime;
	}

	/**
	 * @param constraintComputationTime the constraintComputationTime to set
	 */
	public void setConstraintComputationTime(double constraintComputationTime) {
		this.constraintComputationTime = constraintComputationTime;
	}

	/**
	 * @return the numStatesSpecification
	 */
	public int getNumStatesSpecification() {
		return numStatesSpecification;
	}

	/**
	 * @param numStatesSpecification the numStatesSpecification to set
	 */
	public void setNumStatesSpecification(int numStatesSpecification) {
		this.numStatesSpecification = numStatesSpecification;
	}

	/**
	 * @return the numAcceptStatesSpecification
	 */
	public int getNumAcceptStatesSpecification() {
		return numAcceptStatesSpecification;
	}

	/**
	 * @param numAcceptStatesSpecification the numAcceptStatesSpecification to set
	 */
	public void setNumAcceptStatesSpecification(int numAcceptStatesSpecification) {
		this.numAcceptStatesSpecification = numAcceptStatesSpecification;
	}

	/**
	 * @return the numTransitionSpecification
	 */
	public int getNumTransitionSpecification() {
		return numTransitionSpecification;
	}

	/**
	 * @param numTransitionSpecification the numTransitionSpecification to set
	 */
	public void setNumTransitionSpecification(int numTransitionSpecification) {
		this.numTransitionSpecification = numTransitionSpecification;
	}

	/**
	 * @return the numStatesModel
	 */
	public int getNumStatesModel() {
		return numStatesModel;
	}

	/**
	 * @param numStatesModel the numStatesModel to set
	 */
	public void setNumStatesModel(int numStatesModel) {
		this.numStatesModel = numStatesModel;
	}

	/**
	 * @return the numAcceptStatesModel
	 */
	public int getNumAcceptStatesModel() {
		return numAcceptStatesModel;
	}

	/**
	 * @param numAcceptStatesModel the numAcceptStatesModel to set
	 */
	public void setNumAcceptStatesModel(int numAcceptStatesModel) {
		this.numAcceptStatesModel = numAcceptStatesModel;
	}

	/**
	 * @return the numTransitionModel
	 */
	public int getNumTransitionModel() {
		return numTransitionModel;
	}

	/**
	 * @param numTransitionModel the numTransitionModel to set
	 */
	public void setNumTransitionModel(int numTransitionModel) {
		this.numTransitionModel = numTransitionModel;
	}

	/**
	 * @return the numTransparentStatesModel
	 */
	public int getNumTransparentStatesModel() {
		return numTransparentStatesModel;
	}

	/**
	 * @param numTransparentStatesModel the numTransparentStatesModel to set
	 */
	public void setNumTransparentStatesModel(int numTransparentStatesModel) {
		this.numTransparentStatesModel = numTransparentStatesModel;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toStringHeader() {
		return "1)numStatesMode, 2)result, 3)intersectionTime, 4)emptyTime, 5)constraintComputationTime, 6)numStatesSpecification, "
				+ "7)numAcceptStatesSpecification, "
				+ "8)numTransitionSpecification, 9)numAcceptStatesModel, 10)numTransitionModel, 11)numTransparentStatesModel, "+
				"12)setNumStatesIntersection, 13)setNumAcceptingStatesIntersection, 14)setNumInitialStatesIntersection, 15)setNumMixedStatesIntersection, 16)TransparentStatesModel, 17)totalTime";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return numStatesModel+", "+result+", "+intersectionTime+", "+ emptyTime + ", "+ constraintComputationTime + ", "+ numStatesSpecification + ", "
				+ numAcceptStatesSpecification+ ", " 
				+ numTransitionSpecification+ ", " + numAcceptStatesModel
				+ ", " + numTransitionModel+ ", " + numTransparentStatesModel+", "+numStatesIntersection+", "+
				numAcceptingStatesIntersection+", "+numInitialStatesIntersection+", "+numMixedStatesIntersection+", "+numTransparentStatesModel+", "+this.getTotalTime();
	}

	
	/**
	 * @return the numStatesIntersection
	 */
	public int getNumStatesIntersection() {
		return numStatesIntersection;
	}

	/**
	 * @param numStatesIntersection the numStatesIntersection to set
	 */
	public void setNumStatesIntersection(int numStatesIntersection) {
		this.numStatesIntersection = numStatesIntersection;
	}

	/**
	 * @return the numAcceptingStatesIntersection
	 */
	public int getNumAcceptingStatesIntersection() {
		return numAcceptingStatesIntersection;
	}

	/**
	 * @param numAcceptingStatesIntersection the numAcceptingStatesIntersection to set
	 */
	public void setNumAcceptingStatesIntersection(
			int numAcceptingStatesIntersection) {
		this.numAcceptingStatesIntersection = numAcceptingStatesIntersection;
	}

	/**
	 * @return the numInitialStatesIntersection
	 */
	public int getNumInitialStatesIntersection() {
		return numInitialStatesIntersection;
	}

	/**
	 * @param numInitialStatesIntersection the numInitialStatesIntersection to set
	 */
	public void setNumInitialStatesIntersection(int numInitialStatesIntersection) {
		this.numInitialStatesIntersection = numInitialStatesIntersection;
	}

	/**
	 * @return the numMixedStatesIntersection
	 */
	public int getNumMixedStatesIntersection() {
		return numMixedStatesIntersection;
	}

	/**
	 * @param numMixedStatesIntersection the numMixedStatesIntersection to set
	 */
	public void setNumMixedStatesIntersection(int numMixedStatesIntersection) {
		this.numMixedStatesIntersection = numMixedStatesIntersection;
	}

	/**
	 * @return the totalTime
	 */
	public double getTotalTime() {
		return totalTime;
	}

	/**
	 * @param totalTime the totalTime to set
	 */
	public void setTotalTime(double totalTime) {
		this.totalTime = totalTime;
	}
}
