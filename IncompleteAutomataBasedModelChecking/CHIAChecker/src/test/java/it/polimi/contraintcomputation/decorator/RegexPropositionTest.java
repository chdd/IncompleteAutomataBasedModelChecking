/**
 * 
 */
package it.polimi.contraintcomputation.decorator;

import static org.junit.Assert.*;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.impl.ModelTransitionFactoryImpl;
import it.polimi.contraintcomputation.decorator.RegexProposition;

import org.junit.Before;
import org.junit.Test;

/**
 * @author claudiomenghi
 * 
 */
public class RegexPropositionTest {

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
	 * {@link it.polimi.contraintcomputation.decorator.RegexProposition#Proposition(null, java.lang.String, it.polimi.automata.transition.Transition, it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testPropositionNullState() {
		new RegexProposition<Label, State, Transition<Label>>(null, regex, incoming,
				outcoming);
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.decorator.RegexProposition#Proposition(it.polimi.automata.state.State, null, it.polimi.automata.transition.Transition, it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testPropositionNullRegex() {
		new RegexProposition<Label, State, Transition<Label>>(state, null, incoming,
				outcoming);
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.decorator.RegexProposition#Proposition(it.polimi.automata.state.State, java.lang.String, null, it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testPropositionNullIncoming() {
		new RegexProposition<Label, State, Transition<Label>>(state, regex, null,
				outcoming);
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.decorator.RegexProposition#Proposition(it.polimi.automata.state.State, java.lang.String, it.polimi.automata.transition.Transition, null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testPropositionNullOutcoming() {
		new RegexProposition<Label, State, Transition<Label>>(state, regex, incoming,
				null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.decorator.RegexProposition#Proposition(it.polimi.automata.state.State, java.lang.String, it.polimi.automata.transition.Transition, it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test
	public void testProposition() {
		assertNotNull(new RegexProposition<Label, State, Transition<Label>>(state, regex, incoming,
				outcoming));
	}

	/**
	 * Test method for {@link it.polimi.contraintcomputation.decorator.RegexProposition#getLabel()}.
	 */
	@Test
	public void testGetLabel() {
		RegexProposition<Label, State, Transition<Label>> p=new RegexProposition<Label, State, Transition<Label>>(state, regex, incoming,
				outcoming);
		assertEquals(regex, p.getLabel());
	}

	/**
	 * Test method for {@link it.polimi.contraintcomputation.decorator.RegexProposition#getState()}.
	 */
	@Test
	public void testGetState() {
		RegexProposition<Label, State, Transition<Label>> p=new RegexProposition<Label, State, Transition<Label>>(state, regex, incoming,
				outcoming);
		assertEquals(state, p.getState());
	}

	/**
	 * Test method for {@link it.polimi.contraintcomputation.decorator.RegexProposition#getIncoming()}.
	 */
	@Test
	public void testGetIncoming() {
		RegexProposition<Label, State, Transition<Label>> p=new RegexProposition<Label, State, Transition<Label>>(state, regex, incoming,
				outcoming);
		assertEquals(incoming, p.getIncoming());
	}

	/**
	 * Test method for {@link it.polimi.contraintcomputation.decorator.RegexProposition#getOutcoming()}.
	 */
	@Test
	public void testGetOutcoming() {
		RegexProposition<Label, State, Transition<Label>> p=new RegexProposition<Label, State, Transition<Label>>(state, regex, incoming,
				outcoming);
		assertEquals(outcoming, p.getOutcoming());
	}

}
