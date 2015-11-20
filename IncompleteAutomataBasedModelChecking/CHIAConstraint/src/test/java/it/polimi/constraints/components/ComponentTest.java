/**
 * 
 */
package it.polimi.constraints.components;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.constraints.transitions.PluggingTransition;

import org.junit.Test;

/**
 * @author Claudio1
 *
 */
public class ComponentTest {

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.Component#Component(it.polimi.automata.state.State)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testComponentNullState() {
		new Replacement(null, new IBA(new ModelTransitionFactory()),
				new HashSet<PluggingTransition>(),
				new HashSet<PluggingTransition>());
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.Component#Component(it.polimi.automata.state.State)}
	 * .
	 */
	@Test
	public void testComponent() {

		assertNotNull(new Replacement(new StateFactory().create(), new IBA(
				new ModelTransitionFactory()),
				new HashSet<PluggingTransition>(),
				new HashSet<PluggingTransition>()));
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.Component#getId()}.
	 */
	@Test
	public void testGetId() {
		assertTrue(new Replacement(new StateFactory().create(), new IBA(
				new ModelTransitionFactory()),
				new HashSet<PluggingTransition>(),
				new HashSet<PluggingTransition>()).getId() >= 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.Component#getModelState()}.
	 */
	@Test
	public void testGetModelState() {
		State state = new StateFactory().create();
		Replacement repl = new Replacement(state, new IBA(
				new ModelTransitionFactory()),
				new HashSet<PluggingTransition>(),
				new HashSet<PluggingTransition>());
		assertTrue(state == repl.getModelState());
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.Component#toString()}.
	 */
	@Test
	public void testToString() {
		State state = new StateFactory().create();

		assertNotNull(new Replacement(state, new IBA(
				new ModelTransitionFactory()),
				new HashSet<PluggingTransition>(),
				new HashSet<PluggingTransition>()).toString());
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.Component#equals(java.lang.Object)}
	 * .
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	@Test
	public void testEqualsObject() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		State state = new StateFactory().create();

		State ibaState = new StateFactory().create();
		IBA iba = new IBA(new ModelTransitionFactory());
		iba.addState(ibaState);
		Set<PluggingTransition> inTransitions = new HashSet<PluggingTransition>();
		inTransitions.add(new PluggingTransition(new StateFactory().create(),
				ibaState, new ModelTransitionFactory().create(), true));
		assertFalse(new Replacement(state, iba,
				new HashSet<PluggingTransition>(),
				new HashSet<PluggingTransition>()).equals(new Replacement(
				state, iba, inTransitions, new HashSet<PluggingTransition>())));

		Replacement rep = new Replacement(state, iba,
				new HashSet<PluggingTransition>(),
				new HashSet<PluggingTransition>());
		assertTrue(rep.equals(rep));
		assertFalse(rep.equals(null));

		assertFalse(rep.equals(new Replacement(state, iba, inTransitions,
				new HashSet<PluggingTransition>())));

		assertFalse(rep.equals(new Replacement(new StateFactory().create(),
				iba, inTransitions, new HashSet<PluggingTransition>())));

		assertFalse(rep.equals(new Replacement(state,
				iba, inTransitions, new HashSet<PluggingTransition>())));
		
		Replacement rep1=new Replacement(state,
				iba, inTransitions, new HashSet<PluggingTransition>());
		
		Field privateStringField =rep1.getClass().getSuperclass().getDeclaredField("id");
		
		privateStringField.setAccessible(true);
	
		privateStringField.set(rep1, rep.getId());
		
		assertTrue(rep.equals(rep1));
		
	}

	/**
	 * Test method for
	 * {@link it.polimi.constraints.components.Component#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		State state = new StateFactory().create();

		State ibaState = new StateFactory().create();

		IBA iba = new IBA(new ModelTransitionFactory());
		iba.addState(ibaState);
		Set<PluggingTransition> inTransitions = new HashSet<PluggingTransition>();
		inTransitions.add(new PluggingTransition(ibaState, ibaState,
				new ModelTransitionFactory().create(), true));
		assertFalse(new Replacement(state, iba,
				new HashSet<PluggingTransition>(),
				new HashSet<PluggingTransition>()).hashCode() == new Replacement(
				state, iba, inTransitions, new HashSet<PluggingTransition>())
				.hashCode());
		assertFalse(new Replacement(state, iba,
				new HashSet<PluggingTransition>(),
				new HashSet<PluggingTransition>()).equals(new StateFactory()
				.create()));
	}

}
