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
public class KripkeAcceptingPolicyTest {

	private IBA model;
	private BA claim;

	public KripkeAcceptingPolicyTest() {
		this.model = new IBA(new ModelTransitionFactory());
		this.claim = new BA(new ClaimTransitionFactory());
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.KripkeAcceptingPolicy#KripkeAcceptingPolicy()}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testKripkeAcceptingPolicyNullModel() {
		assertNotNull(
				"The object related to the Kripke accepting policy is correctly created",
				new KripkeAcceptingPolicy(null, claim));
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.KripkeAcceptingPolicy#KripkeAcceptingPolicy()}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testKripkeAcceptingPolicyNullClaim() {
		assertNotNull(
				"The object related to the Kripke accepting policy is correctly created",
				new KripkeAcceptingPolicy(model, null));
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.KripkeAcceptingPolicy#KripkeAcceptingPolicy()}
	 * .
	 */
	@Test
	public void testKripkeAcceptingPolicy() {
		assertNotNull(
				"The object related to the Kripke accepting policy is correctly created",
				new KripkeAcceptingPolicy(model, claim));
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.KripkeAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testComuteNumberNullClaimState() {
		new KripkeAcceptingPolicy(model, claim).comuteNumber(
				new StateFactory().create(), null, 1);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.KripkeAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testComuteNumberNullModelState() {
		new KripkeAcceptingPolicy(model, claim).comuteNumber(null,
				new StateFactory().create(), 1);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.KripkeAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testComuteNumberIllegalArgument0() {
		new KripkeAcceptingPolicy(model, claim).comuteNumber(
				new StateFactory().create(), new StateFactory().create(), 0);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.KripkeAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testComuteNumberIllegalArgument2() {
		new KripkeAcceptingPolicy(model, claim).comuteNumber(
				new StateFactory().create(), new StateFactory().create(), 3);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.KripkeAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testComuteNumberStateOfTheClaimNotIncluded() {
		IBA model = new IBA(new ModelTransitionFactory());
		BA claim = new BA(new ClaimTransitionFactory());
		State stateModel = new StateFactory().create();
		model.addState(stateModel);
		new KripkeAcceptingPolicy(model, claim).comuteNumber(stateModel,
				new StateFactory().create(), 3);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.KripkeAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testComuteNumberNotIncluded() {
		IBA model = new IBA(new ModelTransitionFactory());
		BA claim = new BA(new ClaimTransitionFactory());
		State stateClaim = new StateFactory().create();
		claim.addState(stateClaim);
		State stateModel = new StateFactory().create();
		model.addState(stateModel);
		new KripkeAcceptingPolicy(model, claim).comuteNumber(
				stateModel, stateClaim, -1);
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.KripkeAcceptingPolicy#comuteNumber(it.polimi.automata.state.State, it.polimi.automata.state.State, int)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testComuteNumberStateOfTheModelNotIncluded() {
		IBA model = new IBA(new ModelTransitionFactory());
		BA claim = new BA(new ClaimTransitionFactory());
		State stateClaim = new StateFactory().create();
		claim.addState(stateClaim);
		State stateModel = new StateFactory().create();
		model.addState(stateModel);
		new KripkeAcceptingPolicy(model, claim).comuteNumber(
				stateModel, stateClaim, 3);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.KripkeAcceptingPolicy#comuteInitNumber(it.polimi.automata.state.State, it.polimi.automata.state.State)}
	 * .
	 */
	@Test
	public void testComuteNumber() {
		IBA model = new IBA(new ModelTransitionFactory());
		BA claim = new BA(new ClaimTransitionFactory());
		State stateClaim = new StateFactory().create();
		claim.addState(stateClaim);
		State stateModel = new StateFactory().create();
		model.addState(stateModel);
		assertEquals(
				"If the state is accepting the next state must be labeled with 1",
				1, new KripkeAcceptingPolicy(model, claim).comuteNumber(
						stateModel, stateClaim, 2));
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.KripkeAcceptingPolicy#comuteInitNumber(it.polimi.automata.state.State, it.polimi.automata.state.State)}
	 * .
	 */
	@Test
	public void testComuteNumber2() {
		IBA model = new IBA(new ModelTransitionFactory());
		BA claim = new BA(new ClaimTransitionFactory());
		State stateClaim = new StateFactory().create();
		claim.addState(stateClaim);
		State stateModel = new StateFactory().create();
		model.addState(stateModel);
		assertEquals(
				"If the state is accepting the next state must be labeled with 1",
				1, new KripkeAcceptingPolicy(model, claim).comuteNumber(
						stateModel, stateClaim, 1));
	}
	

	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.KripkeAcceptingPolicy#comuteInitNumber(it.polimi.automata.state.State, it.polimi.automata.state.State)}
	 * .
	 */
	@Test
	public void testComuteNumber3() {
		IBA model = new IBA(new ModelTransitionFactory());
		BA claim = new BA(new ClaimTransitionFactory());
		State stateClaim = new StateFactory().create();
		claim.addAcceptState(stateClaim);
		State stateModel = new StateFactory().create();
		model.addState(stateModel);
		assertEquals(
				"If the state is accepting the next state must be labeled with 1",
				2, new KripkeAcceptingPolicy(model, claim).comuteNumber(
						stateModel, stateClaim, 1));
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.checker.intersection.acceptingpolicies.KripkeAcceptingPolicy#comuteInitNumber(it.polimi.automata.state.State, it.polimi.automata.state.State)}
	 * .
	 */
	@Test
	public void testComuteNumber4() {
		IBA model = new IBA(new ModelTransitionFactory());
		BA claim = new BA(new ClaimTransitionFactory());
		State stateClaim = new StateFactory().create();
		claim.addState(stateClaim);
		State stateModel = new StateFactory().create();
		model.addAcceptState(stateModel);
		assertEquals(
				"If the state is accepting the next state must be labeled with 1",
				1, new KripkeAcceptingPolicy(model, claim).comuteNumber(
						stateModel, stateClaim, 1));
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
		new KripkeAcceptingPolicy(model, claim).comuteInitNumber(modelState, null);
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
		new KripkeAcceptingPolicy(model, claim).comuteInitNumber(null, claimState);
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
		new KripkeAcceptingPolicy(model, claim).comuteInitNumber(modelState, new StateFactory().create());
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
		new KripkeAcceptingPolicy(model, claim).comuteInitNumber(new StateFactory().create(), claimState);
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
		assertEquals("The KriplkeAccepting policy returns 0 as initial value", 1, new KripkeAcceptingPolicy(model, claim).comuteInitNumber(modelState, claimState));
	}
}
