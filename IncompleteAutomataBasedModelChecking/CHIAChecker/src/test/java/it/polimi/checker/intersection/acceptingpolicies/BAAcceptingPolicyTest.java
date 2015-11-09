/**
 * 
 */
package it.polimi.checker.intersection.acceptingpolicies;

import static org.junit.Assert.*;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.ModelTransitionFactory;

import org.junit.Test;

/**
 * @author Claudio Menghi
 *
 */
public class BAAcceptingPolicyTest {

	private IBA model;
	private BA claim;

	public BAAcceptingPolicyTest() {
		this.model = new IBA(new ModelTransitionFactory());
		this.claim = new BA(new ClaimTransitionFactory());
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.BAAcceptingPolicy#BAAcceptingPolicy(it.polimi.automata.IBA, it.polimi.automata.BA)}
	 * .
	 */
	@Test
	public void testBAAcceptingPolicy() {
		assertNotNull("It must be possible to create a BA accepting policy",
				new BAAcceptingPolicy(model, claim));
	}

	
	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.BAAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testComuteNumberNullClaimState() {

		State claimState = new StateFactory().create();
		BA claim = new BA(new ClaimTransitionFactory());
		claim.addState(claimState);
		new BAAcceptingPolicy(model, claim).comuteNumber(null, claimState, 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.BAAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testComuteNumberNullModelState() {

		State modelState = new StateFactory().create();
		IBA model = new IBA(new ModelTransitionFactory());
		model.addState(modelState);
		new BAAcceptingPolicy(model, claim).comuteNumber(modelState, null, 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.BAAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testComuteNumberClaimStateNotInTheSetOfStates() {

		State modelState = new StateFactory().create();
		IBA model = new IBA(new ModelTransitionFactory());
		model.addState(modelState);
		new BAAcceptingPolicy(model, claim).comuteNumber(modelState,
				new StateFactory().create(), 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.BAAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testComuteNumberModelStateNotInTheSetOfStates() {

		State claimState = new StateFactory().create();
		BA claim = new BA(new ClaimTransitionFactory());
		claim.addState(claimState);
		new BAAcceptingPolicy(model, claim).comuteNumber(
				new StateFactory().create(), claimState, 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.BAAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testComuteNumberNumberOutOfLowerBound() {

		State claimState = new StateFactory().create();
		BA claim = new BA(new ClaimTransitionFactory());
		claim.addState(claimState);
		State modelState = new StateFactory().create();
		IBA model = new IBA(new ModelTransitionFactory());
		model.addState(modelState);
		new BAAcceptingPolicy(model, claim).comuteNumber(modelState,
				claimState, -1);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.BAAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testComuteNumberNumberOutOfUpperBound() {

		State claimState = new StateFactory().create();
		BA claim = new BA(new ClaimTransitionFactory());
		claim.addState(claimState);
		State modelState = new StateFactory().create();
		IBA model = new IBA(new ModelTransitionFactory());
		model.addState(modelState);
		new BAAcceptingPolicy(model, claim).comuteNumber(modelState,
				claimState, 3);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.BAAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
	 * .
	 */
	@Test
	public void testComuteNumberOne() {
		State claimState = new StateFactory().create();
		BA claim = new BA(new ClaimTransitionFactory());
		claim.addState(claimState);
		State modelState = new StateFactory().create();
		IBA model = new IBA(new ModelTransitionFactory());
		model.addAcceptState(modelState);
		assertEquals("When the initial value is 0 and I enter an accepting state of the model the reached number is 1",
				1,
				new BAAcceptingPolicy(model, claim).comuteNumber(modelState,
						claimState, 0));
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.BAAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
	 * .
	 */
	@Test
	public void testComuteNumberOneb() {
		State claimState = new StateFactory().create();
		BA claim = new BA(new ClaimTransitionFactory());
		claim.addAcceptState(claimState);
		State modelState = new StateFactory().create();
		IBA model = new IBA(new ModelTransitionFactory());
		model.addAcceptState(modelState);
		assertEquals("When the initial value is 0 and I enter an accepting state of the model the reached number is 1",
				1,
				new BAAcceptingPolicy(model, claim).comuteNumber(modelState,
						claimState, 0));
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.BAAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
	 * .
	 */
	@Test
	public void testComuteNumberOnec() {
		State claimState = new StateFactory().create();
		BA claim = new BA(new ClaimTransitionFactory());
		claim.addState(claimState);
		State modelState = new StateFactory().create();
		IBA model = new IBA(new ModelTransitionFactory());
		model.addAcceptState(modelState);
		assertEquals("When the initial value is 1 and I enter an state of the claim that is not accepting the reached number is 1",
				1,
				new BAAcceptingPolicy(model, claim).comuteNumber(modelState,
						claimState, 1));
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.BAAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
	 * .
	 */
	@Test
	public void testComuteNumberTwo() {
		State claimState = new StateFactory().create();
		BA claim = new BA(new ClaimTransitionFactory());
		claim.addAcceptState(claimState);
		State modelState = new StateFactory().create();
		IBA model = new IBA(new ModelTransitionFactory());
		model.addState(modelState);
		assertEquals("When the initial value is 1 and I enter an accepting state of the claim the reached number is 2",
				2,
				new BAAcceptingPolicy(model, claim).comuteNumber(modelState,
						claimState, 1));
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.BAAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
	 * .
	 */
	@Test
	public void testComuteNumberTwob() {
		State claimState = new StateFactory().create();
		BA claim = new BA(new ClaimTransitionFactory());
		claim.addAcceptState(claimState);
		State modelState = new StateFactory().create();
		IBA model = new IBA(new ModelTransitionFactory());
		model.addAcceptState(modelState);
		assertEquals("When the initial value is 1 and I enter an accepting state of the claim the reached number is 1",
				2,
				new BAAcceptingPolicy(model, claim).comuteNumber(modelState,
						claimState, 1));
	}
	
	
	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.BAAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
	 * .
	 */
	@Test
	public void testComuteNumberZero() {
		State claimState = new StateFactory().create();
		BA claim = new BA(new ClaimTransitionFactory());
		claim.addAcceptState(claimState);
		State modelState = new StateFactory().create();
		IBA model = new IBA(new ModelTransitionFactory());
		model.addState(modelState);
		assertEquals("When the initial value is 2 the reached number is 0",
				0,
				new BAAcceptingPolicy(model, claim).comuteNumber(modelState,
						claimState, 2));
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.BAAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
	 * .
	 */
	@Test
	public void testComuteNumberZerob() {
		State claimState = new StateFactory().create();
		BA claim = new BA(new ClaimTransitionFactory());
		claim.addAcceptState(claimState);
		State modelState = new StateFactory().create();
		IBA model = new IBA(new ModelTransitionFactory());
		model.addAcceptState(modelState);
		assertEquals("When the initial value is 2 the reached number is 0",
				0,
				new BAAcceptingPolicy(model, claim).comuteNumber(modelState,
						claimState, 2));
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.BAAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
	 * .
	 */
	@Test
	public void testComuteNumberZeroc() {
		State claimState = new StateFactory().create();
		BA claim = new BA(new ClaimTransitionFactory());
		claim.addAcceptState(claimState);
		State modelState = new StateFactory().create();
		IBA model = new IBA(new ModelTransitionFactory());
		model.addState(modelState);
		assertEquals("When the initial value is 0 and I enter a state of the model that is not accepting the reached number is 0",
				0,
				new BAAcceptingPolicy(model, claim).comuteNumber(modelState,
						claimState, 0));
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.BAAcceptingPolicy#comuteInitNumber(it.polimi.automata.state.State, it.polimi.automata.state.State)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testComuteInitNumberNullClaimState() {
		State modelState = new StateFactory().create();
		IBA model = new IBA(new ModelTransitionFactory());
		model.addState(modelState);
		new BAAcceptingPolicy(model, claim).comuteInitNumber(modelState, null);
	}
	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.BAAcceptingPolicy#comuteInitNumber(it.polimi.automata.state.State, it.polimi.automata.state.State)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testComuteInitNumberNullModelState() {
		// fail("Not yet implemented");
			State claimState = new StateFactory().create();
		BA claim = new BA(new ClaimTransitionFactory());
		claim.addState(claimState);
		new BAAcceptingPolicy(model, claim).comuteInitNumber(null, claimState);
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.BAAcceptingPolicy#comuteInitNumber(it.polimi.automata.state.State, it.polimi.automata.state.State)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testComuteInitNumberNotContainedClaimState() {
		State modelState = new StateFactory().create();
		IBA model = new IBA(new ModelTransitionFactory());
		model.addState(modelState);
		new BAAcceptingPolicy(model, claim).comuteInitNumber(modelState, new StateFactory().create());
	}
	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.BAAcceptingPolicy#comuteInitNumber(it.polimi.automata.state.State, it.polimi.automata.state.State)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testComuteInitNumberNotContainedModelState() {
		// fail("Not yet implemented");
			State claimState = new StateFactory().create();
		BA claim = new BA(new ClaimTransitionFactory());
		claim.addState(claimState);
		new BAAcceptingPolicy(model, claim).comuteInitNumber(new StateFactory().create(), claimState);
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.BAAcceptingPolicy#comuteInitNumber(it.polimi.automata.state.State, it.polimi.automata.state.State)}
	 * .
	 */
	@Test
	public void testComuteInitNumber() {
		// fail("Not yet implemented");
			State claimState = new StateFactory().create();
		BA claim = new BA(new ClaimTransitionFactory());
		claim.addState(claimState);
		
		State modelState = new StateFactory().create();
		IBA model = new IBA(new ModelTransitionFactory());
		model.addState(modelState);
		assertEquals("The BAAccepting policy returns 0 as initial value", 0, new BAAcceptingPolicy(model, claim).comuteInitNumber(modelState, claimState));
	}
}
