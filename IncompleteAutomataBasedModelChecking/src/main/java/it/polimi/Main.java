package it.polimi;

import javax.xml.bind.JAXBException;

import it.polimi.controller.Controller;
import it.polimi.model.IntersectionState;
import it.polimi.model.Model;
import it.polimi.model.ModelInterface;
import it.polimi.model.State;
import it.polimi.model.LabelledTransition;
import it.polimi.model.io.BuilderException;
import it.polimi.view.View;
import it.polimi.view.ViewInterface;


public class Main{

	public static void main(String args[]) throws BuilderException, JAXBException {
		
		// creates the model of the application starting from the model of the system contained in modelPath and the specification contained in the specificationPath
		ModelInterface model=new Model();
		
		// contains the view of the application
		ViewInterface<State, LabelledTransition<State>,IntersectionState<State>, LabelledTransition<IntersectionState<State>>> view=new View();
		
		// creates a new controller with the specified model and view
		Controller controller=new Controller(model, view);
		
		// add  the controller as observer of the view
		view.addObserver(controller);
	}
}
