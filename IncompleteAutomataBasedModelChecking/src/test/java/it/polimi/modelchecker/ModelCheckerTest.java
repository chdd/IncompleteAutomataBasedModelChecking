package it.polimi.modelchecker;

import static org.junit.Assert.assertTrue;
import it.polimi.model.ba.BuchiAutomaton;
import it.polimi.model.ba.LabelledTransition;
import it.polimi.model.graph.State;
import it.polimi.model.iba.IncompleteBuchiAutomaton;
import it.polimi.model.intersection.IntersectionState;

import java.util.HashSet;
import java.util.Set;

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
		IncompleteBuchiAutomaton<State, LabelledTransition<State>> model=new IncompleteBuchiAutomaton<State, LabelledTransition<State>>(alphabet);
		State s1=new State("s1");
		State s2=new State("s2");
		model.addInitialState(s1);
		model.addAcceptState(s2);
		model.addTransition(s1, new LabelledTransition<State>("a", s2));
		model.addTransition(s2, new LabelledTransition<State>("b", s2));
		
		// creates the specification
		BuchiAutomaton<State, LabelledTransition<State>> specification=new BuchiAutomaton<State, LabelledTransition<State>>(alphabet);
		State s3=new State("s3");
		State s4=new State("s4");
		specification.addInitialState(s3);
		specification.addAcceptState(s4);
		specification.addTransition(s3, new LabelledTransition<State>("a", s4));
		specification.addTransition(s4, new LabelledTransition<State>("b", s4));
		
		// creates the model checking parameters
		ModelCheckerParameters<State> mp=new ModelCheckerParameters<State>();
		
		// creates the model checker
		ModelChecker<State, LabelledTransition<State>, IntersectionState<State>, LabelledTransition<IntersectionState<State>>> mck=
				new ModelChecker<State, LabelledTransition<State>, IntersectionState<State>, LabelledTransition<IntersectionState<State>>>(model, specification, mp);
		
		// the property is not satisfied since the intersection between the model and the specification is not empty
		assertTrue(mck.check()==0);
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
		IncompleteBuchiAutomaton<State, LabelledTransition<State>> model=new IncompleteBuchiAutomaton<State, LabelledTransition<State>>(alphabet);
		State s1=new State("s1");
		State s2=new State("s2");
		model.addInitialState(s1);
		model.addAcceptState(s2);
		model.addTransition(s1, new LabelledTransition<State>("a", s2));
		model.addTransition(s2, new LabelledTransition<State>("b", s2));
		
		// creates the specification
		BuchiAutomaton<State, LabelledTransition<State>> specification=new BuchiAutomaton<State, LabelledTransition<State>>(alphabet);
		State s3=new State("s3");
		State s4=new State("s4");
		specification.addInitialState(s3);
		specification.addAcceptState(s4);
		specification.addTransition(s3, new LabelledTransition<State>("a", s4));
		specification.addTransition(s4, new LabelledTransition<State>("c", s4));
		
		// creates the model checking parameters
		ModelCheckerParameters<State> mp=new ModelCheckerParameters<State>();
		
		// creates the model checker
		ModelChecker<State, LabelledTransition<State>, IntersectionState<State>, LabelledTransition<IntersectionState<State>>> mck=
				new ModelChecker<State, LabelledTransition<State>, IntersectionState<State>, LabelledTransition<IntersectionState<State>>>(model, specification, mp);
		
		// the property the intersection between the model and the specification is empty the property is satisfied
		assertTrue(mck.check()==1);
	}

}
