package it.polimi.model.incompletebuchiautomaton;

import static org.junit.Assert.assertTrue;
import it.polimi.model.ba.LabelledTransition;
import it.polimi.model.graph.State;
import it.polimi.model.iba.IncompleteBuchiAutomaton;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * tests the correct behavior of the reset method of the incomplete buchi automaton
 * @author claudiomenghi
 */
public class IBAresetTest {

	@Test
	public void test() {
		State s1=new State("s1");
		State s2=new State("s2");
		State s3=new State("s3");
		Set<String> alphabet=new HashSet<String>();
		alphabet.add("a");
		alphabet.add("b");
		
		IncompleteBuchiAutomaton<State, LabelledTransition<State>> a=new IncompleteBuchiAutomaton<State, LabelledTransition<State>>(alphabet);
		a.addInitialState(s1);
		a.addTransparentState(s3);
		a.addAcceptState(s2);
		a.addTransition(s1, new LabelledTransition<State>("a", s2));
		a.reset();
		assertTrue(a.getAlphabet().isEmpty());
		assertTrue(a.getStates().isEmpty());
		assertTrue(a.getInitialStates().isEmpty());
		assertTrue(a.getAcceptStates().isEmpty());
		assertTrue(a.getTransitionsWithSource(s1).isEmpty());
		assertTrue(a.getTransparentStates().isEmpty());
	}

}
