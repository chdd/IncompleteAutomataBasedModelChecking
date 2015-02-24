/**
 * 
 */
package it.polimi.contraintcomputation.decorator;

import static org.junit.Assert.*;
import it.polimi.automata.state.State;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.impl.TransitionFactoryModelImpl;
import it.polimi.contraintcomputation.decorator.RegexProposition;

import org.junit.Before;
import org.junit.Test;

/**
 * @author claudiomenghi
 * 
 */
public class RegexPropositionTest {

	private State state;

	private Transition incoming;

	private Transition outcoming;

	private String regex;

	@Before
	public void setUp() {
		state = new StateFactoryImpl().create();
		incoming = new TransitionFactoryModelImpl<State>(Transition.class).create();
		outcoming = new TransitionFactoryModelImpl<State>(Transition.class).create();
		regex="";
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.decorator.RegexProposition#Proposition(null, java.lang.String, it.polimi.automata.transition.Transition, it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testPropositionNullState() {
		new RegexProposition<State, Transition>(null, regex, incoming,
				outcoming);
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.decorator.RegexProposition#Proposition(it.polimi.automata.state.State, null, it.polimi.automata.transition.Transition, it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testPropositionNullRegex() {
		new RegexProposition<State, Transition>(state, null, incoming,
				outcoming);
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.decorator.RegexProposition#Proposition(it.polimi.automata.state.State, java.lang.String, null, it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testPropositionNullIncoming() {
		new RegexProposition<State, Transition>(state, regex, null,
				outcoming);
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.decorator.RegexProposition#Proposition(it.polimi.automata.state.State, java.lang.String, it.polimi.automata.transition.Transition, null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testPropositionNullOutcoming() {
		new RegexProposition<State, Transition>(state, regex, incoming,
				null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.decorator.RegexProposition#Proposition(it.polimi.automata.state.State, java.lang.String, it.polimi.automata.transition.Transition, it.polimi.automata.transition.Transition)}
	 * .
	 */
	@Test
	public void testProposition() {
		assertNotNull(new RegexProposition<State, Transition>(state, regex, incoming,
				outcoming));
	}

	/**
	 * Test method for {@link it.polimi.contraintcomputation.decorator.RegexProposition#getLabel()}.
	 */
	@Test
	public void testGetLabel() {
		RegexProposition<State, Transition> p=new RegexProposition<State, Transition>(state, regex, incoming,
				outcoming);
		assertEquals(regex, p.getLabel());
	}

	/**
	 * Test method for {@link it.polimi.contraintcomputation.decorator.RegexProposition#getState()}.
	 */
	@Test
	public void testGetState() {
		RegexProposition<State, Transition> p=new RegexProposition<State, Transition>(state, regex, incoming,
				outcoming);
		assertEquals(state, p.getState());
	}

	/**
	 * Test method for {@link it.polimi.contraintcomputation.decorator.RegexProposition#getIncoming()}.
	 */
	@Test
	public void testGetIncoming() {
		RegexProposition<State, Transition> p=new RegexProposition< State, Transition>(state, regex, incoming,
				outcoming);
		assertEquals(incoming, p.getIncoming());
	}

	/**
	 * Test method for {@link it.polimi.contraintcomputation.decorator.RegexProposition#getOutcoming()}.
	 */
	@Test
	public void testGetOutcoming() {
		RegexProposition<State, Transition> p=new RegexProposition<State, Transition>(state, regex, incoming,
				outcoming);
		assertEquals(outcoming, p.getOutcoming());
	}

}
