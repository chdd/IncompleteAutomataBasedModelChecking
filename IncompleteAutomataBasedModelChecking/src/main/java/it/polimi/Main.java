package it.polimi;

import it.polimi.controller.Controller;
import it.polimi.model.Model;
import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.ConstrainedTransition;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.View;
import it.polimi.view.ViewInterface;


public class Main{

	public static void main(String args[]) {
		
		// creates the model of the application starting from the model of the system contained in modelPath and the specification contained in the specificationPath
		ModelInterface model=new Model();
		
		// contains the view of the application
		ViewInterface<State, LabelledTransition,IntersectionState<State>, ConstrainedTransition<State>,
			LabelledTransitionFactory<LabelledTransition>,
			ConstrainedTransitionFactory<State, ConstrainedTransition<State>>> view=
			new View<State, LabelledTransition,IntersectionState<State>, ConstrainedTransition<State>,
			LabelledTransitionFactory<LabelledTransition>,
			ConstrainedTransitionFactory<State, ConstrainedTransition<State>>>(
				 model.getModel(), 
				 model.getSpecification(), 
				 model.getIntersection());
		
		// creates a new controller with the specified model and view
		Controller controller=new Controller(model, view);
		
		// add  the controller as observer of the view
		view.addObserver(controller);
		
		
		
		
	}
}
