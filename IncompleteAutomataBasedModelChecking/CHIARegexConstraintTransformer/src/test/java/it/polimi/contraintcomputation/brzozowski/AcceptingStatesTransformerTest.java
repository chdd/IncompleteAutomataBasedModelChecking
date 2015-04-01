/**
 * 
 */
package it.polimi.contraintcomputation.brzozowski;

import static org.junit.Assert.*;
import it.polimi.automata.BA;
import it.polimi.automata.impl.IBAImpl;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.impl.TransitionFactoryClaimImpl;
import it.polimi.contraintcomputation.Constants;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author claudiomenghi
 *
 */
public class AcceptingStatesTransformerTest {

	private BA<State, Transition> ba;
	private State state1;
	private State state2;
	private State state3;
	private State state4;
	
	
	@Before
	public void setUp() {
		this.ba=new IBAImpl<State, Transition>(new TransitionFactoryClaimImpl<State>(Transition.class));
		StateFactory<State> factory=new StateFactoryImpl();
		state1=factory.create();
		state2=factory.create();
		state3=factory.create();
		state4=factory.create();
		this.ba.addState(state1);
		this.ba.addState(state2);
		this.ba.addState(state3);
	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.brzozowski.AcceptingStatesTransformer#AcceptingStatesTransformer(java.util.List, it.polimi.automata.state.State)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testAcceptingStatesTransformerNullOrderedStates() {
		new AcceptingStatesTransformer<State, Transition>(null, new StateFactoryImpl().create());
	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.brzozowski.AcceptingStatesTransformer#AcceptingStatesTransformer(java.util.List, null)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testAcceptingStatesTransformerNullState() {
		new AcceptingStatesTransformer<State, Transition>(new ArrayList<State>(), null);
	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.brzozowski.AcceptingStatesTransformer#AcceptingStatesTransformer(java.util.List, it.polimi.automata.state.State)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testAcceptingStatesTransformerStateNotInTheOrderedStates() {
		new AcceptingStatesTransformer<State, Transition>(new ArrayList<State>(), new StateFactoryImpl().create());
	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.brzozowski.AcceptingStatesTransformer#AcceptingStatesTransformer(java.util.List, it.polimi.automata.state.State)}.
	 */
	@Test
	public void testAcceptingStatesTransformer() {
		
		List<State> states=new ArrayList<State>();
		states.add(state1);
		states.add(state2);
		states.add(state3);
		
		assertNotNull(new AcceptingStatesTransformer<State, Transition>(states, state1));
	}

	/**
	 * Test method for {@link it.polimi.contraintcomputation.brzozowski.AcceptingStatesTransformer#transform(it.polimi.automata.BA)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testTransformIllegalState() {
		List<State> states=new ArrayList<State>();
		states.add(state1);
		states.add(state2);
		states.add(state3);
		states.add(state4);
		new AcceptingStatesTransformer<State, Transition>(states, state4).transform(this.ba);
	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.brzozowski.AcceptingStatesTransformer#transform(it.polimi.automata.BA)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testTransformIllegalList() {
		List<State> states=new ArrayList<State>();
		states.add(state1);
		states.add(state2);
		states.add(state3);
		states.add(state4);
		new AcceptingStatesTransformer<State, Transition>(states, state3).transform(this.ba);
	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.brzozowski.AcceptingStatesTransformer#transform(it.polimi.automata.BA)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testTransformIllegalList2() {
		List<State> states=new ArrayList<State>();
		states.add(state1);
		states.add(state2);
		new AcceptingStatesTransformer<State, Transition>(states, state2).transform(this.ba);
	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.brzozowski.AcceptingStatesTransformer#transform(it.polimi.automata.BA)}.
	 */
	@Test
	public void testTransform() {
		List<State> states=new ArrayList<State>();
		states.add(state1);
		states.add(state2);
		states.add(state3);
		
		
		 String[] ret=new AcceptingStatesTransformer<State, Transition>(states, state2).transform(this.ba);
		 assertEquals(Constants.EMPTYSET, ret[0]);
		 assertEquals(Constants.LAMBDA, ret[1]);
		 assertEquals(Constants.EMPTYSET, ret[2]);
	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.brzozowski.AcceptingStatesTransformer#transform(it.polimi.automata.BA)}.
	 */
	@Test
	public void testTransform2() {
		List<State> states=new ArrayList<State>();
		states.add(state1);
		states.add(state2);
		states.add(state3);
		
		this.ba.addAcceptState(state2);
		
		 String[] ret=new AcceptingStatesTransformer<State, Transition>(states, state3).transform(this.ba);
		 assertEquals(Constants.EMPTYSET, ret[0]);
		 assertEquals(Constants.EMPTYSET, ret[1]);
		 assertEquals(Constants.LAMBDA, ret[2]);
	}
}
