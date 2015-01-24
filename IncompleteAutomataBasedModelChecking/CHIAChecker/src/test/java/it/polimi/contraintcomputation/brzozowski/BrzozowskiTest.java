/**
 * 
 */
package it.polimi.contraintcomputation.brzozowski;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import it.polimi.automata.BA;
import it.polimi.automata.impl.IBAFactoryImpl;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.impl.LabelImplFactory;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.ModelTransitionFactoryImpl;

import org.junit.Before;
import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * @author claudiomenghi
 * 
 */
public class BrzozowskiTest {

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
		this.ba = new IBAFactoryImpl<Label, State, Transition<Label>>()
				.create();
		StateFactory<State> factory = new StateFactoryImpl();
		state1 = factory.create();
		state2 = factory.create();
		state3 = factory.create();
		state4 = factory.create();
		this.ba.addState(state1);
		this.ba.addState(state2);
		this.ba.addState(state3);
		TransitionFactory<Label, Transition<Label>> transitionFactory = new ModelTransitionFactoryImpl<Label>();

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
	 * Test method for
	 * {@link it.polimi.contraintcomputation.brzozowski.Brzozowski#Brzozowski(null, it.polimi.automata.state.State, it.polimi.automata.state.State)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testBrzozowskiNullAutomaton() {
		new Brzozowski<Label, State, Transition<Label>>(null, state1, state3);
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.brzozowski.Brzozowski#Brzozowski(it.polimi.automata.BA, null, it.polimi.automata.state.State)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testBrzozowskiNullInitialState() {
		new Brzozowski<Label, State, Transition<Label>>(this.ba, null, state3);
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.brzozowski.Brzozowski#Brzozowski(it.polimi.automata.BA, it.polimi.automata.state.State, null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testBrzozowskiNullFinalState() {
		new Brzozowski<Label, State, Transition<Label>>(this.ba, state3, null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.brzozowski.Brzozowski#Brzozowski(it.polimi.automata.BA, Illegal, it.polimi.automata.state.State)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testBrzozowskiIllegalInitialState() {
		new Brzozowski<Label, State, Transition<Label>>(this.ba, state4, state3);
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.brzozowski.Brzozowski#Brzozowski(it.polimi.automata.BA, it.polimi.automata.state.State, Illegal)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testBrzozowskiIllegalFinalState() {
		new Brzozowski<Label, State, Transition<Label>>(this.ba, state3, state4);
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.brzozowski.Brzozowski#Brzozowski(it.polimi.automata.BA, it.polimi.automata.state.State, it.polimi.automata.state.State)}
	 * .
	 */
	@Test
	public void testBrzozowski() {
		assertNotNull(new Brzozowski<Label, State, Transition<Label>>(this.ba,
				state1, state3));
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.brzozowski.Brzozowski#getRegularExpression()}
	 * .
	 */
	@Test
	public void testGetRegularExpression() {
		String ret = new Brzozowski<Label, State, Transition<Label>>(this.ba,
				state1, state3).getRegularExpression();
		assertEquals("[a].[b].([c])*", ret);

		TransitionFactory<Label, Transition<Label>> transitionFactory = new ModelTransitionFactoryImpl<Label>();

		Set<Label> labelsT2 = new HashSet<Label>();
		Set<IGraphProposition> propositionsT2 = new HashSet<IGraphProposition>();
		propositionsT2.add(new GraphProposition("b", false));
		Label label2 = new LabelImplFactory().create(propositionsT2);
		labelsT2.add(label2);
		Transition<Label> t4 = transitionFactory.create(labelsT2);
		this.ba.addTransition(state1, state3, t4);
		ret = new Brzozowski<Label, State, Transition<Label>>(this.ba, state1,
				state3).getRegularExpression();

		assertTrue(ret.equals("(([b])+([a].[b])).([c])*")
				|| ret.equals("(([a].[b])+([b])).([c])*"));
	}

}
