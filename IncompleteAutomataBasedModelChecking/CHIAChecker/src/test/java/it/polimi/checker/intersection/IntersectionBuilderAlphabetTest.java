package it.polimi.checker.intersection;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;

import org.junit.Before;
import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import rwth.i2.ltl2ba4j.model.impl.SigmaProposition;

public class IntersectionBuilderAlphabetTest {

	
	private BA claim;

	private IBA model;
	
	

	@Before
	public void setUp() {
		this.claim = new BA(new ClaimTransitionFactory());
		this.claim.addProposition(new GraphProposition("a", false));
		this.model = new IBA(new ModelTransitionFactory());
		this.model.addProposition(new GraphProposition("b", false));
		
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionBuilder#getAcceptingPolicy()}
	 * .
	 */
	@Test
	public void test() {
		
		Set<IGraphProposition> propositions=new HashSet<IGraphProposition>();
		propositions.add(new GraphProposition("a", false));
		propositions.add(new GraphProposition("b", false));
		propositions.add(new SigmaProposition());
		
		AcceptingPolicy acceptingPolicy=
				AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model, claim);
		IntersectionBA intersection= new IntersectionBuilder(model, claim, acceptingPolicy).perform();
		assertEquals("The propositions are correctly computed", propositions,intersection.getPropositions());
	}

}
