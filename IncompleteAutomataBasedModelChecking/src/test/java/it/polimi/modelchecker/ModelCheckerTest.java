package it.polimi.modelchecker;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.IncompleteBuchiAutomaton;
import it.polimi.model.IntersectionState;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.modelchecker.brzozowski.predicates.AbstractPredicate;
import it.polimi.modelchecker.brzozowski.predicates.EmptyPredicate;

import org.junit.Test;

/**
 * contains the tests that refers to the model checker
 * @author claudiomenghi
 */
public class ModelCheckerTest {

	/** 
	 * contains a simple test where the property is not satisfied.
	 * the model moves from s1 (initial) to s2 (accepting) and then it infinitely loops on s1
	 * the specification moves (with the same characters) from s3 to s4 and then it infinitely loops on s2
	 */
	@Test
	public void test1() {
		// contains the alphabet of the automata
		Set<String> alphabet=new HashSet<String>();
		alphabet.add("a");
		alphabet.add("b");
		
		// creates the model
		IncompleteBuchiAutomaton<State, Transition<State>> model=new IncompleteBuchiAutomaton<State, Transition<State>>(alphabet);
		State s1=new State("s1");
		State s2=new State("s2");
		model.addInitialState(s1);
		model.addAcceptState(s2);
		model.addTransition(s1, new Transition<State>("a", s2));
		model.addTransition(s2, new Transition<State>("b", s2));
		
		// creates the specification
		BuchiAutomaton<State, Transition<State>> specification=new BuchiAutomaton<State, Transition<State>>(alphabet);
		State s3=new State("s3");
		State s4=new State("s4");
		specification.addInitialState(s3);
		specification.addAcceptState(s4);
		specification.addTransition(s3, new Transition<State>("a", s4));
		specification.addTransition(s4, new Transition<State>("b", s4));
		
		// creates the model checking parameters
		ModelCheckerParameters mp=new ModelCheckerParameters();
		
		// creates the model checker
		ModelChecker<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>> mck=
				new ModelChecker<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>>(model, specification, mp);
		AbstractPredicate<State> returnConstraint=new EmptyPredicate<State>();
		
		// the property is not satisfied since the intersection between the model and the specification is not empty
		assertTrue(mck.check(returnConstraint)==0);
	}
	/** 
	 * contains a simple test where the property is satisfied.
	 * the model moves from s1 (initial) to s2 (accepting) and then it infinitely loops on s1
	 * the specification moves (with different characters) from s3 to s4 and then it infinitely loops on s2
	 */
	@Test
	public void test2() {
		// contains the alphabet of the automata
		Set<String> alphabet=new HashSet<String>();
		alphabet.add("a");
		alphabet.add("b");
		alphabet.add("c");
		
		// creates the model
		IncompleteBuchiAutomaton<State, Transition<State>> model=new IncompleteBuchiAutomaton<State, Transition<State>>(alphabet);
		State s1=new State("s1");
		State s2=new State("s2");
		model.addInitialState(s1);
		model.addAcceptState(s2);
		model.addTransition(s1, new Transition<State>("a", s2));
		model.addTransition(s2, new Transition<State>("b", s2));
		
		// creates the specification
		BuchiAutomaton<State, Transition<State>> specification=new BuchiAutomaton<State, Transition<State>>(alphabet);
		State s3=new State("s3");
		State s4=new State("s4");
		specification.addInitialState(s3);
		specification.addAcceptState(s4);
		specification.addTransition(s3, new Transition<State>("a", s4));
		specification.addTransition(s4, new Transition<State>("c", s4));
		
		// creates the model checking parameters
		ModelCheckerParameters mp=new ModelCheckerParameters();
		
		// creates the model checker
		ModelChecker<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>> mck=
				new ModelChecker<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>>(model, specification, mp);
		AbstractPredicate<State> returnConstraint=new EmptyPredicate<State>();
		
		// the property the intersection between the model and the specification is empty the property is satisfied
		assertTrue(mck.check(returnConstraint)==1);
	}

}
