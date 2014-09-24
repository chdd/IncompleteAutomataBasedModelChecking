package it.polimi.controller;

import it.polimi.model.IntersectionState;
import it.polimi.model.ModelInterface;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.model.io.BuilderException;
import it.polimi.modelchecker.ModelChecker;
import it.polimi.modelchecker.ModelCheckerParameters;
import it.polimi.view.Actions;
import it.polimi.view.ViewInterface;

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
	 */
	public Controller(ModelInterface model, ViewInterface<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>> view) {
		this.model=model;
		this.view=view;
	}

	@Override
	public void update(Observable o, Object arg) {
		try {
			
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
		} catch (BuilderException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/**
	 * run the model checker and updates the view with the current model of the system 
	 */
	private void update(){
		
		this.view.updateModel(model.getModel());
		this.view.updateSpecification(model.getSpecification());
		this.view.updateIntersection(model.getIntersection());
		ModelCheckerParameters<State> mp=new ModelCheckerParameters<State>();
		
		ModelChecker<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>> mc=new ModelChecker<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>>(model.getModel(), model.getSpecification(), mp);
		mc.check();
		this.view.updateVerificationResults(mp);
	}
	

}
