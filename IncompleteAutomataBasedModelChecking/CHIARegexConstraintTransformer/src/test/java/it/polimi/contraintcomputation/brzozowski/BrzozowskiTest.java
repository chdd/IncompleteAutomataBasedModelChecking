/**
 * 
 */
package it.polimi.contraintcomputation.brzozowski;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import it.polimi.automata.BA;
import it.polimi.automata.impl.IBAImpl;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.TransitionFactoryClaimImpl;
import it.polimi.automata.transition.impl.TransitionFactoryModelImpl;
import it.polimi.contraintcomputation.Constants;

import org.junit.Before;
import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * @author claudiomenghi
 * 
 */
public class BrzozowskiTest {

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
		this.ba = new IBAImpl<State,Transition>(new TransitionFactoryClaimImpl<State>(Transition.class));
		StateFactory<State> factory = new StateFactoryImpl();
		state1 = factory.create();
		state2 = factory.create();
		state3 = factory.create();
		state4 = factory.create();
		this.ba.addState(state1);
		this.ba.addState(state2);
		this.ba.addState(state3);
		TransitionFactory<State, Transition> transitionFactory = new TransitionFactoryModelImpl<State>(Transition.class);

		Set<IGraphProposition> labelsT1 = new HashSet<IGraphProposition>();
		labelsT1.add(new GraphProposition("a", false));
		t1 = transitionFactory.create(labelsT1);

		Set<IGraphProposition> labelsT2 = new HashSet<IGraphProposition>();
		labelsT2.add(new GraphProposition("b", false));
		t2 = transitionFactory.create(labelsT2);

		Set<IGraphProposition> labelsT3 = new HashSet<IGraphProposition>();
		labelsT3.add(new GraphProposition("c", false));
		t3 = transitionFactory.create(labelsT3);

		this.ba.addCharacters(labelsT1);
		this.ba.addCharacters(labelsT2);
		this.ba.addCharacters(labelsT3);
		this.ba.addTransition(state1, state2, t1);
		this.ba.addTransition(state2, state3, t2);
		this.ba.addTransition(state3, state3, t3);
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.brzozowski.Brzozowski#Brzozowski(it.polimi.automata.BA, it.polimi.automata.state.State, it.polimi.automata.state.State)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testBrzozowskiNotNullStarTransformer() {
		new Brzozowski<State, Transition>(this.ba,
				state1, state3, null, new UnionTransformer(Constants.UNIONPLUS), new ConcatenateTransformer(Constants.CONCATENATIONDOT));
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.brzozowski.Brzozowski#Brzozowski(it.polimi.automata.BA, it.polimi.automata.state.State, it.polimi.automata.state.State)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testBrzozowskiNotNullUnionTransformer() {
		new Brzozowski<State, Transition>(this.ba,
				state1, state3, new StarTransformer(), null, new ConcatenateTransformer(Constants.CONCATENATIONDOT));
	}
	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.brzozowski.Brzozowski#Brzozowski(it.polimi.automata.BA, it.polimi.automata.state.State, it.polimi.automata.state.State)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testBrzozowskiNotNullConcatenateTransformer() {
		new Brzozowski<State, Transition>(this.ba,
				state1, state3, new StarTransformer(), new UnionTransformer(Constants.UNIONPLUS), null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.brzozowski.Brzozowski#Brzozowski(null, it.polimi.automata.state.State, it.polimi.automata.state.State)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testBrzozowskiNullAutomaton() {
		new Brzozowski<State, Transition>(null, state1, state3, new StarTransformer(), new UnionTransformer(Constants.UNIONPLUS), new ConcatenateTransformer(Constants.CONCATENATIONDOT));
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.brzozowski.Brzozowski#Brzozowski(it.polimi.automata.BA, null, it.polimi.automata.state.State)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testBrzozowskiNullInitialState() {
		new Brzozowski<State, Transition>(this.ba, null, state3, new StarTransformer(), new UnionTransformer(Constants.UNIONPLUS), new ConcatenateTransformer(Constants.CONCATENATIONDOT));
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.brzozowski.Brzozowski#Brzozowski(it.polimi.automata.BA, it.polimi.automata.state.State, null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testBrzozowskiNullFinalState() {
		new Brzozowski<State, Transition>(this.ba, state3, null, new StarTransformer(), new UnionTransformer(Constants.UNIONPLUS), new ConcatenateTransformer(Constants.CONCATENATIONDOT));
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.brzozowski.Brzozowski#Brzozowski(it.polimi.automata.BA, Illegal, it.polimi.automata.state.State)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testBrzozowskiIllegalInitialState() {
		new Brzozowski<State, Transition>(this.ba, state4, state3, new StarTransformer(), new UnionTransformer(Constants.UNIONPLUS), new ConcatenateTransformer(Constants.CONCATENATIONDOT));
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.brzozowski.Brzozowski#Brzozowski(it.polimi.automata.BA, it.polimi.automata.state.State, Illegal)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testBrzozowskiIllegalFinalState() {
		new Brzozowski<State, Transition>(this.ba, state3, state4, new StarTransformer(), new UnionTransformer(Constants.UNIONPLUS), new ConcatenateTransformer(Constants.CONCATENATIONDOT));
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.brzozowski.Brzozowski#Brzozowski(it.polimi.automata.BA, it.polimi.automata.state.State, it.polimi.automata.state.State)}
	 * .
	 */
	@Test
	public void testBrzozowski() {
		assertNotNull(new Brzozowski<State, Transition>(this.ba,
				state1, state3, new StarTransformer(), new UnionTransformer(Constants.UNIONPLUS), new ConcatenateTransformer(Constants.CONCATENATIONDOT)));
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.brzozowski.Brzozowski#getRegularExpression()}
	 * .
	 */
	@Test
	public void testGetRegularExpression() {
		String ret = new Brzozowski<State, Transition>(this.ba,
				state1, state3, new StarTransformer(), new UnionTransformer(Constants.UNIONPLUS), new ConcatenateTransformer(Constants.CONCATENATIONDOT)).getRegularExpression();
		assertEquals("[a].[b].([c])*", ret);

		TransitionFactory<State, Transition> transitionFactory = new TransitionFactoryClaimImpl<State>(Transition.class);

		Set<IGraphProposition> labelsT2 = new HashSet<IGraphProposition>();
		labelsT2.add(new GraphProposition("b", false));
		Transition t4 = transitionFactory.create(labelsT2);
		this.ba.addTransition(state1, state3, t4);
		ret = new Brzozowski< State, Transition>(this.ba, state1,
				state3, new StarTransformer(), new UnionTransformer(Constants.UNIONPLUS), new ConcatenateTransformer(Constants.CONCATENATIONDOT)).getRegularExpression();

		assertTrue(ret.equals("(([b])+([a].[b])).([c])*")
				|| ret.equals("(([a].[b])+([b])).([c])*")
				|| ret.equals("(([b].([c])*)+([a].[b].([c])*))"));
	}

}
