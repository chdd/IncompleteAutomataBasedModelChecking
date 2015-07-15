package it.polimi.casestudies.utils;

import java.io.File;

public class Experiment {

	private final File claimFile;
	private final File modelFile;
	private final File resultsFile;
	private final boolean refinement;
	
	
	public Experiment( File claimFile, File modelFile,  File resultsFile, boolean refinement){
		this.claimFile=claimFile;
		this.modelFile=modelFile;
		this.resultsFile=resultsFile;
		this.refinement=refinement;
		
	}

	/**
	 * @return the claimFile
	 */
	public File getClaimFile() {
		return claimFile;
	}

	/**
	 * @return the modelFile
	 */
	public File getModelFile() {
		return modelFile;
	}

	/**
	 * @return the resultsFile
	 */
	public File getResultsFile() {
		return resultsFile;
	}

	/**
	 * @return the refinement
	 */
	public boolean isRefinement() {
		return refinement;
	}
}
