/**
 * 
 */
package it.polimi.contraintcomputation;

import static org.junit.Assert.*;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.impl.IntBAFactoryImpl;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.LabelFactory;
import it.polimi.automata.labeling.impl.LabelImplFactory;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.ModelTransitionFactoryImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * @author claudiomenghi
 *
 */
public class ConstraintGeneratorTest {

	private IntersectionBA<Label, State, Transition<Label>> ba;
	private State state1;
	private State state2;
	private State state3;
	private State state4;
	private Transition<Label> t1;
	private Transition<Label> t2;
	private Transition<Label> t3;
	
	private Map<State, Set<State>> modelIntersectionStatesMap;
	private TransitionFactory<Label, Transition<Label>> transitionFactory;
	
	private StateFactory<State> stateFactory;
	
	private LabelFactory<Label> labelFactory;

	@Before
	public void setUp() {
		this.labelFactory=new LabelImplFactory();
		this.ba = new IntBAFactoryImpl<Label, State, Transition<Label>>().create();
		stateFactory = new StateFactoryImpl();
		state1 = stateFactory.create();
		state2 = stateFactory.create();
		state3 = stateFactory.create();
		state4 = stateFactory.create();
		modelIntersectionStatesMap=new HashMap<State, Set<State>>();
		this.ba.addState(state1);
		this.ba.addState(state2);
		this.ba.addState(state3);
		transitionFactory = new ModelTransitionFactoryImpl<Label>();

		Set<Label> labelsT1 = new HashSet<Label>();
		Set<IGraphProposition> propositionsT1 = new HashSet<IGraphProposition>();
		propositionsT1.add(new GraphProposition("a", false));
		Label label1 = new LabelImplFactory().create(propositionsT1);
		labelsT1.add(label1);
		t1 = transitionFactory.create(labelsT1);

		Set<Label> labelsT2 = new HashSet<Label>();
		Set<IGraphProposition> propositionsT2 = new HashSet<IGraphProposition>();
		propositionsT2.add(new GraphProposition("b", false));
		Label label2 = new LabelImplFactory().create(propositionsT2);
		labelsT2.add(label2);
		t2 = transitionFactory.create(labelsT2);

		Set<Label> labelsT3 = new HashSet<Label>();
		Set<IGraphProposition> propositionsT3 = new HashSet<IGraphProposition>();
		propositionsT3.add(new GraphProposition("c", false));
		Label label3 = new LabelImplFactory().create(propositionsT3);
		labelsT3.add(label3);
		t3 = transitionFactory.create(labelsT3);

		this.ba.addCharacter(label1);
		this.ba.addCharacter(label2);
		this.ba.addCharacter(label3);
		this.ba.addTransition(state1, state2, t1);
		this.ba.addTransition(state2, state3, t2);
		this.ba.addTransition(state3, state3, t3);
	}
	
	
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.ConstraintGenerator#ConstraintGenerator(null, java.util.Map, it.polimi.automata.labeling.LabelFactory, it.polimi.automata.state.StateFactory, it.polimi.automata.transition.TransitionFactory)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testConstraintGeneratorNullBA() {
		new ConstraintGenerator<Label, State, Transition<Label>> (null, modelIntersectionStatesMap, labelFactory, stateFactory, transitionFactory);
	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.ConstraintGenerator#ConstraintGenerator(it.polimi.automata.IntersectionBA, null, it.polimi.automata.labeling.LabelFactory, it.polimi.automata.state.StateFactory, it.polimi.automata.transition.TransitionFactory)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testConstraintGeneratorNullMap() {
		new ConstraintGenerator<Label, State, Transition<Label>> (ba, null, labelFactory, stateFactory, transitionFactory);
	}
	

	/**
	 * Test method for {@link it.polimi.contraintcomputation.ConstraintGenerator#ConstraintGenerator(it.polimi.automata.IntersectionBA, java.util.Map, null, it.polimi.automata.state.StateFactory, it.polimi.automata.transition.TransitionFactory)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testConstraintGeneratorNullLabelFactory() {
		new ConstraintGenerator<Label, State, Transition<Label>> (ba, modelIntersectionStatesMap, null, stateFactory, transitionFactory);
	}
	

	/**
	 * Test method for {@link it.polimi.contraintcomputation.ConstraintGenerator#ConstraintGenerator(it.polimi.automata.IntersectionBA, java.util.Map, it.polimi.automata.labeling.LabelFactory, null, it.polimi.automata.transition.TransitionFactory)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testConstraintGeneratorNullStateFactory() {
		new ConstraintGenerator<Label, State, Transition<Label>> (ba, modelIntersectionStatesMap, labelFactory, null, transitionFactory);
	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.ConstraintGenerator#ConstraintGenerator(it.polimi.automata.IntersectionBA, java.util.Map, it.polimi.automata.labeling.LabelFactory, it.polimi.automata.state.StateFactory, null)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testConstraintGeneratorNullTransitionFactory() {
		new ConstraintGenerator<Label, State, Transition<Label>> (ba, modelIntersectionStatesMap, labelFactory, stateFactory, null);
	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.ConstraintGenerator#ConstraintGenerator(it.polimi.automata.IntersectionBA, java.util.Map, it.polimi.automata.labeling.LabelFactory, it.polimi.automata.state.StateFactory, it.polimi.automata.transition.TransitionFactory)}.
	 */
	@Test
	public void testConstraintGenerator() {
		assertNotNull(new ConstraintGenerator<Label, State, Transition<Label>> (ba, modelIntersectionStatesMap, labelFactory, stateFactory, transitionFactory));
	}

	/**
	 * Test method for {@link it.polimi.contraintcomputation.ConstraintGenerator#generateConstraint()}.
	 */
	@Test
	public void testGenerateConstraint() {
		fail("Not yet implemented");
	}

}
