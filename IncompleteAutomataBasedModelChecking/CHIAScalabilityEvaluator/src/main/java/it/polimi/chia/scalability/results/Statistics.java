package it.polimi.chia.scalability.results;

public class Statistics {
	
	private int numPossibly;
	private int numSat;
	private int numUnsat;
	private int repIsMoreEfficient;
	
	public Statistics(){
		this.numPossibly=0;
		this.numSat=0;
		this.numUnsat=0;
		this.repIsMoreEfficient=0;
	}
	
	public void incNumPossibly(){
		this.numPossibly=this.numPossibly+1;
	}
	
	public void incNumSat(){
		this.numSat=this.numSat+1;
	}
	public void incNumUnsat(){
		this.numUnsat=this.numUnsat+1;
	}
	
	public void incRepIsMoreEfficient(){
		this.repIsMoreEfficient=this.repIsMoreEfficient+1;
	}
	@Override
	public String toString() {
		double totale=numPossibly+numSat+numUnsat;
		
		return "Statistics [totalTests=" + totale + 
				", % possibly=" + (numPossibly/totale*100) +
				", % sat=" + (numSat/totale*100) +
				", % unsat=" + (numUnsat/totale*100) +
				", % more efficient=" + (repIsMoreEfficient/((double) this.numPossibly)*100) +"]";
	}
	
}
