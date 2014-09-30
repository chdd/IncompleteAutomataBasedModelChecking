package it.polimi.controller.actions;

import it.polimi.model.ModelInterface;
import it.polimi.model.graph.State;

import java.awt.event.ActionEvent;

public class BuchiTransitionCreationAction extends ActionEvent implements ActionInterface{
	
	
	protected final String sourceState;
	protected final String destinationState;
	protected final String character;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BuchiTransitionCreationAction(Object source, int id, String command,  String sourceState, String destinationState, String character){
		super(source, id, command);
		this.sourceState=sourceState;
		this.destinationState=destinationState;
		this.character=character;
	}

	@Override
	public void perform(ModelInterface model) {
		model.addTransitionToTheSpecification(new State(this.sourceState), new State(this.destinationState), this.character);
	}

	
}
