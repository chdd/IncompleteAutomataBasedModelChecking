/**
 * 
 */
package it.polimi.automata.io.in.propositions;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * @author Claudio Menghi
 *
 */
public class StringToModelPropositionsTest {

	

	/**
	 * Test method for {@link  it.polimi.automata.io.in.propositions.StringToModelPropositions#transform(String)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testTransformNull() {
		new StringToModelPropositions().transform(null);
	}

	/**
	 * Test method for {@link  it.polimi.automata.io.in.propositions.StringToModelPropositions#transform(String)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testTransformIllegalArgument() {
		new StringToModelPropositions().transform("()");
	}
	
	/**
	 * Test method for {@link  it.polimi.automata.io.in.propositions.StringToModelPropositions#transform(String)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testTransformIllegalArgumentNegatedProposition() {
		new StringToModelPropositions().transform("a^!b");
	}
	
	/**
	 * Test method for {@link  it.polimi.automata.io.in.propositions.StringToModelPropositions#transform(String)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testTransformIllegalArgumentSigma() {
		new StringToModelPropositions().transform("SIGMA");
	}
	
	/**
	 * Test method for {@link  it.polimi.automata.io.in.propositions.StringToModelPropositions#transform(String)}.
	 */
	@Test
	public void testTransformAtomicProposition() {
		assertNotNull("An atomic proposition is a valid label for a model transition", new StringToModelPropositions().transform("a"));
		
		Set<IGraphProposition> propositions=new HashSet<IGraphProposition>();
		propositions.add(new GraphProposition("a", false));
		assertEquals("An atomic proposition is a valid label for a model transition", propositions, new StringToModelPropositions().transform("a"));
	}
	
	/**
	 * Test method for {@link  it.polimi.automata.io.in.propositions.StringToModelPropositions#transform(String)}.
	 */
	@Test
	public void testTransformPropositionalFormula() {
		assertNotNull("An atomic proposition is a valid label for a model transition", new StringToModelPropositions().transform("a"));
		
		Set<IGraphProposition> propositions=new HashSet<IGraphProposition>();
		propositions.add(new GraphProposition("a", false));
		propositions.add(new GraphProposition("b", false));
		assertEquals("An atomic proposition is a valid label for a model transition", propositions, new StringToModelPropositions().transform("a^b"));
	}

}
