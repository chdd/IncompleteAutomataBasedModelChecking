package it.polimi;

import it.polimi.controller.Controller;
import it.polimi.model.Model;
import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactory;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.ConstrainedTransitionFactoryImpl;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.impl.transitions.LabelledTransitionFactoryImpl;
import it.polimi.model.interfaces.transitions.ConstrainedTransitionFactory;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.View;
import it.polimi.view.ViewInterface;


public class Main{

	public static void main(String args[]) {
		
	    
		// creates the model of the application starting from the model of the system contained in modelPath and the specification contained in the specificationPath
		ModelInterface<State,
			State, StateFactory<State>, 
			LabelledTransition<State>, 
			LabelledTransitionFactory<State, LabelledTransition<State>>, 
			IntersectionState<State>, 
			IntersectionStateFactory<State,IntersectionState<State>>, 
			LabelledTransition<State>, 
			ConstrainedTransitionFactory<State,LabelledTransition<State>>> model=new Model<State,
					State, StateFactory<State>, 
					LabelledTransition<State>, 
					LabelledTransitionFactory<State, LabelledTransition<State>>, 
					IntersectionState<State>, 
					IntersectionStateFactory<State,IntersectionState<State>>, 
					LabelledTransition<State>, 
					ConstrainedTransitionFactory<State,LabelledTransition<State>>>(
							new StateFactory<State>(),
							new LabelledTransitionFactoryImpl(),
							new IntersectionStateFactory<State, IntersectionState<State>>(),
							new ConstrainedTransitionFactoryImpl());
		
		// contains the view of the application
		ViewInterface<State, State, LabelledTransition<State>,IntersectionState<State>, LabelledTransition<State>,
			LabelledTransitionFactory<State, LabelledTransition <State>>,
			ConstrainedTransitionFactory<State, LabelledTransition<State>>> view=
			new View<State, State, StateFactory<State>, LabelledTransition<State>, LabelledTransitionFactory<State, LabelledTransition<State>>, IntersectionState<State>, 
			IntersectionStateFactory<State, IntersectionState<State>>,
			LabelledTransition<State>,
			ConstrainedTransitionFactory<State, LabelledTransition<State>>>(
				 model);
		
		// creates a new controller with the specified model and view
		Controller<State, State, StateFactory<State>, LabelledTransition<State>, LabelledTransitionFactory<State, LabelledTransition<State>>, 
					IntersectionState<State>, IntersectionStateFactory<State,IntersectionState<State>>,
					LabelledTransition<State>, ConstrainedTransitionFactory<State,LabelledTransition<State>>> controller=
					new Controller<State, State, StateFactory<State>, LabelledTransition<State>, LabelledTransitionFactory<State, LabelledTransition<State>>, 
							IntersectionState<State>, IntersectionStateFactory<State,IntersectionState<State>>,
							LabelledTransition<State>, ConstrainedTransitionFactory<State,LabelledTransition<State>>>(model, view);
		
		// add  the controller as observer of the view
		view.addObserver(controller);
		
		
		
		
	}
}
