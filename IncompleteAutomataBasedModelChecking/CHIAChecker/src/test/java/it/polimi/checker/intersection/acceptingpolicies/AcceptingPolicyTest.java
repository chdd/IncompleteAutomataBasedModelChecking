package it.polimi.checker.intersection.acceptingpolicies;

import static org.junit.Assert.*;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;

import org.junit.Test;

public class AcceptingPolicyTest {

	

	@Test
	public void testSetClaim() {
		State claimState = new StateFactory().create();
		BA claim = new BA(new ClaimTransitionFactory());
		claim.addState(claimState);
		
		State modelState = new StateFactory().create();
		IBA model = new IBA(new ModelTransitionFactory());
		model.addState(modelState);
		AcceptingPolicy ap=AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model, claim);
		
		BA claim2 = new BA(new ClaimTransitionFactory());
		ap.setClaim(claim2);
		
		assertTrue(claim2==ap.getClaim());
	}

	@Test
	public void testSetModel() {
		State claimState = new StateFactory().create();
		BA claim = new BA(new ClaimTransitionFactory());
		claim.addState(claimState);
		
		State modelState = new StateFactory().create();
		IBA model = new IBA(new ModelTransitionFactory());
		model.addState(modelState);
		AcceptingPolicy ap=AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model, claim);
		
		IBA model2 = new IBA(new ModelTransitionFactory());
		ap.setModel(model2);
		assertTrue(model2==ap.getModel());
	}

	
	@Test
	public void testGetBaAcceptingPolicy() {
		State claimState = new StateFactory().create();
		BA claim = new BA(new ClaimTransitionFactory());
		claim.addState(claimState);
		
		State modelState = new StateFactory().create();
		IBA model = new IBA(new ModelTransitionFactory());
		model.addState(modelState);
		assertTrue("A BA accepting policy is returned", AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model, claim) instanceof BAAcceptingPolicy);
	}
	
	@Test
	public void testGetKripkeAcceptingPolicy() {
		State claimState = new StateFactory().create();
		BA claim = new BA(new ClaimTransitionFactory());
		claim.addState(claimState);
		
		State modelState = new StateFactory().create();
		IBA model = new IBA(new ModelTransitionFactory());
		model.addState(modelState);
		assertTrue("A Kripke accepting policy is returned", AcceptingPolicy.getAcceptingPolicy(AcceptingType.KRIPKE, model, claim) instanceof KripkeAcceptingPolicy);
	}
	
}
