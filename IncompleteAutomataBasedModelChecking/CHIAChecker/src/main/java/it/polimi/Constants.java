package it.polimi;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import org.junit.Test;

public final class Constants {
	public static final String LAMBDA="λ";
	public static final String EMPTYSET="∅";
	
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
