package it.polimi.model.automaton;

import static org.junit.Assert.*;
import it.polimi.model.BuchiAutomaton;
import it.polimi.model.IncompleteBuchiAutomaton;
import it.polimi.model.IntersectionAutomaton;
import it.polimi.model.IntersectionState;
import it.polimi.model.State;
import it.polimi.model.Transition;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

public class BuchiAutomatonIntersectionTest {

	@Test
	public void test() throws JAXBException, SAXException, IOException, ParserConfigurationException {
		
		IncompleteBuchiAutomaton<State, Transition<State>> a1 = IncompleteBuchiAutomaton.loadAutomaton("src//test//resources//ExtendedAutomaton1.xml");
		  
		 BuchiAutomaton<State, Transition<State>>  a2 = BuchiAutomaton.loadAutomaton("src//test//resources//Automaton1.xml");
		 IntersectionAutomaton<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>> ris=a1.computeIntersection(a2);
		 IntersectionAutomaton<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>> exprectedRis=IntersectionAutomaton.loadIntAutomaton("src//test//resources//IntersectionAutomaton1.xml");
		 assertTrue(ris.equals(exprectedRis));
	}

}
