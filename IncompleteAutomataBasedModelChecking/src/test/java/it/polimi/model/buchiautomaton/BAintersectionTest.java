package it.polimi.model.buchiautomaton;

import static org.junit.Assert.*;
import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.LabelledTransition;
import it.polimi.model.automata.iba.IncompleteBuchiAutomaton;
import it.polimi.model.automata.intersection.IntersectionAutomaton;
import it.polimi.model.automata.intersection.IntersectionState;
import it.polimi.model.graph.State;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

public class BAintersectionTest {

	/**
	 * tests the intersection of an empty model and an empty specification
	 */
	@Test
	public void testIntersection1() throws JAXBException, SAXException, IOException, ParserConfigurationException {
		
		 Set<String> alphabet=new HashSet<String>();
		 IncompleteBuchiAutomaton<State, LabelledTransition<State>> model =new  IncompleteBuchiAutomaton<State, LabelledTransition<State>>(alphabet);
		  
		 BuchiAutomaton<State, LabelledTransition<State>>  specification =new  BuchiAutomaton<State, LabelledTransition<State>>();
		 IntersectionAutomaton<State, LabelledTransition<State>, IntersectionState<State>, LabelledTransition<IntersectionState<State>>> ris=
				 new IntersectionAutomaton<State, LabelledTransition<State>, IntersectionState<State>, LabelledTransition<IntersectionState<State>>>(model, specification);
		 
		 assertTrue(ris.isEmpty());
		 assertTrue(ris.getStates().isEmpty());
	}
	/**
	 * tests the intersection of an empty specification and a model with a single state s1
	 */
	@Test
	public void testIntersection2() throws JAXBException, SAXException, IOException, ParserConfigurationException {
		 // creates the alphabet
		 Set<String> alphabet=new HashSet<String>();
		 // creates the model of the system
		 IncompleteBuchiAutomaton<State, LabelledTransition<State>> model =new  IncompleteBuchiAutomaton<State, LabelledTransition<State>>(alphabet);
		 // add the state s1 to the model
		 model.addState(new State("s1"));
		 // creates the specification of the system
		 BuchiAutomaton<State, LabelledTransition<State>>  specification =new  BuchiAutomaton<State, LabelledTransition<State>>();
		 IntersectionAutomaton<State, LabelledTransition<State>, IntersectionState<State>, LabelledTransition<IntersectionState<State>>> ris=
				 new IntersectionAutomaton<State, LabelledTransition<State>, IntersectionState<State>, LabelledTransition<IntersectionState<State>>>(model, specification);
		 	
		 assertTrue(ris.isEmpty());
		 assertTrue(ris.getStates().isEmpty());
	}
	/**
	 * tests the intersection of a model with initial state s1 with a specification with initial state s2
	 */
	@Test
	public void testIntersection3() throws JAXBException, SAXException, IOException, ParserConfigurationException {
		
		 Set<String> alphabet=new HashSet<String>();
		 // creates the model
		 IncompleteBuchiAutomaton<State, LabelledTransition<State>> model =new  IncompleteBuchiAutomaton<State, LabelledTransition<State>>(alphabet);
		 model.addState(new State("s1"));
		 model.addInitialState(new State("s1"));
		 // creates the specification
		 BuchiAutomaton<State, LabelledTransition<State>>  specification =new  BuchiAutomaton<State, LabelledTransition<State>>();
		 specification.addState(new State("s2"));
		 specification.addInitialState(new State("s2"));
		 
		 // creates the intersection automaton
		 IntersectionAutomaton<State, LabelledTransition<State>, IntersectionState<State>, LabelledTransition<IntersectionState<State>>> ris=
				 new IntersectionAutomaton<State, LabelledTransition<State>, IntersectionState<State>, LabelledTransition<IntersectionState<State>>>(model, specification);
		 
		 // the automaton is empty since no accepting runs are presented
		 assertTrue(ris.isEmpty());
		 assertTrue(ris.getStates().contains(new IntersectionState<State>(new State("s1"), new State("s2"), 0)));
	}

}
