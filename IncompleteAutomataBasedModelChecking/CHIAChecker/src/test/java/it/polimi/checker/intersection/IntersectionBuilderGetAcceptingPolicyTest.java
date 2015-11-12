package it.polimi.checker.intersection;

import static org.junit.Assert.assertTrue;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;

import org.junit.Before;
import org.junit.Test;

public class IntersectionBuilderGetAcceptingPolicyTest {

	
	private BA claim;

	private IBA model;

	@Before
	public void setUp() {
		this.claim = new BA(new ClaimTransitionFactory());
		this.model = new IBA(new ModelTransitionFactory());

		
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.IntersectionBuilder#getAcceptingPolicy()}
	 * .
	 */
	@Test
	public void test() {
		AcceptingPolicy acceptingPolicy=
				AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model, claim);
		assertTrue(acceptingPolicy==new IntersectionBuilder(model, claim, acceptingPolicy).getAcceptingPolicy());
	}

}
