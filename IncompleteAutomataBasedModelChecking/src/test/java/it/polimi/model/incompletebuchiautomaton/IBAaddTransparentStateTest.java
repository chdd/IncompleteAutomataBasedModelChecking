package it.polimi.model.incompletebuchiautomaton;

import static org.junit.Assert.*;
import it.polimi.model.IncompleteBuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.LabelledTransition;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * this class test the behavior of the method addTransparentState
 * @author claudiomenghi
 */
public class IBAaddTransparentStateTest {

	/** 
	 * tests the behavior of the method when s2 is a state
	 */
	@Test
	public void test1() {
		Set<String> alphabet=new HashSet<String>();
		IncompleteBuchiAutomaton<State, LabelledTransition<State>> iba=new IncompleteBuchiAutomaton<State, LabelledTransition<State>>(alphabet);
		State s1=new State("a");
		iba.addTransparentState(s1);
	
		assertTrue(iba.isTransparent(s1));
		assertTrue(iba.isContained(s1));
		assertFalse(iba.isAccept(s1));
		assertFalse(iba.isInitial(s1));
		
		assertTrue(iba.getStates().contains(s1));
		assertTrue(iba.getTransparentStates().contains(s1));
		assertFalse(iba.getAcceptStates().contains(s1));
		assertFalse(iba.getInitialStates().contains(s1));
	}
	/**
	 * tests the behavior of the method when the added state is transparent 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void test2() {
		Set<String> alphabet=new HashSet<String>();
		IncompleteBuchiAutomaton<State, LabelledTransition<State>> iba=new IncompleteBuchiAutomaton<State, LabelledTransition<State>>(alphabet);
		iba.addTransparentState(null);	
	}

}
