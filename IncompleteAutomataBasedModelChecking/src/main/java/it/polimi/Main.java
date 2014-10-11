package it.polimi;

import it.polimi.controller.Controller;
import it.polimi.model.Model;
import it.polimi.model.ModelInterface;
import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.transition.ConstrainedTransition;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.automata.intersection.IntersectionState;
import it.polimi.view.View;
import it.polimi.view.ViewInterface;

import javax.xml.bind.JAXBException;

import rwth.i2.ltl2ba4j.formula.IFormula;
import rwth.i2.ltl2ba4j.formula.IFormulaFactory;
import rwth.i2.ltl2ba4j.formula.impl.FormulaFactory;


public class Main{

	public static void main(String args[]) throws JAXBException {
		
		// creates the model of the application starting from the model of the system contained in modelPath and the specification contained in the specificationPath
		ModelInterface model=new Model();
		
		// contains the view of the application
		ViewInterface<State, LabelledTransition,IntersectionState<State>, ConstrainedTransition<State>> view=new View<State, LabelledTransition,IntersectionState<State>, ConstrainedTransition<State>>(model.getModel(), model.getSpecification(), model.getIntersection());
		
		// creates a new controller with the specified model and view
		Controller controller=new Controller(model, view);
		
		// add  the controller as observer of the view
		view.addObserver(controller);
		
		/*IFormulaFactory factory = new FormulaFactory();
		
		IFormula formula=factory.G(
                     factory.And(
                        factory.Proposition("prop1"),
                        factory.Not(
                                factory.Proposition("prop2")
                        )
                     )
                   );
		formula.*/
		
		
	}
}
