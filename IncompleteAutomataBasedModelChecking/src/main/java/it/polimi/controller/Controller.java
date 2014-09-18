package it.polimi.controller;

import it.polimi.model.AutomatonBuilder;
import it.polimi.model.BuchiAutomaton;
import it.polimi.model.IncompleteBuchiAutomaton;
import it.polimi.model.IntersectionAutomaton;
import it.polimi.model.IntersectionState;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.modelchecker.ModelChecker;
import it.polimi.modelchecker.ModelCheckerParameters;
import it.polimi.view.ViewInterface;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Controller implements Observer, ControllerInterface{

	private ViewInterface view;
	public Controller(String modelFilePath, String specificationFilePath, ViewInterface view) throws JAXBException, SAXException, IOException, ParserConfigurationException{
		
		AutomatonBuilder<State, Transition<State>, IncompleteBuchiAutomaton<State, Transition<State>>> builderIBA=
				new AutomatonBuilder<State, Transition<State>, IncompleteBuchiAutomaton<State, Transition<State>>>();
		IncompleteBuchiAutomaton<State, Transition<State>> model = builderIBA.loadAutomaton(IncompleteBuchiAutomaton.class, modelFilePath);
		
		AutomatonBuilder<State, Transition<State>, BuchiAutomaton<State, Transition<State>>> builderBA=
				new AutomatonBuilder<State, Transition<State>, BuchiAutomaton<State, Transition<State>>>();
		
		BuchiAutomaton<State, Transition<State>>  specification=builderBA.loadAutomaton(BuchiAutomaton.class, specificationFilePath);
		IntersectionAutomaton<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>> intersection=
				new IntersectionAutomaton<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>>(model, specification);
		
		ModelCheckerParameters<State> mp=new ModelCheckerParameters<State>();
		
		ModelChecker<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>> mc=new ModelChecker<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>>(model, specification, mp);
		mc.check();
		
		this.view=view;
		this.view.updateModel(model);
		this.view.updateSpecification(specification);
		this.view.updateIntersection(intersection);
		this.view.updateVerificationResults(mp);
		
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
