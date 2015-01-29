/**
 * 
 */
package it.polimi.checker;

import static org.junit.Assert.assertEquals;
import it.polimi.automata.BA;
import it.polimi.automata.BAFactory;
import it.polimi.automata.IBA;
import it.polimi.automata.IBAFactory;
import it.polimi.automata.IntersectionBA;
import it.polimi.automata.impl.BAFactoryImpl;
import it.polimi.automata.impl.IBAFactoryImpl;
import it.polimi.automata.impl.IntBAFactoryImpl;
import it.polimi.automata.io.BAReader;
import it.polimi.automata.io.IBAReader;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.LabelFactory;
import it.polimi.automata.labeling.impl.LabelImplFactory;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.ClaimTransitionFactoryImpl;
import it.polimi.automata.transition.impl.IntersectionTransitionFactoryImpl;
import it.polimi.automata.transition.impl.ModelTransitionFactoryImpl;
import it.polimi.checker.intersection.impl.IntersectionRuleImpl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.junit.Before;
import org.junit.Test;

import edu.uci.ics.jung.io.GraphIOException;

/**
 * @author claudiomenghi
 *
 */
public class SendingMessageTest {

	
	private StateFactory<State> stateFactory;
	private TransitionFactory<Label, Transition<Label>> transitionFactory;
	private LabelFactory<Label> labelFactory;
	
	
	
	@Before
	public void setUp() throws GraphIOException {
	
		this.stateFactory = new StateFactoryImpl();
		this.transitionFactory=new ModelTransitionFactoryImpl<Label>();
		this.labelFactory=new LabelImplFactory();
		
		
	}
	@Test
	public void checkerTest() throws FileNotFoundException, GraphIOException {
		BAReader<Label, LabelFactory<Label>, State, StateFactory<State>, Transition<Label>, TransitionFactory<Label, Transition<Label>>, BAFactory<Label, State, Transition<Label>>> claimReader = new BAReader<Label, LabelFactory<Label>, State, StateFactory<State>, Transition<Label>, TransitionFactory<Label, Transition<Label>>, BAFactory<Label, State, Transition<Label>>>(
				this.labelFactory, new ClaimTransitionFactoryImpl<Label>(), this.stateFactory,
				new BAFactoryImpl<Label, State, Transition<Label>>(),
				new BufferedReader(new FileReader(getClass().getClassLoader()
						.getResource("SendingMessageClaim.xml").getFile())));

		BA<Label, State, Transition<Label>> claim = claimReader.read();

		IBAReader<Label, LabelFactory<Label>, State, StateFactory<State>, Transition<Label>, TransitionFactory<Label, Transition<Label>>, IBAFactory<Label, State, Transition<Label>>> modelReader=new IBAReader<Label, LabelFactory<Label>, State, StateFactory<State>, Transition<Label>, TransitionFactory<Label, Transition<Label>>, IBAFactory<Label, State, Transition<Label>>>(
				this.labelFactory, this.transitionFactory, this.stateFactory, new IBAFactoryImpl<Label, State, Transition<Label>>(),
				new BufferedReader(new FileReader(getClass().getClassLoader()
						.getResource("SendingMessageModel.xml").getFile())));
		
		IBA<Label, State, Transition<Label>> model=modelReader.read();
		
		ModelCheckingResults mp=new ModelCheckingResults();
		ModelChecker<Label, State, Transition<Label>> modelChecker=new ModelChecker<Label, State, Transition<Label>>
		(model, claim, new IntersectionRuleImpl<Label, Transition<Label>>(), new IntBAFactoryImpl<Label, State, Transition<Label>>(), new StateFactoryImpl(), new IntersectionTransitionFactoryImpl<Label>(), mp);
		
		int res=modelChecker.check();
		IntersectionBA<Label, State, Transition<Label>> intersectionBA=modelChecker.getIntersectionAutomaton();
		assertEquals(-1, res);
		assertEquals(11, intersectionBA.getStates().size());
		assertEquals(1, intersectionBA.getInitialStates().size());
		assertEquals(1, intersectionBA.getAcceptStates().size());
		assertEquals(4, intersectionBA.getMixedStates().size());
		assertEquals(20, intersectionBA.getTransitions().size());
	}
	
	
	

}
