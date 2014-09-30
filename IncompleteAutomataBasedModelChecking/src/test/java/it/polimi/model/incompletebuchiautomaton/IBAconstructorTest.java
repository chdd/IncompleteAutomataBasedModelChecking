package it.polimi.model.incompletebuchiautomaton;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import it.polimi.model.IncompleteBuchiAutomaton;
import it.polimi.model.LabelledTransition;
import it.polimi.model.graph.State;

import org.junit.Test;

/**
 * This class tests the correct behavior of the constructor of the IncompleteBuchiAutomaton
 * @author claudiomenghi
 */
public class IBAconstructorTest {

	@Test
	public void test1() {
		Set<String> alphabet=new HashSet<String>();
		alphabet.add("a");
		alphabet.add("b");
		alphabet.add("c");
		IncompleteBuchiAutomaton<State, LabelledTransition<State>> iba=new IncompleteBuchiAutomaton<State, LabelledTransition<State>>(alphabet);
		assertTrue(iba.getAcceptStates()!=null);
		assertTrue(iba.getAcceptStates().isEmpty());
		assertTrue(iba.getStates()!=null);
		assertTrue(iba.getStates().isEmpty());
		assertTrue(iba.getAlphabet().equals(alphabet));
		assertTrue(iba.getInitialStates()!=null);
		assertTrue(iba.getInitialStates().isEmpty());
		assertTrue(iba.getTransparentStates()!=null);
		assertTrue(iba.getTransparentStates().isEmpty());
	}
	/**
	 * tests that an {@link IllegalArgumentException} is generated when an IncompleteBuchi automaton with a null alphabet is created
	 */
	@Test(expected=IllegalArgumentException.class)
	public void test2(){
		new IncompleteBuchiAutomaton<State, LabelledTransition<State>>(null);
	}

}
