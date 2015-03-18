/**
 * 
 */
package it.polimi.casestudies.sendingmessage.checker;

import static org.junit.Assert.assertEquals;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.io.BAReader;
import it.polimi.automata.io.IBAReader;
import it.polimi.automata.io.transformer.states.BAStateElementParser;
import it.polimi.automata.io.transformer.states.IBAStateElementParser;
import it.polimi.automata.io.transformer.states.StateElementParser;
import it.polimi.automata.io.transformer.transitions.BATransitionParser;
import it.polimi.automata.io.transformer.transitions.ClaimTransitionParser;
import it.polimi.automata.io.transformer.transitions.IBATransitionParser;
import it.polimi.automata.io.transformer.transitions.ModelTransitionParser;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.checker.ModelChecker;
import it.polimi.checker.ModelCheckingResults;
import it.polimi.checker.intersection.impl.IntersectionRule;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

/**
 * @author claudiomenghi
 *
 */
public class SendingMessageTest2 {


	@Before
	public void setUp() {


	}

	@Test
	public void checkerTest() throws FileNotFoundException {

		StateElementParser<State, Transition, BA<State, Transition>> stateElementParser = new BAStateElementParser(
				new StateFactory());

		ClaimTransitionParser<State, Transition, BA<State, Transition>> transitionElementParser = new BATransitionParser(
				new ClaimTransitionFactory<State>());

		BAReader<State, Transition> claimReader = new BAReader<State, Transition>(
				new File(
						getClass()
								.getClassLoader()
								.getResource(
										"it/polimi/casestudies/sendingmessage/SendingMessageClaim.xml")
								.getFile()), stateElementParser,
				transitionElementParser);

		BA<State, Transition> claim = claimReader.read();

		ModelTransitionParser<State, Transition, IBA<State,Transition>> modelRransitionElementParser=new 
				IBATransitionParser<State, Transition, IBA<State,Transition>>(new ModelTransitionFactory<State>());
		
		StateElementParser<State, Transition, IBA<State,Transition>> ibaStateElementParser=new IBAStateElementParser(new StateFactory());
	

		IBAReader<State, Transition> modelReader = new IBAReader<State,  Transition>(
				new File(getClass().getClassLoader()
											.getResource(
										"it/polimi/casestudies/sendingmessage/SendingMessageModel.xml").getFile()),
								ibaStateElementParser,
								modelRransitionElementParser);

		IBA<State, Transition> model = modelReader.read();

		ModelCheckingResults mp = new ModelCheckingResults();
		ModelChecker<State, Transition> modelChecker = new ModelChecker<State, Transition>(
				model,
				claim,
				new IntersectionRule<State, Transition>(new ClaimTransitionFactory<State>(),	new StateFactory()),
				 mp);

		int res = modelChecker.check();
		IntersectionBA<State, Transition> intersectionBA = modelChecker
				.getIntersectionAutomaton();

		System.out.println(intersectionBA);

		assertEquals(-1, res);
		assertEquals(11, intersectionBA.getStates().size());
		assertEquals(1, intersectionBA.getInitialStates().size());
		assertEquals(1, intersectionBA.getAcceptStates().size());
		assertEquals(4, intersectionBA.getMixedStates().size());
		assertEquals(20, intersectionBA.getTransitions().size());
	}
}
