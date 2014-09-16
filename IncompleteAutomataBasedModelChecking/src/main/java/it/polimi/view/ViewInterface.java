package it.polimi.view;

import javax.xml.bind.JAXBException;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.IncompleteBuchiAutomaton;
import it.polimi.model.IntersectionAutomaton;
import it.polimi.model.IntersectionState;
import it.polimi.model.State;
import it.polimi.model.Transition;

public interface ViewInterface<S1 extends State, T1 extends Transition<S1>, S extends IntersectionState<S1>, T extends Transition<S>>{

	public void updateModel(IncompleteBuchiAutomaton<S1, T1> model) throws JAXBException;
	public void updateSpecification(BuchiAutomaton<S1, T1> specification) throws JAXBException;
	public void updateIntersection(IntersectionAutomaton<S1,T1,S,T> intersection) throws JAXBException;
}
