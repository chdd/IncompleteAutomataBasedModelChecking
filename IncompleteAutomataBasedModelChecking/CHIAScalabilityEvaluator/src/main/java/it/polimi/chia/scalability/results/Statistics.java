package it.polimi.chia.scalability.results;

public class Statistics {

	private int numPossibly;
	private int numSat;
	private int numUnsat;
	private int repIsMoreEfficientSpace;
	private int repIsMoreEfficientTime;

	public Statistics() {
		this.numPossibly = 0;
		this.numSat = 0;
		this.numUnsat = 0;
		this.repIsMoreEfficientSpace = 0;
		this.repIsMoreEfficientTime = 0;
	}

	public void incNumPossibly() {
		this.numPossibly = this.numPossibly + 1;
	}

	public void incNumSat() {
		this.numSat = this.numSat + 1;
	}

	public void incNumUnsat() {
		this.numUnsat = this.numUnsat + 1;
	}

	public void incRepIsMoreEfficientSpace() {
		this.repIsMoreEfficientSpace = this.repIsMoreEfficientSpace + 1;
	}

	public void incRepIsMoreEfficientTime() {
		this.repIsMoreEfficientTime = this.repIsMoreEfficientTime + 1;
	}

	@Override
	public String toString() {
		double totale = numPossibly + numSat + numUnsat;

		return "Statistics [totalTests="
				+ totale
				+ ", % possibly="
				+ (numPossibly / totale * 100)
				+ ", % sat="
				+ (numSat / totale * 100)
				+ ", % unsat="
				+ (numUnsat / totale * 100)
				+ ", % more efficient Space="
				+ (repIsMoreEfficientSpace / ((double) this.numPossibly) * 100)
				+ ", % more efficient Time="
				+ (this.repIsMoreEfficientTime / ((double) this.numPossibly) * 100)
				+ "]";
	}

}
