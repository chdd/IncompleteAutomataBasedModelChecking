package it.polimi;

import it.polimi.controller.Controller;
import it.polimi.model.Model;
import it.polimi.model.ModelInterface;
import it.polimi.model.impl.states.IntersectionState;
import it.polimi.model.impl.states.IntersectionStateFactoryImpl;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactoryImpl;
import it.polimi.model.impl.transitions.ConstrainedTransitionFactoryImpl;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.impl.transitions.TransitionFactoryImpl;
import it.polimi.view.View;
import it.polimi.view.ViewInterface;

public class Main {

	public static void main(String args[]) {

		// creates the model of the application starting from the model of the
		// system contained in modelPath and the specification contained in the
		// specificationPath
		ModelInterface<State, State, Transition, IntersectionState<State>, Transition> model = 
				new Model<State, State, Transition, IntersectionState<State>, Transition>(
				new StateFactoryImpl(), new TransitionFactoryImpl(),
				new IntersectionStateFactoryImpl(),
				new ConstrainedTransitionFactoryImpl());

		// contains the view of the application
		ViewInterface<State, State, Transition, IntersectionState<State>, Transition> view =
				new View<State, State, Transition, IntersectionState<State>, Transition>(
				model);

		// creates a new controller with the specified model and view
		Controller<State, State, Transition, IntersectionState<State>, Transition> controller = new Controller<State, State, Transition, IntersectionState<State>,  Transition>(
				model, view);

		// add the controller as observer of the view
		view.addObserver(controller);

	}
}
