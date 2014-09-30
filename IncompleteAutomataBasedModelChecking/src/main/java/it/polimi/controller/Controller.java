package it.polimi.controller;

import it.polimi.controller.actions.ActionInterface;
import it.polimi.model.IntersectionState;
import it.polimi.model.ModelInterface;
import it.polimi.model.LabelledTransition;
import it.polimi.model.graph.State;
import it.polimi.modelchecker.ModelChecker;
import it.polimi.modelchecker.ModelCheckerParameters;
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
	private ViewInterface<State, LabelledTransition<State>, IntersectionState<State>, LabelledTransition<IntersectionState<State>>> view;
	
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
	public Controller(ModelInterface model, ViewInterface<State, LabelledTransition<State>, IntersectionState<State>, LabelledTransition<IntersectionState<State>>> view) {
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
		try {
			
			ActionInterface a=(ActionInterface) arg;
			a.perform(model);
			this.update();
		} catch (Exception e) {
			this.view.displayErrorMessage(e.getMessage());
		}
	}
	
	/**
	 * run the model checker and updates the view with the current model of the system 
	 */
	private void update(){
		
		this.view.updateModel(model.getModel());
		this.view.updateSpecification(model.getSpecification());
		ModelCheckerParameters<State> mp=new ModelCheckerParameters<State>();
		
		ModelChecker<State, LabelledTransition<State>, IntersectionState<State>, LabelledTransition<IntersectionState<State>>> mc=new ModelChecker<State, LabelledTransition<State>, IntersectionState<State>, LabelledTransition<IntersectionState<State>>>(model.getModel(), model.getSpecification(), mp);
		mc.check();
		this.view.updateIntersection(model.getIntersection());
		
		this.view.updateVerificationResults(mp);
	}
	

}
