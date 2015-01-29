/**
 * 
 */
package it.polimi.contraintcomputation.brzozowski;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.polimi.automata.BA;
import it.polimi.Constants;
import it.polimi.automata.impl.IBAFactoryImpl;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.impl.LabelImplFactory;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.ModelTransitionFactoryImpl;

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
		

		Set<Label> labelsT1=new HashSet<Label>();
		Set<IGraphProposition> propositionsT1=new HashSet<IGraphProposition>();
		propositionsT1.add(new GraphProposition("a", false));
		Label label1=new LabelImplFactory().create(propositionsT1);
		labelsT1.add(label1);
		t1=transitionFactory.create(labelsT1);
		
		Set<Label> labelsT2=new HashSet<Label>();
		Set<IGraphProposition> propositionsT2=new HashSet<IGraphProposition>();
		propositionsT2.add(new GraphProposition("b", false));
		Label label2=new LabelImplFactory().create(propositionsT1);
		labelsT2.add(label2);
		t2=transitionFactory.create(labelsT2);
		
		Set<Label> labelsT3=new HashSet<Label>();
		Set<IGraphProposition> propositionsT3=new HashSet<IGraphProposition>();
		propositionsT3.add(new GraphProposition("c", false));
		Label label3=new LabelImplFactory().create(propositionsT1);
		labelsT3.add(label3);
		t3=transitionFactory.create(labelsT3);
		
		this.ba.addCharacter(label1);
		this.ba.addCharacter(label2);
		this.ba.addCharacter(label3);
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
