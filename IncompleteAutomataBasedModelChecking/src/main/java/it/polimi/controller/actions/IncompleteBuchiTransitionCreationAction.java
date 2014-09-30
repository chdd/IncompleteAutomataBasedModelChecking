package it.polimi.controller.actions;

import it.polimi.model.ModelInterface;
import it.polimi.model.graph.State;

public class IncompleteBuchiTransitionCreationAction extends BuchiTransitionCreationAction implements ActionInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IncompleteBuchiTransitionCreationAction(Object source, int id,
			String command, String sourceState, String destinationState,
			String character) {
		super(source, id, command, sourceState, destinationState, character);
	}

	@Override
	public void perform(ModelInterface model) {
		model.addTransitionToTheModel(new State(this.sourceState), new State(this.destinationState), this.character);
		
	}
}
