package it.polimi.controller;

import it.polimi.model.IntersectionState;
import it.polimi.model.ModelInterface;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.modelchecker.ModelChecker;
import it.polimi.modelchecker.ModelCheckerParameters;
import it.polimi.view.Actions;
import it.polimi.view.ViewInterface;
import it.polimi.view.actions.BuchiActionStateCreation;
import it.polimi.view.actions.BuchiAddCharacterAction;
import it.polimi.view.actions.BuchiTransitionCreationAction;
import it.polimi.view.actions.IncompleteBuchiActionStateCreation;
import it.polimi.view.actions.IncompleteBuchiAddCharacterAction;
import it.polimi.view.actions.IncompleteBuchiTransitionCreationAction;

import java.awt.event.ActionEvent;
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
	private ViewInterface<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>> view;
	
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
	public Controller(ModelInterface model, ViewInterface<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>> view) {
		if(this.model==null){
			throw new IllegalArgumentException("The model cannot be null");
		}
		if(this.view==null){
			throw new IllegalArgumentException("The view cannot be null");
		}
		this.model=model;
		this.view=view;
		this.update();
	}

	@Override
	public void update(Observable o, Object arg) {
		try {
			if(arg instanceof IncompleteBuchiAddCharacterAction){
				BuchiAddCharacterAction e=(BuchiAddCharacterAction)arg;
				this.model.addCharacterToTheModed(e.getCharacter());
				this.update();
			}
			else{
				if(arg instanceof BuchiAddCharacterAction){
					BuchiAddCharacterAction e=(BuchiAddCharacterAction)arg;
					this.model.addCharacterToTheSpecification(e.getCharacter());
					this.update();
					
				}
				else{
					if(arg instanceof IncompleteBuchiTransitionCreationAction){
						IncompleteBuchiTransitionCreationAction e=(IncompleteBuchiTransitionCreationAction)arg;
						this.model.addTransitionToTheModel(new State(e.getSourceState()), new State(e.getDestinationState()), e.getCharacter());
						this.update();
					}
					else{
						if(arg instanceof BuchiTransitionCreationAction){
							BuchiTransitionCreationAction e=(BuchiTransitionCreationAction)arg;
							this.model.addTransitionToTheSpecification(new State(e.getSourceState()), new State(e.getDestinationState()), e.getCharacter());
							this.update();
						}
						else{
							if(arg instanceof IncompleteBuchiActionStateCreation){
								IncompleteBuchiActionStateCreation e=(IncompleteBuchiActionStateCreation)arg;
								State s=new State(e.getName());
								this.model.addRegularStateToTheModel(s, e.isRegular(), e.isInitial(), e.isAccepting());
								this.update();
							}
							else{
								if(arg instanceof  BuchiActionStateCreation){
									BuchiActionStateCreation e=(BuchiActionStateCreation)arg;
									State s=new State(e.getName());
									this.model.addRegularStateToTheSpecification(s, e.isInitial(), e.isAccepting());
									this.update();
								}
							}
						}
					}
				}
			}
			
			
			
			ActionEvent e=(ActionEvent) arg;
			if(e.getActionCommand()== Actions.LOADMODEL.name()){
				String file=this.view.getFile();
				model.changeModel(file);
				this.update();
			}
			if(e.getActionCommand()== Actions.LOADSPECIFICATION.name()){
				String file=this.view.getFile();
				model.changeSpecification(file);
				this.update();
			}
			if(e.getActionCommand()==Actions.SAVEMODEL.name()){
				String filePath=this.view.createFile();
				this.model.loadModelFromXML(this.view.getModelXML());
				this.model.saveModel(filePath);
			}
			if(e.getActionCommand()==Actions.SAVESPECIFICATION.name()){
				String filePath=this.view.createFile();
				this.model.loadSpecificationFromXML(this.view.getSpecificationXML());
				this.model.saveSpecification(filePath);
				this.update();
			}
		} catch (Exception e1) {
			this.view.displayErrorMessage(e1.getMessage());
		}
	}
	
	/**
	 * run the model checker and updates the view with the current model of the system 
	 */
	private void update(){
		
		this.view.updateModel(model.getModel());
		this.view.updateSpecification(model.getSpecification());
		ModelCheckerParameters<State> mp=new ModelCheckerParameters<State>();
		
		ModelChecker<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>> mc=new ModelChecker<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>>(model.getModel(), model.getSpecification(), mp);
		mc.check();
		this.view.updateIntersection(model.getIntersection());
		
		this.view.updateVerificationResults(mp);
	}
	

}
