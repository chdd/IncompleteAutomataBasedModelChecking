/**
 * 
 */
package it.polimi.checker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.impl.IBAFactoryImpl;
import it.polimi.automata.impl.IntBAFactoryImpl;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.impl.LabelFactoryImpl;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.ClaimTransitionFactoryImpl;
import it.polimi.automata.transition.impl.IntersectionTransitionFactoryImpl;
import it.polimi.automata.transition.impl.ModelTransitionFactoryImpl;
import it.polimi.checker.intersection.impl.IntersectionRuleImpl;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * @author claudiomenghi
 * 
 */
public class ModelCheckerTest {

	/*
	 * Claim 1
	 */
	private BA<Label, State, Transition<Label>> claim1;
	private State claim1State1;
	private State claimState2;
	private State claim1State3;
	private Transition<Label> claim1Transition1;
	private Transition<Label> claim1Transition2;
	private Transition<Label> claim1Transition3;
	
	/*
	 * Claim 2
	 */
	private BA<Label, State, Transition<Label>> claim2;
	private State claim2State1;
	private State claim2State2;
	private State claim2State3;
	private Transition<Label> claim2Transition1;
	private Transition<Label> claim2Transition2;
	private Transition<Label> claim2Transition3;
	private Set<Label> claim2Transition1Labels;
	private Set<Label> claim2Transition2Labels;
	private Set<Label> claim2Transition3Labels;

	/*
	 * Model 1
	 */
	private IBA<Label, State, Transition<Label>> model1;
	private State model1State1;
	private State model1State2;
	private State model1State3;
	private Transition<Label> model1Transition1;
	private Transition<Label> model1Transition2;
	private Transition<Label> model1Transition3;
	private Set<Label> model1T1Labels;
	private Set<Label> model1T2Labels;
	private Set<Label> model1T3Labels;

	/*
	 * Model 2
	 */
	private IBA<Label, State, Transition<Label>> model2;
	private State model2State1;
	private State model2State2;
	private State model2State3;
	private Transition<Label> model2Transition1;
	private Transition<Label> model2Transition2;
	private Transition<Label> model2Transition3;
	private Set<Label> model2T1Labels;
	private Set<Label> model2T2Labels;
	private Set<Label> model2T3Labels;

