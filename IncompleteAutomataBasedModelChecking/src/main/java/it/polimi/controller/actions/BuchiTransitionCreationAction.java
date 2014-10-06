package it.polimi.controller.actions;

import it.polimi.model.ModelInterface;
import it.polimi.model.automata.ba.State;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;


public class BuchiTransitionCreationAction extends ActionEvent implements ActionInterface{
	
	
	protected final String sourceState;
	protected final String destinationState;
	protected final Set<String> character;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BuchiTransitionCreationAction(Object source, int id, String command,  String sourceState, String destinationState, String character){
		super(source, id, command);
		this.sourceState=sourceState;
		this.destinationState=destinationState;
		this.character=new HashSet<String>(java.util.Arrays.asList(character.split(",")));
	}

	@Override
	public void perform(ModelInterface model) {
		model.addTransitionToTheSpecification(new State(this.sourceState), new State(this.destinationState), this.character);
	}

	
}
