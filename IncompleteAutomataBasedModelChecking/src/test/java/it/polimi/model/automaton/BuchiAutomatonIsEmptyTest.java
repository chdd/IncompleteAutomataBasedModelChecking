package it.polimi.model.automaton;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;

import org.junit.Test;

public class BuchiAutomatonIsEmptyTest {

	@Test
	public void isEmptyTest1() {
		State s1=new State("s1");
		State s2=new State("s2");
		State s3=new State("s3");
		Set<String> alphabet=new HashSet<String>();
		alphabet.add("a");
		BuchiAutomaton<State, Transition<State>> a=new BuchiAutomaton<State, Transition<State>>(alphabet);
		a.addState(s1);
		a.addState(s2);
		a.addState(s3);
		a.addInitialState(s1);
		a.addAcceptState(s3);
		a.addTransition(s1, new Transition<State>("a", s2));
		a.addTransition(s2, new Transition<State>("a", s3));
		a.addTransition(s3, new Transition<State>("a", s3));
		assertFalse(a.isEmpty());
	}

}
