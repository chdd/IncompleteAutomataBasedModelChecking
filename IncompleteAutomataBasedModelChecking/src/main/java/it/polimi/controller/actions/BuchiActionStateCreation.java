package it.polimi.controller.actions;

import it.polimi.model.ModelInterface;
import it.polimi.model.graph.State;

import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class BuchiActionStateCreation extends ActionEvent implements ActionInterface{
	
	protected final String name;
	protected final boolean initial;
	protected final boolean accepting;

	public BuchiActionStateCreation(Object source, int id, String command, String  name, boolean initial, boolean accepting){
		super(source, id, command);
		this.name=name;
		this.initial=initial;
		this.accepting=accepting;
	}
	
	
	public void perform(ModelInterface model){
		State s=new State(this.name);
		model.addRegularStateToTheSpecification(s, this.initial, this.accepting);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


		/**
	 * @return the initial
	 */
	public boolean isInitial() {
		return initial;
	}

	
	/**
	 * @return the accepting
	 */
	public boolean isAccepting() {
		return accepting;
	}

	
}
