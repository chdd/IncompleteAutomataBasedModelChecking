package it.polimi.controller;

import it.polimi.controller.actions.ActionInterface;
import it.polimi.model.ModelInterface;
import it.polimi.model.automata.ba.transition.ConstrainedTransition;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.elements.states.IntersectionState;
import it.polimi.model.elements.states.State;
import it.polimi.modelchecker.brzozowski.Brzozowski;
import it.polimi.view.ViewInterface;

import java.util.Observable;
import java.util.Observer;

/**
 * Is the controller of the application, manages the model (model of the system and its specification) and the view (the graphical interface of the application)
 * 
 * @author claudiomenghi
 */
public class Controller implements Observer{

	/**
	 * is the (graphical) interface of the application
	 */
	private ViewInterface<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>> view;
	
	/**
	 * is the interface to the model of the application
	 */
	private ModelInterface model;
	
	/**
	 * creates a new controller with the specified model and view 
	 * @param model is the model of the application
	 * @param view is the view of the application
	 * @throws IllegalArgumentException if the model or the specification is null
	 */
	public Controller(ModelInterface model, ViewInterface<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>> view) {
		if(model==null){
			throw new IllegalArgumentException("The model cannot be null");
		}
		if(view==null){
			throw new IllegalArgumentException("The view cannot be null");
		}
		this.model=model;
		this.view=view;
		this.update();
	}

	@Override
	public void update(Observable o, Object arg) {
		
		if(arg instanceof ActionInterface)
		{	ActionInterface a=(ActionInterface) arg;
			try {
				a.perform(model);
			} catch (Exception e) {
				e.printStackTrace();
				this.view.displayErrorMessage(e.getMessage().toString());
			}
		}
		this.update();	
	}
	
	private void update(){
		
		this.view.updateModel(this.model.getModel());
		this.view.updateSpecification(this.model.getSpecification());
		this.view.updateIntersection(model.getIntersection());
		
		this.view.setBrzozoski(new Brzozowski<State, LabelledTransition, IntersectionState<State>, ConstrainedTransition<State>>(this.model.getIntersection()).getConstraintmatrix());
		this.view.updateVerificationResults(this.model.getVerificationResults(), this.model.getIntersection());
	}
	
	
	

}
