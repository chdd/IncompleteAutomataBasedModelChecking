/**
 * 
 */
package it.polimi.automata.io;

import static org.junit.Assert.assertTrue;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.ModelTransitionFactoryImpl;

import java.io.File;
import java.io.FileNotFoundException;
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
public class IBAReaderTest {

	private StateFactory<State> stateFactory;
	private TransitionFactory<State, Transition> transitionFactory;
	
	private Transition t1;
	private Transition t2;
	private Transition t3;
	private Transition t4;
	private Transition t5;
	private Transition t6;
	private Transition t7;
	
	@Before
	public void setUp() {
	
		this.stateFactory = new StateFactoryImpl();
		this.transitionFactory=new ModelTransitionFactoryImpl<State>(Transition.class);
		
		Set<IGraphProposition> propositions1=new HashSet<IGraphProposition>();
		propositions1.add(new GraphProposition("start", false));
		t1=this.transitionFactory.create(1, propositions1);
		
		Set<IGraphProposition> propositions2=new HashSet<IGraphProposition>();
		propositions2.add(new GraphProposition("fail", false));
		t2=this.transitionFactory.create(2, propositions2);
		
		Set<IGraphProposition> propositions3=new HashSet<IGraphProposition>();
		propositions3.add(new GraphProposition("ok", false));
		t3=this.transitionFactory.create(3, propositions3);
		
		Set<IGraphProposition> propositions4=new HashSet<IGraphProposition>();
		propositions4.add(new GraphProposition("fail", false));
		t4=this.transitionFactory.create(4, propositions4);
		
		Set<IGraphProposition> propositions5=new HashSet<IGraphProposition>();
		propositions5.add(new GraphProposition("ok", false));
		t5=this.transitionFactory.create(5, propositions5);
		
		Set<IGraphProposition> propositions6=new HashSet<IGraphProposition>();
		propositions6.add(new GraphProposition("abort", false));
		t6=this.transitionFactory.create(6, propositions6);
		
		Set<IGraphProposition> propositions7=new HashSet<IGraphProposition>();
		propositions7.add(new GraphProposition("success", false));
		t7=this.transitionFactory.create(7, propositions7);
	}
	
	@Test
	public void test() throws FileNotFoundException {
		IBAReader<State, StateFactory<State>, Transition, TransitionFactory<State, Transition>> reader=new IBAReader< State, StateFactory<State>, Transition, TransitionFactory<State, Transition>>(
				this.transitionFactory, this.stateFactory, new File(getClass().getClassLoader()
						.getResource("SendingMessageModel.xml").getFile()));
		
		IBA< State, Transition> sendingMessage=reader.read();
		
		assertTrue(sendingMessage.getStates().contains(stateFactory.create("q1", 1)));
		assertTrue(sendingMessage.getStates().contains(stateFactory.create("send1", 2)));
		assertTrue(sendingMessage.getStates().contains(stateFactory.create("send2", 3)));
		assertTrue(sendingMessage.getStates().contains(stateFactory.create("q2", 4)));
		assertTrue(sendingMessage.getStates().contains(stateFactory.create("q3", 5)));
		assertTrue(sendingMessage.getStates().size()==5);
		
		assertTrue(sendingMessage.getInitialStates().contains(stateFactory.create("q1", 1)));
		assertTrue(sendingMessage.getInitialStates().size()==1);
		
		assertTrue(sendingMessage.getStates().contains(stateFactory.create("q2", 4)));
		assertTrue(sendingMessage.getStates().contains(stateFactory.create("q3", 5)));
		assertTrue(sendingMessage.getAcceptStates().size()==2);
		
		assertTrue(sendingMessage.getTransparentStates().contains(stateFactory.create("send1", 2)));
		assertTrue(sendingMessage.getTransparentStates().contains(stateFactory.create("send2", 3)));
		assertTrue(sendingMessage.getTransparentStates().size()==2);
		
		assertTrue(sendingMessage.getTransitions().contains(t1));
		assertTrue(sendingMessage.getTransitions().contains(t2));
		assertTrue(sendingMessage.getTransitions().contains(t3));
		assertTrue(sendingMessage.getTransitions().contains(t4));
		assertTrue(sendingMessage.getTransitions().contains(t5));
		assertTrue(sendingMessage.getTransitions().contains(t6));
		assertTrue(sendingMessage.getTransitions().contains(t7));
		
		
	}
}
