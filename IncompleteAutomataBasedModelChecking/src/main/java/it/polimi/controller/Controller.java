package it.polimi.controller;

import it.polimi.controller.actions.ActionInterface;
import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.view.ViewInterface;

import java.util.Observable;
import java.util.Observer;

/**
 * Is the controller of the application, manages the model (model of the system and its specification) and the view (the graphical interface of the application)
 * 
 * @author claudiomenghi
 */
public class Controller<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
TRANSITION extends Transition, 
INTERSECTIONSTATE extends IntersectionState<STATE>, 
INTERSECTIONTRANSITION extends Transition>  implements Observer{

	/**
	 * is the (graphical) interface of the application
	 */
	private ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> view;
	
	/**
	 * is the interface to the model of the application
	 */
	private ModelInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE,
	INTERSECTIONTRANSITION> model;
	
	/**
	 * creates a new controller with the specified model and view 
	 * @param model is the model of the application
	 * @param view is the view of the application
	 * @throws IllegalArgumentException if the model or the specification is null
	 */
	public Controller(ModelInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE,
			INTERSECTIONTRANSITION> model, 
			ViewInterface<CONSTRAINEDELEMENT, STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION> view) {
		if(model==null){
			throw new IllegalArgumentException("The model cannot be null");
		}
		if(view==null){
			throw new IllegalArgumentException("The view cannot be null");
		}
		this.model=model;
		this.view=view;
	}

	@Override
	public void update(Observable o, Object arg) {
		
		if(arg instanceof ActionInterface)
		{	@SuppressWarnings("unchecked")
			ActionInterface<
			CONSTRAINEDELEMENT,
				STATE, 
				TRANSITION> a=(ActionInterface<
						CONSTRAINEDELEMENT,
							STATE,  
							TRANSITION>) arg;
			try {
				a.perform(model, this.view);
				view.updateModel(model.getModel(), model.getModelRefinementHierarchy(),model.getflattenModelRefinement());
				view.updateClaim(model.getSpecification());
				
			} catch (Exception e) {
				e.printStackTrace();
				this.view.displayErrorMessage(e.getMessage().toString());
			}
		}
	
	}
	
	
}
