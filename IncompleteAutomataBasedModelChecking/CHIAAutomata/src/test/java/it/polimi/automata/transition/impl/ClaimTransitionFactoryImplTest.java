/**
 * 
 */
package it.polimi.automata.transition.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.transition.Transition;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * @author claudiomenghi
 * 
 */
public class ClaimTransitionFactoryImplTest {

	private ClaimTransitionFactoryImpl<Label> claim;
	private Set<Label> labels;

	@Before
	public void setUp() {
		this.claim = new ClaimTransitionFactoryImpl<Label>();
		this.labels = new HashSet<Label>();
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.transition.impl.ClaimTransitionFactoryImpl#create()}
	 * .
	 */
	@Test
	public void testCreate() {
		assertNotNull(this.claim.create());
		assertTrue(this.claim.create().getId() >= 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.transition.impl.ClaimTransitionFactoryImpl#create(java.util.Set)}
	 * .
	 */
	@Test
	public void testCreateSetOfLABEL() {
		Transition<Label> t = this.claim.create(labels);
		assertNotNull(t);
		assertTrue(t.getId() >= 0);
		assertTrue(t.getLabels() != labels);
		assertTrue(t.getLabels().equals(labels));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.transition.impl.ClaimTransitionFactoryImpl#create(null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testCreateSetOfNull() {
		this.claim.create(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.transition.impl.ClaimTransitionFactoryImpl#create(int, java.util.Set)}
	 * .
	 * 
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	@Test
	public void testCreateIntSetOfLABEL() throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = ClaimTransitionFactoryImpl.class
				.getDeclaredField("transitionCount");
		field.setAccessible(true);
		field.set(claim, 0);
		Transition<Label> t = this.claim.create(5, labels);
		assertNotNull(t);
		assertTrue(t.getId() == 5);
		assertTrue(t.getLabels() != labels);
		assertTrue(t.getLabels().equals(labels));
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.transition.impl.ClaimTransitionFactoryImpl#create(int, null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testCreateIntSetOfLABELNull() {
		this.claim.create(1, null);
	}

	/**
	 * Test method for {@link
	 * it.polimi.automata.transition.impl.ClaimTransitionFactoryImpl#create(-1,
	 * java.util.Set)}.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateIntSetOfLABELNegativeNumber() {
		this.claim.create(-1, new HashSet<Label>());
	}

}
