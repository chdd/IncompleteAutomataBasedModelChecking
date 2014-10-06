package it.polimi.controller.actions;

import it.polimi.model.ModelInterface;
import it.polimi.model.automata.ba.State;

public class IncompleteBuchiActionStateCreation extends BuchiActionStateCreation implements ActionInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final boolean regular;
	public IncompleteBuchiActionStateCreation(Object source, int id,
			String command, String name, boolean regular, boolean initial,
			boolean accepting) {
		super(source, id, command, name,  initial, accepting);
		this.regular=regular;
	
	}

	
	public void perform(ModelInterface model){
		State s=new State(this.name);
		model.addRegularStateToTheModel(s, this.regular, this.initial, this.accepting);
	}
}
