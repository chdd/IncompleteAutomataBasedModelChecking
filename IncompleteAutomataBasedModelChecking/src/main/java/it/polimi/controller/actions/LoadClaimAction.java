package it.polimi.controller.actions;

import it.polimi.model.ModelInterface;

public class LoadClaimAction implements ActionInterface {

	private String claim;
	
	public LoadClaimAction(String claim){
		this.claim=claim;
	}
	
	@Override
	public void perform(ModelInterface model) throws Exception {
		if(this.claim==null){
			throw new NullPointerException("The LTL formula be converted cannot be empty");
		}
		model.loadClaim(this.claim);	
		
		
	}
}
