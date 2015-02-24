/**
 * 
 */
package it.polimi.contraintcomputation.abstractor;

import static org.junit.Assert.assertTrue;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.io.BAReader;
import it.polimi.automata.io.IBAReader;
import it.polimi.automata.io.WriterBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.TransitionFactoryClaimImpl;
import it.polimi.automata.transition.impl.TransitionFactoryIntersectionImpl;
import it.polimi.automata.transition.impl.TransitionFactoryModelImpl;
import it.polimi.checker.ModelChecker;
import it.polimi.checker.ModelCheckingResults;
import it.polimi.checker.intersection.impl.IntersectionRuleImpl;
import it.polimi.constraints.Component;
import it.polimi.contraintcomputation.abstractedBA.AbstractedBA;
import it.polimi.contraintcomputation.subautomatafinder.SubAutomataIdentifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author claudiomenghi
 * 
 */
public class AbstractorTest {

	/**
	 * is the logger of the SubAutomataIdentifier class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(AbstractorTest.class);
	/*
	 * Model
	 */
	private IBA<State, Transition> model;
	/*
	 * Claim
	 */
	private BA<State, Transition> claim;

	private StateFactory<State> stateFactory;
	private TransitionFactory<State, Transition> transitionFactory;

	private Map<State, Set<State>> intersectionStateModelStateMap;

	@Before
	public void setUp() throws FileNotFoundException {

		this.stateFactory = new StateFactoryImpl();
		this.transitionFactory = new TransitionFactoryModelImpl<State>(
				Transition.class);
		this.intersectionStateModelStateMap = new HashMap<State, Set<State>>();
		/*
		 * creating the model
		 */
		IBAReader<State, StateFactory<State>, Transition, TransitionFactory<State, Transition>> modelReader = new IBAReader<State, StateFactory<State>, Transition, TransitionFactory<State, Transition>>(
				this.transitionFactory, this.stateFactory, new File(getClass()
						.getClassLoader()
						.getResource("sendingmessage/SendingMessageModel.xml")
						.getFile()));

		this.model = modelReader.read();

		/*
		 * creating the claim
		 */
		BAReader<State, StateFactory<State>, Transition, TransitionFactory<State, Transition>> claimReader = new BAReader<State, StateFactory<State>, Transition, TransitionFactory<State, Transition>>(
				new TransitionFactoryClaimImpl<State>(Transition.class),
				this.stateFactory, new File(getClass().getClassLoader()
						.getResource("sendingmessage/SendingMessageClaim.xml")
						.getFile()));

		this.claim = claimReader.read();

	}

	/**
	 * Test method for
	 * {@link it.polimi.contraintcomputation.abstractor.Abstractor#abstractIntersection()}
	 * .
	 */
	@Test
	public void testAbstractIntersection() {

		logger.debug("Test abstract intersection");

		ModelChecker<State, Transition> mc = new ModelChecker<State, Transition>(
				model, claim, new IntersectionRuleImpl<State, Transition>(),
				new StateFactoryImpl(),
				new TransitionFactoryIntersectionImpl<State>(Transition.class),
				new ModelCheckingResults());

		mc.check();
		IntersectionBA<State, Transition> intersectionBa = mc
				.getIntersectionAutomaton();
		intersectionStateModelStateMap=mc.getIntersectionStateModelStateMap();
		
		
		new WriterBA<State, Transition>(intersectionBa, new File("/Users/Claudio1/Desktop/CHIAResults/Results.xml")).write();
		
		SubAutomataIdentifier<State, Transition> identifier = new SubAutomataIdentifier<State, Transition>(
				intersectionBa, model, intersectionStateModelStateMap,
				new TransitionFactoryClaimImpl<Component<State, Transition>>(
						Transition.class),
				new TransitionFactoryClaimImpl<State>(Transition.class));

		AbstractedBA<State, Transition, Component<State, Transition>> simplifiedAutomata = identifier
				.getSubAutomata();

		Abstractor<State, Transition> abstractor = new Abstractor<State, Transition>(
				simplifiedAutomata);
		AbstractedBA<State, Transition, Component<State, Transition>> abstractedAutomata = abstractor
				.abstractIntersection();
		
		System.out.println(abstractedAutomata);
		assertTrue(abstractedAutomata.getStates().size() == 4);
		assertTrue(abstractedAutomata.getInitialStates().size() == 1);
		assertTrue(abstractedAutomata.getAcceptStates().size() == 1);
		assertTrue(abstractedAutomata.getOutTransitions(
				abstractedAutomata.getInitialStates().iterator().next()).size() == 1);
		
		
	}

}
