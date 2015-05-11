package it.polimi.contraintcomputation.subpropertyidentifier.coloring;

import it.polimi.contraintcomputation.subpropertyidentifier.SubPropertiesIdentifier;

import com.google.common.base.Preconditions;

public class Coloring {
	
	private SubPropertiesIdentifier sbpId;
	
	public Coloring(SubPropertiesIdentifier sbpId){
		Preconditions.checkNotNull(sbpId, "The subproperty identified cannot be null");
		this.sbpId=sbpId;
	}
	
	
	public void startColoring(){
		new GreenIncomingSearch(sbpId).startSearch();
		new RedOutgoingSearch(sbpId).startSearch();
	}
}
