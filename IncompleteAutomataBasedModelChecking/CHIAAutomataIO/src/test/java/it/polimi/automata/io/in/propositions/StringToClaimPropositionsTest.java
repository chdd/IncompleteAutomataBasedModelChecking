package it.polimi.automata.io.in.propositions;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import rwth.i2.ltl2ba4j.model.impl.SigmaProposition;

public class StringToClaimPropositionsTest {

	/**
	 * Test method for {@link  it.polimi.automata.io.in.propositions.StringToClaimPropositions#transform(String)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testTransformNull() {
		new StringToClaimPropositions().transform(null);
	}

	/**
	 * Test method for {@link  it.polimi.automata.io.in.propositions.StringToClaimPropositions#transform(String)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testTransformIllegalArgument() {
		assertNotNull(new StringToClaimPropositions().transform("()"));
	}

	/**
	 * Test method for {@link  it.polimi.automata.io.in.propositions.StringToClaimPropositions#transform(String)}.
	 */
	@Test
	public void testTransformAtomicProposition() {
		assertNotNull("An atomic proposition is a valid label for a claim transition", new StringToClaimPropositions().transform("a"));
		
		Set<IGraphProposition> propositions=new HashSet<IGraphProposition>();
		propositions.add(new GraphProposition("a", false));
		assertEquals("An atomic proposition is a valid label for a claim transition", propositions, new StringToClaimPropositions().transform("a"));
	}
	
	/**
	 * Test method for {@link  it.polimi.automata.io.in.propositions.StringToClaimPropositions#transform(String)}.
	 */
	@Test
	public void testTransformSigma() {
		
		Set<IGraphProposition> propositions=new HashSet<IGraphProposition>();
		propositions.add(new SigmaProposition());
		assertEquals("A transition of the claim can be labeled with the sigma expression", propositions, new StringToClaimPropositions().transform("SIGMA"));
	}
	
	/**
	 * Test method for {@link  it.polimi.automata.io.in.propositions.StringToClaimPropositions#transform(String)}.
	 */
	@Test
	public void testTransformPropositionalFormula() {
		
		Set<IGraphProposition> propositions=new HashSet<IGraphProposition>();
		propositions.add(new GraphProposition("a", false));
		propositions.add(new GraphProposition("b", false));
		assertEquals("A transition of the claim can be labeled with the sigma expression", propositions, new StringToClaimPropositions().transform("a^b"));
		
	}
	
	/**
	 * Test method for {@link  it.polimi.automata.io.in.propositions.StringToClaimPropositions#transform(String)}.
	 */
	@Test
	public void testTransformPropositionalFormulaNegatedProposition() {
		
		Set<IGraphProposition> propositions=new HashSet<IGraphProposition>();
		propositions.add(new GraphProposition("a", false));
		propositions.add(new GraphProposition("b", true));
		assertEquals("A transition of the claim can be labeled with the sigma expression", propositions, new StringToClaimPropositions().transform("a^!b"));
		
	}
}