	@Before
	public void setUp() {
		this.claim1 = new IBAFactoryImpl<Label, State, Transition<Label>>()
				.create();

		StateFactory<State> factory = new StateFactoryImpl();
		claim1State1 = factory.create("claimState1");
		claimState2 = factory.create("claimState2");
		claim1State3 = factory.create("claimState3");
		this.claim1.addState(claim1State1);
		this.claim1.addState(claimState2);
		this.claim1.addState(claim1State3);
		TransitionFactory<Label, Transition<Label>> transitionFactory = new ClaimTransitionFactoryImpl<Label>();

		this.model1T1Labels = new HashSet<Label>();
		Set<IGraphProposition> propositionsT1 = new HashSet<IGraphProposition>();
		propositionsT1.add(new GraphProposition("a", false));
		Label label1 = new LabelFactoryImpl().create(propositionsT1);
		model1T1Labels.add(label1);
		claim1Transition1 = transitionFactory.create(model1T1Labels);

		this.model1T2Labels = new HashSet<Label>();
		Set<IGraphProposition> propositionsT2 = new HashSet<IGraphProposition>();
		propositionsT2.add(new GraphProposition("b", false));
		Label label2 = new LabelFactoryImpl().create(propositionsT2);
		model1T2Labels.add(label2);
		claim1Transition2 = transitionFactory.create(model1T2Labels);

		this.model1T3Labels = new HashSet<Label>();
		Set<IGraphProposition> propositionsT3 = new HashSet<IGraphProposition>();
		propositionsT3.add(new GraphProposition("c", false));
		Label label3 = new LabelFactoryImpl().create(propositionsT3);
		model1T3Labels.add(label3);
		claim1Transition3 = transitionFactory.create(model1T3Labels);

		this.claim1.addCharacter(label1);
		this.claim1.addCharacter(label2);
		this.claim1.addCharacter(label3);
		this.claim1.addTransition(claim1State1, claimState2, claim1Transition1);
		this.claim1.addTransition(claimState2, claim1State3, claim1Transition2);
		this.claim1
				.addTransition(claim1State3, claim1State3, claim1Transition3);

		// --------------------------------------
		// CLAIM 2
		// --------------------------------------
		this.claim2 = new IBAFactoryImpl<Label, State, Transition<Label>>()
				.create();
		transitionFactory = new ClaimTransitionFactoryImpl<Label>();

		factory = new StateFactoryImpl();
		claim2State1 = factory.create("claimState1");
		claim2State2 = factory.create("claimState2");
		claim2State3 = factory.create("claimState3");
		this.claim2.addState(claim2State1);
		this.claim2.addState(claim2State2);
		this.claim2.addState(claim2State3);

		this.claim2Transition1Labels = new HashSet<Label>();
		Set<IGraphProposition> claim2PropositionsT1 = new HashSet<IGraphProposition>();
		claim2PropositionsT1.add(new GraphProposition("a", false));
		Label labelClaim2T1 = new LabelFactoryImpl()
				.create(claim2PropositionsT1);
		claim2Transition1Labels.add(labelClaim2T1);
		claim2Transition1 = transitionFactory.create(claim2Transition1Labels);

		this.claim2Transition2Labels = new HashSet<Label>();
		Set<IGraphProposition> claim2PropositionsT2 = new HashSet<IGraphProposition>();
		claim2PropositionsT2.add(new GraphProposition("b", true));
		Label labelClaim2T2 = new LabelFactoryImpl()
				.create(claim2PropositionsT2);
		claim2Transition2Labels.add(labelClaim2T2);
		claim2Transition2 = transitionFactory.create(claim2Transition2Labels);

		this.claim2Transition3Labels = new HashSet<Label>();
		Set<IGraphProposition> claim2PropositionsT3 = new HashSet<IGraphProposition>();
		claim2PropositionsT3.add(new GraphProposition("c", false));
		Label labelClaim2T3 = new LabelFactoryImpl()
				.create(claim2PropositionsT3);
		claim2Transition3Labels.add(labelClaim2T3);
		claim2Transition3 = transitionFactory.create(claim2Transition3Labels);

		this.claim2.addCharacter(labelClaim2T1);
		this.claim2.addCharacter(labelClaim2T2);
		this.claim2.addCharacter(labelClaim2T3);
		this.claim2
				.addTransition(claim2State1, claim2State2, claim2Transition1);
		this.claim2
				.addTransition(claim2State2, claim2State3, claim2Transition2);
		this.claim2
				.addTransition(claim2State3, claim2State3, claim2Transition3);

		/*
		 * MODEL
		 */
		this.model1 = new IBAFactoryImpl<Label, State, Transition<Label>>()
				.create();

		StateFactory<State> modelStateFactory = new StateFactoryImpl();
		this.model1State1 = modelStateFactory.create("modelState1");
		this.model1State2 = modelStateFactory.create("modelState2");
		this.model1State3 = modelStateFactory.create("modelState3");
		this.model1.addState(model1State1);
		this.model1.addState(model1State2);
		this.model1.addState(model1State3);
		TransitionFactory<Label, Transition<Label>> modelTransitionFactory = new ModelTransitionFactoryImpl<Label>();

		Set<Label> labelsT1Model = new HashSet<Label>();
		Set<IGraphProposition> propositionsModelT1 = new HashSet<IGraphProposition>();
		propositionsModelT1.add(new GraphProposition("a", false));
		Label labelModel1 = new LabelFactoryImpl().create(propositionsModelT1);
		labelsT1Model.add(labelModel1);
		this.model1Transition1 = modelTransitionFactory.create(labelsT1Model);

		Set<Label> labelsT2Model = new HashSet<Label>();
		Set<IGraphProposition> propositionsModelT2 = new HashSet<IGraphProposition>();
		propositionsModelT2.add(new GraphProposition("b", false));
		Label labelModel2 = new LabelFactoryImpl().create(propositionsModelT2);
		labelsT2Model.add(labelModel2);
		this.model1Transition2 = modelTransitionFactory.create(labelsT2Model);

		Set<Label> labelsT3Model = new HashSet<Label>();
		Set<IGraphProposition> propositionsModelT3 = new HashSet<IGraphProposition>();
		propositionsModelT3.add(new GraphProposition("c", false));
		Label labelModel3 = new LabelFactoryImpl().create(propositionsModelT3);
		labelsT3Model.add(labelModel3);
		this.model1Transition3 = modelTransitionFactory.create(labelsT3Model);

		this.model1.addCharacter(label1);
		this.model1.addCharacter(label2);
		this.model1.addCharacter(label3);
		this.model1
				.addTransition(model1State1, model1State2, model1Transition1);
		this.model1
				.addTransition(model1State2, model1State3, model1Transition2);
		this.model1
				.addTransition(model1State3, model1State3, model1Transition3);

		/*
		 * MODEL
		 */
		this.model2 = new IBAFactoryImpl<Label, State, Transition<Label>>()
				.create();

		modelStateFactory = new StateFactoryImpl();
		this.model2State1 = modelStateFactory.create("modelState1");
		this.model2State2 = modelStateFactory.create("modelState2");
		this.model2State3 = modelStateFactory.create("modelState3");
		this.model2.addState(model2State1);
		this.model2.addState(model2State2);
		this.model2.addState(model2State3);

		model2T1Labels = new HashSet<Label>();
		Set<IGraphProposition> propositionsModel2T1 = new HashSet<IGraphProposition>();
		propositionsModel2T1.add(new GraphProposition("a", false));
		Label model2T1Label = new LabelFactoryImpl()
				.create(propositionsModel2T1);
		model2T1Labels.add(model2T1Label);
		this.model2Transition1 = modelTransitionFactory.create(model2T1Labels);

		model2T2Labels = new HashSet<Label>();
		Set<IGraphProposition> propositionsModel2T2 = new HashSet<IGraphProposition>();
		propositionsModel2T2.add(new GraphProposition("b", false));
		Label model2T2Label = new LabelFactoryImpl()
				.create(propositionsModel2T2);
		model2T2Labels.add(model2T2Label);
		this.model2Transition2 = modelTransitionFactory.create(model2T2Labels);

		model2T3Labels = new HashSet<Label>();
		Set<IGraphProposition> propositionsModel2T3 = new HashSet<IGraphProposition>();
		propositionsModel2T3.add(new GraphProposition("c", false));
		Label model2T3Label = new LabelFactoryImpl()
				.create(propositionsModel2T3);
		model2T3Labels.add(model2T3Label);
		this.model2Transition3 = modelTransitionFactory.create(model2T3Labels);

		this.model2.addCharacter(model2T1Label);
		this.model2.addCharacter(model2T2Label);
		this.model2.addCharacter(model2T3Label);
		this.model2
				.addTransition(model2State1, model2State2, model2Transition1);
		this.model2
				.addTransition(model2State2, model2State3, model2Transition2);
		this.model2
				.addTransition(model2State3, model2State3, model2Transition3);
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelChecker#ModelChecker(null, it.polimi.automata.BA, it.polimi.checker.ModelCheckingResults)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testModelCheckerNullModel() {
		new ModelChecker<Label, State, Transition<Label>>(null, claim1,
				new IntersectionRuleImpl<Label, Transition<Label>>(),
				new IntBAFactoryImpl<Label, State, Transition<Label>>(),
				new StateFactoryImpl(),
				new IntersectionTransitionFactoryImpl<Label>(),
				new ModelCheckingResults());
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelChecker#ModelChecker(it.polimi.automata.IBA, null, it.polimi.checker.ModelCheckingResults)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testModelCheckerNullClaim() {
		new ModelChecker<Label, State, Transition<Label>>(model1, null,
				new IntersectionRuleImpl<Label, Transition<Label>>(),
				new IntBAFactoryImpl<Label, State, Transition<Label>>(),
				new StateFactoryImpl(),
				new IntersectionTransitionFactoryImpl<Label>(),
				new ModelCheckingResults());
	}

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelChecker#ModelChecker(it.polimi.automata.IBA, it.polimi.automata.BA, null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testModelCheckerNullIntersectionRule() {
		new ModelChecker<Label, State, Transition<Label>>(model1, claim1, 
				null,
				new IntBAFactoryImpl<Label, State, Transition<Label>>(),
				new StateFactoryImpl(),
				new IntersectionTransitionFactoryImpl<Label>(),
				new ModelCheckingResults());
	}
	
	/**
	 * Test method for {@link it.polimi.checker.ModelChecker#check()}.
	 */
	@Test(expected = NullPointerException.class)
	public void testCheckNullIntersectionAutomatonFactory() {
		new ModelChecker<Label, State, Transition<Label>>(
				model1, claim1, 
				new IntersectionRuleImpl<Label, Transition<Label>>(),
				null,
				new StateFactoryImpl(),
				new IntersectionTransitionFactoryImpl<Label>(),
				new ModelCheckingResults());
	}
	

	/**
	 * Test method for {@link it.polimi.checker.ModelChecker#check()}.
	 */
	@Test(expected = NullPointerException.class)
	public void testCheckNullStateFactory() {
		ModelChecker<Label, State, Transition<Label>> mck = new ModelChecker<Label, State, Transition<Label>>(
				model1, claim1, 
				new IntersectionRuleImpl<Label, Transition<Label>>(),
				new IntBAFactoryImpl<Label, State, Transition<Label>>(),
				null,
				new IntersectionTransitionFactoryImpl<Label>(),
				new ModelCheckingResults());
		assertEquals(1, mck.check());
	}
	/**
	 * Test method for {@link it.polimi.checker.ModelChecker#check()}.
	 */
	@Test(expected = NullPointerException.class)
	public void testCheckNullIntersectionFactory() {
		ModelChecker<Label, State, Transition<Label>> mck = new ModelChecker<Label, State, Transition<Label>>(
				model1, claim1, 
				new IntersectionRuleImpl<Label, Transition<Label>>(),
				new IntBAFactoryImpl<Label, State, Transition<Label>>(),
				new StateFactoryImpl(),
				null,
				new ModelCheckingResults());
		assertEquals(1, mck.check());
	}
	
	/**
	 * Test method for {@link it.polimi.checker.ModelChecker#check()}.
	 */
	@Test(expected = NullPointerException.class)
	public void testCheckNullIntersectionResults() {
		ModelChecker<Label, State, Transition<Label>> mck = new ModelChecker<Label, State, Transition<Label>>(
				model1, claim1, 
				new IntersectionRuleImpl<Label, Transition<Label>>(),
				new IntBAFactoryImpl<Label, State, Transition<Label>>(),
				new StateFactoryImpl(),
				new IntersectionTransitionFactoryImpl<Label>(),
				null);
		assertEquals(1, mck.check());
	}
	
	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelChecker#ModelChecker(it.polimi.automata.IBA, it.polimi.automata.BA, null)}
	 * .
	 */
	@Test(expected = NullPointerException.class)
	public void testModelCheckerNullResutls() {
		new ModelChecker<Label, State, Transition<Label>>(model1, claim1, 
				new IntersectionRuleImpl<Label, Transition<Label>>(),
				new IntBAFactoryImpl<Label, State, Transition<Label>>(),
				new StateFactoryImpl(),
				new IntersectionTransitionFactoryImpl<Label>(),
				null);
	}

	/**
	 * Test method for {@link it.polimi.checker.ModelChecker#check()}.
	 */
	@Test
	public void testCheck() {
		ModelChecker<Label, State, Transition<Label>> mck = new ModelChecker<Label, State, Transition<Label>>(
				model1, claim1, 
				new IntersectionRuleImpl<Label, Transition<Label>>(),
				new IntBAFactoryImpl<Label, State, Transition<Label>>(),
				new StateFactoryImpl(),
				new IntersectionTransitionFactoryImpl<Label>(),
				new ModelCheckingResults());
		assertEquals(1, mck.check());
	}
	
	/**
	 * Test method for {@link it.polimi.checker.ModelChecker#check()}.
	 */
	@Test
	public void testCheck1() {
		model1.addInitialState(model1State1);
		claim1.addInitialState(claim1State1);
		ModelChecker<Label, State, Transition<Label>> mck = new ModelChecker<Label, State, Transition<Label>>(
				model1, claim1, 
				new IntersectionRuleImpl<Label, Transition<Label>>(),
				new IntBAFactoryImpl<Label, State, Transition<Label>>(),
				new StateFactoryImpl(),
				new IntersectionTransitionFactoryImpl<Label>(),
				new ModelCheckingResults());
		assertEquals(1, mck.check());
	}
	
	/**
	 * Test method for {@link it.polimi.checker.ModelChecker#check()}.
	 */
	@Test
	public void testCheck2() {
		model1.addInitialState(model1State1);
		model1.addAcceptState(model1State3);
		claim1.addInitialState(claim1State1);
		claim1.addAcceptState(claim1State3);
		ModelChecker<Label, State, Transition<Label>> mck = new ModelChecker<Label, State, Transition<Label>>(
				model1, claim1, 
				new IntersectionRuleImpl<Label, Transition<Label>>(),
				new IntBAFactoryImpl<Label, State, Transition<Label>>(),
				new StateFactoryImpl(),
				new IntersectionTransitionFactoryImpl<Label>(),
				new ModelCheckingResults());
		assertEquals(0, mck.check());
	}
	
	/**
	 * Test method for {@link it.polimi.checker.ModelChecker#check()}.
	 */
	@Test
	public void testCheck3() {
		ModelChecker<Label, State, Transition<Label>> mck = new ModelChecker<Label, State, Transition<Label>>(
				model2, claim2, 
				new IntersectionRuleImpl<Label, Transition<Label>>(),
				new IntBAFactoryImpl<Label, State, Transition<Label>>(),
				new StateFactoryImpl(),
				new IntersectionTransitionFactoryImpl<Label>(),
				new ModelCheckingResults());
		assertEquals(1, mck.check());
	}
	
	/**
	 * Test method for {@link it.polimi.checker.ModelChecker#check()}.
	 */
	@Test
	public void testCheck5() {
		model2.addInitialState(model2State1);
		claim1.addInitialState(claim1State1);
		claim1.addAcceptState(claim1State3);
		ModelChecker<Label, State, Transition<Label>> mck = new ModelChecker<Label, State, Transition<Label>>(
				model2, claim1, 
				new IntersectionRuleImpl<Label, Transition<Label>>(),
				new IntBAFactoryImpl<Label, State, Transition<Label>>(),
				new StateFactoryImpl(),
				new IntersectionTransitionFactoryImpl<Label>(),
				new ModelCheckingResults());
		assertEquals(1, mck.check());
	}
	
	/**
	 * Test method for {@link it.polimi.checker.ModelChecker#check()}.
	 */
	@Test
	public void testCheck6() {
		model1.addInitialState(model1State1);
		model1.addAcceptState(model1State3);
		model1.addTransparentState(model1State2);
		claim1.addInitialState(claim1State1);
		claim1.addAcceptState(claim1State3);
		ModelChecker<Label, State, Transition<Label>> mck = new ModelChecker<Label, State, Transition<Label>>(
				model1, claim1, 
				new IntersectionRuleImpl<Label, Transition<Label>>(),
				new IntBAFactoryImpl<Label, State, Transition<Label>>(),
				new StateFactoryImpl(),
				new IntersectionTransitionFactoryImpl<Label>(),
				new ModelCheckingResults());
		assertEquals(-1, mck.check());
	}
	

	/**
	 * Test method for
	 * {@link it.polimi.checker.ModelChecker#getVerificationTimes()}.
	 */
	@Test
	public void testGetVerificationTimes() {
		model1.addInitialState(model1State1);
		model1.addAcceptState(model1State3);
		model1.addTransparentState(model1State2);
		claim1.addInitialState(claim1State1);
		claim1.addAcceptState(claim1State3);
		ModelChecker<Label, State, Transition<Label>> mck = new ModelChecker<Label, State, Transition<Label>>(
				model1, claim1, 
				new IntersectionRuleImpl<Label, Transition<Label>>(),
				new IntBAFactoryImpl<Label, State, Transition<Label>>(),
				new StateFactoryImpl(),
				new IntersectionTransitionFactoryImpl<Label>(),
				new ModelCheckingResults());
		assertNotNull(mck.getVerificationTimes());
	}
}
