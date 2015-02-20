/**
 * 
 */
package it.polimi.contraintcomputation.brzozowski;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.polimi.automata.BA;
import it.polimi.Constants;
import it.polimi.automata.impl.IBAImpl;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.TransitionFactoryModelImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * @author claudiomenghi
 *
 */
public class TransitionMatrixTranformerTest {

	
	private BA<State, Transition> ba;
	private State state1;
	private State state2;
	private State state3;
	private State state4;
	private Transition t1;
	private Transition t2;
	private Transition t3;
	
	
	@Before
	public void setUp() {
		
		TransitionFactory<State, Transition> transitionFactory=new TransitionFactoryModelImpl<State>(Transition.class);
		
		this.ba=new IBAImpl<State, Transition>(transitionFactory);
		StateFactory<State> factory=new StateFactoryImpl();
		state1=factory.create();
		state2=factory.create();
		state3=factory.create();
		state4=factory.create();
		this.ba.addState(state1);
		this.ba.addState(state2);
		this.ba.addState(state3);
		
		

		Set<IGraphProposition> labelsT1=new HashSet<IGraphProposition>();
		labelsT1.add(new GraphProposition("a", false));
		t1=transitionFactory.create(labelsT1);
		
		Set<IGraphProposition> labelsT2=new HashSet<IGraphProposition>();
		labelsT2.add(new GraphProposition("b", false));
		t2=transitionFactory.create(labelsT2);
		
		Set<IGraphProposition> labelsT3=new HashSet<IGraphProposition>();
		labelsT3.add(new GraphProposition("c", false));
		t3=transitionFactory.create(labelsT3);
		
		this.ba.addCharacters(labelsT1);
		this.ba.addCharacters(labelsT2);
		this.ba.addCharacters(labelsT3);
		this.ba.addTransition(state1, state2, t1);
		this.ba.addTransition(state2, state3, t2);
		this.ba.addTransition(state3, state3, t3);
	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.brzozowski.TransitionMatrixTranformer#TransitionMatrixTranformer(null)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testTransitionMatrixTranformerNull() {
		new TransitionMatrixTranformer<State, Transition>(null);
	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.brzozowski.TransitionMatrixTranformer#TransitionMatrixTranformer(java.util.List)}.
	 */
	@Test
	public void testTransitionMatrixTranformer() {
		assertNotNull(new TransitionMatrixTranformer<State, Transition>(new ArrayList<State>()));
	}

	/**
	 * Test method for {@link it.polimi.contraintcomputation.brzozowski.TransitionMatrixTranformer#transform(null)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testTransformNull() {
		new TransitionMatrixTranformer<State, Transition>(new ArrayList<State>()).transform(null);
	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.brzozowski.TransitionMatrixTranformer#transform(null)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testTransformIllegalOrderedStates() {
		List<State> states=new ArrayList<State>();
		states.add(state1);
		states.add(state2);
		new TransitionMatrixTranformer<State, Transition>(states).transform(this.ba);
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
		new TransitionMatrixTranformer<State, Transition>(states).transform(this.ba);
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
		String[][] matrix=new TransitionMatrixTranformer<State, Transition>(states).transform(this.ba);
		assertEquals(Constants.EMPTYSET, matrix[0][0]);
		assertEquals(t1.getLabels().toString(), matrix[0][1]);
		assertEquals(Constants.EMPTYSET, matrix[0][2]);
		
		assertEquals(Constants.EMPTYSET, matrix[1][0]);
		assertEquals(Constants.EMPTYSET, matrix[1][1]);
		assertEquals(t2.getLabels().toString(), matrix[1][2]);
		
		assertEquals(Constants.EMPTYSET, matrix[2][0]);
		assertEquals(Constants.EMPTYSET, matrix[2][1]);
		assertEquals(t3.getLabels().toString(), matrix[2][2]);
	}

}
