/**
 * 
 */
package it.polimi;

import static org.junit.Assert.*;
import it.polimi.automata.Constants;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.Test;

/**
 * @author claudiomenghi
 *
 */
public class ConstantsTest {

	/**
	 * tests that the Constant class has a private constructor
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	@Test
	public void test() throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		Constructor<Constants> constructor = Constants.class
				.getDeclaredConstructor();
		constructor.setAccessible(true);
		assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		constructor.setAccessible(true);
		assertNotNull(constructor.newInstance());

	}

}
