/**
 * 
 */
package it.polimi.contraintcomputation.brzozowski;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.polimi.Constants;
import it.polimi.automata.BA;
import it.polimi.automata.impl.IBAFactoryImpl;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.ModelTransitionFactoryImpl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author claudiomenghi
 *
 */
public class TransitionMatrixTranformerTest {

	
	private BA<Label, State, Transition<Label>> ba;
	private State state1;
	private State state2;
	private State state3;
	private State state4;
	private Transition<Label> t1;
	private Transition<Label> t2;
	private Transition<Label> t3;
	
	
	@Before
	public void setUp() {
		this.ba=new IBAFactoryImpl<Label, State, Transition<Label>>().create();
		StateFactory<State> factory=new StateFactoryImpl();
		state1=factory.create();
		state2=factory.create();
		state3=factory.create();
		state4=factory.create();
		this.ba.addState(state1);
		this.ba.addState(state2);
		this.ba.addState(state3);
		
		TransitionFactory<Label, Transition<Label>> transitionFactory=new ModelTransitionFactoryImpl<Label>();
		t1=transitionFactory.create();
		t2=transitionFactory.create();
		t3=transitionFactory.create();
		this.ba.addTransition(state1, state2, t1);
		this.ba.addTransition(state2, state3, t2);
		this.ba.addTransition(state3, state3, t3);
	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.brzozowski.TransitionMatrixTranformer#TransitionMatrixTranformer(null)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testTransitionMatrixTranformerNull() {
		new TransitionMatrixTranformer<Label, State, Transition<Label>>(null);
	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.brzozowski.TransitionMatrixTranformer#TransitionMatrixTranformer(java.util.List)}.
	 */
	@Test
	public void testTransitionMatrixTranformer() {
		assertNotNull(new TransitionMatrixTranformer<Label, State, Transition<Label>>(new ArrayList<State>()));
	}

	/**
	 * Test method for {@link it.polimi.contraintcomputation.brzozowski.TransitionMatrixTranformer#transform(null)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testTransformNull() {
		new TransitionMatrixTranformer<Label, State, Transition<Label>>(new ArrayList<State>()).transform(null);
	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.brzozowski.TransitionMatrixTranformer#transform(null)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testTransformIllegalOrderedStates() {
		List<State> states=new ArrayList<State>();
		states.add(state1);
		states.add(state2);
		new TransitionMatrixTranformer<Label, State, Transition<Label>>(states).transform(this.ba);
	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.brzozowski.TransitionMatrixTranformer#transform(null)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testTransformIllegalOrderedStates2() {
		List<State> states=new ArrayList<State>();
		states.add(state1);
		states.add(state2);
		states.add(state3);
		states.add(state4);
		new TransitionMatrixTranformer<Label, State, Transition<Label>>(states).transform(this.ba);
	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.brzozowski.TransitionMatrixTranformer#transform(it.polimi.automata.BA)}.
	 */
	@Test
	public void testTransform() {
		List<State> states=new ArrayList<State>();
		states.add(state1);
		states.add(state2);
		states.add(state3);
		String[][] matrix=new TransitionMatrixTranformer<Label, State, Transition<Label>>(states).transform(this.ba);
		assertEquals(Constants.EMPTYSET, matrix[0][0]);
		assertEquals(t1.toString(), matrix[0][1]);
		assertEquals(Constants.EMPTYSET, matrix[0][2]);
		
		assertEquals(Constants.EMPTYSET, matrix[1][0]);
		assertEquals(Constants.EMPTYSET, matrix[1][1]);
		assertEquals(t2.toString(), matrix[1][2]);
		
		assertEquals(Constants.EMPTYSET, matrix[2][0]);
		assertEquals(Constants.EMPTYSET, matrix[2][1]);
		assertEquals(t3.toString(), matrix[2][2]);
	}

}
