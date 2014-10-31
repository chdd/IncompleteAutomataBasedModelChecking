package it.polimi.controller;

import it.polimi.controller.actions.ActionInterface;
import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.ViewInterface;

import java.util.Observable;
import java.util.Observer;

/**
 * Is the controller of the application, manages the model (model of the system and its specification) and the view (the graphical interface of the application)
 * 
 * @author claudiomenghi
 */
public class Controller<STATE extends State, 
STATEFACTORY extends StateFactory<STATE>,
TRANSITION extends LabelledTransition, 
TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>,
INTERSECTIONSTATE extends IntersectionState<STATE>, 
INTERSECTIONSTATEFACTORY extends IntersectionStateFactory<STATE, INTERSECTIONSTATE>,
INTERSECTIONTRANSITION extends ConstrainedTransition<STATE>,
INTERSECTIONTRANSITIONFACTORY extends ConstrainedTransitionFactory<STATE, INTERSECTIONTRANSITION>>  implements Observer{

	/**
	 * is the (graphical) interface of the application
	 */
	private ViewInterface<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, 
	TRANSITIONFACTORY,
	INTERSECTIONTRANSITIONFACTORY> view;
	
	/**
	 * is the interface to the model of the application
	 */
	private ModelInterface<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY,
	INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> model;
	
	/**
	 * creates a new controller with the specified model and view 
	 * @param model is the model of the application
	 * @param view is the view of the application
	 * @throws IllegalArgumentException if the model or the specification is null
	 */
	public Controller(ModelInterface<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY, INTERSECTIONSTATE, INTERSECTIONSTATEFACTORY,
			INTERSECTIONTRANSITION, INTERSECTIONTRANSITIONFACTORY> model, 
			ViewInterface<STATE, TRANSITION, INTERSECTIONSTATE, INTERSECTIONTRANSITION, TRANSITIONFACTORY, INTERSECTIONTRANSITIONFACTORY> view) {
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
				STATE, 
				STATEFACTORY, 
				TRANSITION , 
				TRANSITIONFACTORY, 
				INTERSECTIONSTATE, 
				INTERSECTIONSTATEFACTORY, 
				INTERSECTIONTRANSITION, 
				INTERSECTIONTRANSITIONFACTORY> a=(ActionInterface<
							STATE, 
							STATEFACTORY, 
							TRANSITION , 
							TRANSITIONFACTORY, 
							INTERSECTIONSTATE, 
							INTERSECTIONSTATEFACTORY, 
							INTERSECTIONTRANSITION, 
							INTERSECTIONTRANSITIONFACTORY>) arg;
			try {
				a.perform(model, this.view);
			} catch (Exception e) {
				e.printStackTrace();
				this.view.displayErrorMessage(e.getMessage().toString());
			}
		}
	
	}
	
	
}
