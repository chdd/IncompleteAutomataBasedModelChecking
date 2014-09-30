package it.polimi.model.buchiautomaton;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.LabelledTransition;
import it.polimi.model.graph.State;

import org.junit.Test;

/**
 * tests the is Empty method
 * @author claudiomenghi
 *
 */
public class BAisEmptyTest {

	// Creates a new test where there are three states in sequence and a self loop on the state s3
	@Test
	public void isEmptyTest1() {
		// creates the states
		State s1=new State("s1");
		State s2=new State("s2");
		State s3=new State("s3");
		// creates the alphabet
		Set<String> alphabet=new HashSet<String>();
		alphabet.add("a");
		//creates the Buchi automaton
		BuchiAutomaton<State, LabelledTransition<State>> a=new BuchiAutomaton<State, LabelledTransition<State>>(alphabet);
		// add the states into the automaton
		a.addState(s1);
		a.addState(s2);
		a.addState(s3);
		// add the initial state
		a.addInitialState(s1);
		// add the accepting state
		a.addAcceptState(s3);
		// add the transitions
		a.addTransition(s1, new LabelledTransition<State>("a", s2));
		a.addTransition(s2, new LabelledTransition<State>("a", s3));
		a.addTransition(s3, new LabelledTransition<State>("a", s3));
		// assert that the automaton is not empty
		assertFalse(a.isEmpty());
	}

}
