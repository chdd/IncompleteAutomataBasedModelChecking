package it.polimi;

import javax.xml.bind.JAXBException;

import it.polimi.controller.Controller;
import it.polimi.model.IntersectionState;
import it.polimi.model.Model;
import it.polimi.model.ModelInterface;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.model.io.BuilderException;
import it.polimi.view.View;
import it.polimi.view.ViewInterface;


public class Main{

	private static final String modelPath="src//main//resources//ExtendedAutomaton1.xml";
	private static final String specificationPath="src//main//resources//Automaton2.xml";
	
		public static void main(String args[]) throws BuilderException, JAXBException {
		
		// creates the model of the application starting from the model of the system contained in modelPath and the specification contained in the specificationPath
		ModelInterface model=new Model(modelPath, specificationPath);
		
		// contains the view of the application
		ViewInterface<State, Transition<State>,IntersectionState<State>, Transition<IntersectionState<State>>> view=new View<State, Transition<State>,IntersectionState<State>, Transition<IntersectionState<State>>>();
		
		// creates a new controller with the specified model and view
		Controller controller=new Controller(model, view);
		
		// add  the controller as observer of the view
		view.addObserver(controller);
	}
}
