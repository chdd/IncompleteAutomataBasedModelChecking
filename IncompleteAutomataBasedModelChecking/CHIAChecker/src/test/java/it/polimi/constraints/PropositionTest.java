/**
 * 
 */
package it.polimi.constraints;

import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.impl.ModelTransitionFactoryImpl;

import org.junit.Before;
import org.junit.Test;

/**
 * @author claudiomenghi
 * 
 */
public class PropositionTest {

	private State state;

	private Transition<Label> incoming;

	private Transition<Label> outcoming;

	private String regex;

	@Before
	public void setUp() {
		state = new StateFactoryImpl().create();
		incoming = new ModelTransitionFactoryImpl<Label>().create();
		outcoming = new ModelTransitionFactoryImpl<Label>().create();
		regex="";
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.Proposition#Proposition(null, java.lang.String, it.polimi.automata.transition.Transition, it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testPropositionNullState() {
		new Proposition<Label, State, Transition<Label>>(null, regex, incoming,
				outcoming);
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.Proposition#Proposition(it.polimi.automata.state.State, null, it.polimi.automata.transition.Transition, it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testPropositionNullRegex() {
		new Proposition<Label, State, Transition<Label>>(state, null, incoming,
				outcoming);
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.Proposition#Proposition(it.polimi.automata.state.State, java.lang.String, null, it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testPropositionNullIncoming() {
		new Proposition<Label, State, Transition<Label>>(state, regex, null,
				outcoming);
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.Proposition#Proposition(it.polimi.automata.state.State, java.lang.String, it.polimi.automata.transition.Transition, null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testPropositionNullOutcoming() {
		new Proposition<Label, State, Transition<Label>>(state, regex, null,
				outcoming);
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.Proposition#Proposition(it.polimi.automata.state.State, java.lang.String, it.polimi.automata.transition.Transition, it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test
	public void testProposition() {
		//TODO
	}

	/**
	 * Test method for {@link it.polimi.constraints.Proposition#getLabel()}.
	 */
	@Test
	public void testGetLabel() {
		//TODO
	}

	/**
	 * Test method for {@link it.polimi.constraints.Proposition#getState()}.
	 */
	@Test
	public void testGetState() {
		//TODO
	}

	/**
	 * Test method for {@link it.polimi.constraints.Proposition#getIncoming()}.
	 */
	@Test
	public void testGetIncoming() {
		//TODO
	}

	/**
	 * Test method for {@link it.polimi.constraints.Proposition#getOutcoming()}.
	 */
	@Test
	public void testGetOutcoming() {
		//TODO
	}

}
