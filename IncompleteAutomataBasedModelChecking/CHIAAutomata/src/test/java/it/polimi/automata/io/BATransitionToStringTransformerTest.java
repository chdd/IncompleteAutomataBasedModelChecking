/**
 * 
 */
package it.polimi.automata.io;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import it.polimi.Constants;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.transition.Transition;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author claudiomenghi
 *
 */
public class BATransitionToStringTransformerTest {


	@Mock
	private Transition<Label> t;
	
	@Mock
	private Label l1;

	@Mock
	private Label l2;
	
	private Set<Label> labels;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		labels=new HashSet<Label>();
		labels.add(l1);
		labels.add(l2);
		when(t.getLabels()).thenReturn(labels);
		when(l1.toString()).thenReturn("a");
		when(l2.toString()).thenReturn("b");
	}
	
	/**
	 * Test method for {@link it.polimi.automata.io.BATransitionToStringTransformer#transform(null)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testTransformNull() {
		new BATransitionToStringTransformer<Label, Transition<Label>>().transform(null);
	}
	
	/**
	 * Test method for {@link it.polimi.automata.io.BATransitionToStringTransformer#transform(it.polimi.automata.transition.Transition)}.
	 */
	@Test
	public void testTransform() {
		BATransitionToStringTransformer<Label, Transition<Label>> transformer=new BATransitionToStringTransformer<Label, Transition<Label>>();
		String ret=transformer.transform(t);
		assertTrue(ret.equals("(a)"+Constants.OR+"(b)") || ret.equals("(b)"+Constants.OR+"(a)"));
	}

}
