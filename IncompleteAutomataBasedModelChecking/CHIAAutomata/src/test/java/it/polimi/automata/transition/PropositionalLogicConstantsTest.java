package it.polimi.automata.transition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;

public class PropositionalLogicConstantsTest {

	/**
	 * Test method for
	 * {@link it.polimi.automata.transition.ModelTransitionFactory#create(int, java.util.Set)}
	 * .
	 * @throws Throwable 
	 */
	@Test(expected = InstantiationException.class)
	public void testCreatePropositionalLogicConstraints()
			throws Throwable {

		final Class<?> cls = PropositionalLogicConstants.class;
		final Constructor<?> c = cls.getDeclaredConstructors()[0];
		c.setAccessible(true);
		try {
			c.newInstance((Object[]) null);
		} catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}
	}
}
