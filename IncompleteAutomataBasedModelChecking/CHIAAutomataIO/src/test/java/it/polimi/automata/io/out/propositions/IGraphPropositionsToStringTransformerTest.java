/**
 * 
 */
package it.polimi.automata.io.out.propositions;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import rwth.i2.ltl2ba4j.model.impl.SigmaProposition;

/**
 * @author Claudio Menghi
 *
 */
public class IGraphPropositionsToStringTransformerTest {

	@Mock
	private Set<IGraphProposition> propositions;

	@Mock
	private Iterator<IGraphProposition> iterator;

	public IGraphPropositionsToStringTransformerTest() {
		MockitoAnnotations.initMocks(this);
		when(propositions.iterator()).thenReturn(iterator);
		when(iterator.next()).thenReturn(new GraphProposition("a", false)).thenReturn(new GraphProposition("b", true));
		when(iterator.hasNext()).thenReturn(true).thenReturn(true)
				.thenReturn(false);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.propositions.IGraphPropositionsToStringTransformerTest#IGraphPropositionsToStringTransformerTest(BA,Map)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testNullPointerProposition() {
		new IGraphPropositionsToStringTransformer().transform(null);
	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.propositions.IGraphPropositionsToStringTransformerTest#IGraphPropositionsToStringTransformerTest(BA,Map)}
	 * .
	 */
	@Test
	public void testTransformIGraphProposition() {
		Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
		propositions.add(new GraphProposition("a", false));
		assertEquals(
				"Transforming a set of propositions which contains a single proposition returns the proposition",
				"a", new IGraphPropositionsToStringTransformer()
						.transform(propositions));

	}

	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.propositions.IGraphPropositionsToStringTransformerTest#IGraphPropositionsToStringTransformerTest(BA,Map)}
	 * .
	 */
	@Test
	public void testTransformSetIGraphProposition() {
		assertEquals(
				"Transforming a set of propositions which contains a single proposition returns the proposition",
				"a^!b", new IGraphPropositionsToStringTransformer()
						.transform(propositions));

	}
	
	/**
	 * Test method for
	 * {@link it.polimi.automata.io.out.propositions.IGraphPropositionsToStringTransformerTest#IGraphPropositionsToStringTransformerTest(BA,Map)}
	 * .
	 */
	@Test
	public void testTransformSigmaProposition() {
		Set<IGraphProposition> propositions = new HashSet<IGraphProposition>();
		propositions.add(new SigmaProposition());
		assertEquals(
				"Transforming a sigma proposition",
				"SIGMA", new IGraphPropositionsToStringTransformer()
						.transform(propositions));

	}

}
