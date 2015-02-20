/**
 * 
 */
package it.polimi.checker;

import static org.junit.Assert.assertEquals;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.io.BAReader;
import it.polimi.automata.io.IBAReader;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.TransitionFactoryClaimImpl;
import it.polimi.automata.transition.impl.TransitionFactoryIntersectionImpl;
import it.polimi.automata.transition.impl.TransitionFactoryModelImpl;
import it.polimi.checker.intersection.impl.IntersectionRuleImpl;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;


/**
 * @author claudiomenghi
 *
 */
public class SendingMessageTest {

	
	private StateFactory<State> stateFactory;
	private TransitionFactory<State, Transition> transitionFactory;
	private TransitionFactory<State, Transition> claimTransitionFactory;
	
	
	
	@Before
	public void setUp() {
	
		this.stateFactory = new StateFactoryImpl();
		this.transitionFactory=new TransitionFactoryModelImpl<State>(Transition.class);
		this.claimTransitionFactory=new TransitionFactoryClaimImpl<State>(Transition.class);
		
	}
	@Test
	public void checkerTest() throws FileNotFoundException {
		BAReader<State, StateFactory<State>, Transition, TransitionFactory<State, Transition>> claimReader = 
				new BAReader<State, StateFactory<State>, Transition, TransitionFactory<State, Transition>>(
				claimTransitionFactory,this.stateFactory,  
				new File(getClass().getClassLoader()
						.getResource("sendingmessage/SendingMessageClaim.xml").getFile()));

		BA<State, Transition> claim = claimReader.read();

		IBAReader<State, StateFactory<State>, Transition, TransitionFactory<State, Transition>> modelReader=
				new IBAReader<State, StateFactory<State>, Transition, TransitionFactory<State, Transition>>(
				this.transitionFactory, this.stateFactory, 
				new File(getClass().getClassLoader()
						.getResource("sendingmessage/SendingMessageModel.xml").getFile()));
		
		IBA< State, Transition> model=modelReader.read();
		
		ModelCheckingResults mp=new ModelCheckingResults();
		ModelChecker<State, Transition> modelChecker=new ModelChecker< State, Transition>
		(model, claim, 
				new IntersectionRuleImpl<State, Transition>(), new StateFactoryImpl(), 
				new TransitionFactoryIntersectionImpl<State>(Transition.class), mp);
		
		int res=modelChecker.check();
		IntersectionBA< State, Transition> intersectionBA=modelChecker.getIntersectionAutomaton();
		
		assertEquals(-1, res);
		assertEquals(11, intersectionBA.getStates().size());
		assertEquals(1, intersectionBA.getInitialStates().size());
		assertEquals(1, intersectionBA.getAcceptStates().size());
		assertEquals(4, intersectionBA.getMixedStates().size());
		assertEquals(20, intersectionBA.getTransitions().size());
	}
	
	
	

}
