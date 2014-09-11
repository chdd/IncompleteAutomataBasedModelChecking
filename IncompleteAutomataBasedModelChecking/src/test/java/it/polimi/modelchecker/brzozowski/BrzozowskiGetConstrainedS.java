package it.polimi.modelchecker.brzozowski;

import static org.junit.Assert.assertTrue;
import it.polimi.model.BuchiAutomaton;
import it.polimi.model.IncompleteBuchiAutomaton;
import it.polimi.model.IntersectionAutomaton;
import it.polimi.model.IntersectionState;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.modelchecker.brzozowski.predicates.AbstractPredicate;
import it.polimi.modelchecker.brzozowski.predicates.EmptyPredicate;
import it.polimi.modelchecker.brzozowski.predicates.LambdaPredicate;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class BrzozowskiGetConstrainedS {

	private IntersectionAutomaton<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>> intersectBA=null;
	private Brzozowski<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>> brzozowski=null;
	
	private State s1=new State("s1");
	private State s2=new State("s2");
	private State s3=new State("s3");
	private State s4=new State("s4");
	
	private AbstractPredicate<State> emptyPredicate=null;
	private AbstractPredicate<State> lambdaPredicate=null;
	@Before
	public void setUp(){
		Set<String> alphabet=new HashSet<String>();
		alphabet.add("a");
		
		IncompleteBuchiAutomaton<State, Transition<State>> model=new IncompleteBuchiAutomaton<State, Transition<State>>(alphabet);
		// add the states to the BuchiAutomaton
		model.addInitialState(s1);
		model.addState(s2);
		model.addState(s3);
		model.addAcceptState(s4);
		
		model.addTransition(s1, new Transition<State>("a", s2));
		model.addTransition(s2, new Transition<State>("a", s3));
		model.addTransition(s3, new Transition<State>("a", s4));
		model.addTransition(s4, new Transition<State>("a", s4));
		
		// creates the BuchiAutomaton
		BuchiAutomaton<State, Transition<State>> specification=new BuchiAutomaton<State, Transition<State>>(alphabet);
		// add the states to the BuchiAutomaton
		specification.addInitialState(s1);
		specification.addState(s2);
		specification.addState(s3);
		specification.addAcceptState(s4);
		
		specification.addTransition(s1, new Transition<State>("a", s2));
		specification.addTransition(s2, new Transition<State>("a", s3));
		specification.addTransition(s3, new Transition<State>("a", s4));
		specification.addTransition(s4, new Transition<State>("a", s4));
	
		emptyPredicate=new EmptyPredicate<State>();
		lambdaPredicate=new LambdaPredicate<State>();
		
		intersectBA=new IntersectionAutomaton<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>>(model, specification);
		System.out.println(intersectBA);
		brzozowski=new Brzozowski<State, Transition<State>, IntersectionState<State>, Transition<IntersectionState<State>>>(intersectBA);
	}

	/**
	 * tests that when a state which is not accepting is passed as a final state in the method getS, the method getS returns an
	 * exception
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testGetS1() {
		brzozowski.getConstrainedS(new IntersectionState<State>(s1, s1, 0));
	}
	
	/**
	 * tests the correct behavior of the method getS when an accepting state is passed as parameter
	 */
	@Test
	public void testGetS2() {
		AbstractPredicate<State>[] ret=brzozowski.getConstrainedS(new IntersectionState<State>(s4, s4, 2));
		int i=0;
		for(IntersectionState<State> s: this.intersectBA.getStates()){
			if(s.equals(new IntersectionState<State>(s1, s1, 0)) ||
					s.equals(new IntersectionState<State>(s2, s2, 0))||
					s.equals(new IntersectionState<State>(s3, s3, 0))||
					s.equals(new IntersectionState<State>(s4, s4, 0))||
					s.equals(new IntersectionState<State>(s4, s4, 1))){
				assertTrue(ret[i].equals(emptyPredicate));
			}
			if(s.equals(new IntersectionState<State>(s4, s4, 2))){
				assertTrue(ret[i].equals(lambdaPredicate));
			}
			i++;
		}
	}
	/**
	 * Tests the correct behavior of the method when a null pointr parameter is passed
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testGetS3() {
		brzozowski.getConstrainedS(null);

	}
}
