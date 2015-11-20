/**
 * 
 */
package it.polimi.automata.io.in;

import static org.junit.Assert.assertTrue;
import it.polimi.automata.IBA;
import it.polimi.automata.io.in.ModelReader;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;


/**
 * @author Claudio Menghi
 *
 */
public class ModelReaderTest {
	
	private StateFactory stateFactory;
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
	
		this.stateFactory = new StateFactory();
		this.transitionFactory=new ModelTransitionFactory();
		
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
	
	
	/**
	 * Test method for {@link it.polimi.automata.io.in.ModelReader#ModelReader()}
	 * .
	 * 
	 * @throws ParserConfigurationException
	 */
	@Test(expected = NullPointerException.class)
	public void testModelReaderNullString() throws JAXBException,
			ParserConfigurationException {
		new ModelReader((String) null);
	}
	
	/**
	 * Test method for {@link it.polimi.automata.io.in.ModelReader#ModelReader()}
	 * .
	 * 
	 * @throws ParserConfigurationException
	 */
	@Test(expected = NullPointerException.class)
	public void testModelReaderNullFile() throws JAXBException,
			ParserConfigurationException {
		new ModelReader((File) null);
	}


	/**
	 * Test method for {@link it.polimi.automata.io.in.ModelReader#perform()}
	 * .
	 * 
	 * @throws ParserConfigurationException
	 */
	@Test
	public void test() throws Exception {

		ModelReader reader=new ModelReader(
				 new File(getClass().getClassLoader()
						.getResource("SendingMessageModel.xml").getFile()));
		
		IBA sendingMessage=reader.perform();
		
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
		
		assertTrue(sendingMessage.getBlackBoxStates().contains(stateFactory.create("send1", 2)));
		assertTrue(sendingMessage.getBlackBoxStates().contains(stateFactory.create("send2", 3)));
		assertTrue(sendingMessage.getBlackBoxStates().size()==2);
		
		assertTrue(sendingMessage.getTransitions().contains(t1));
		assertTrue(sendingMessage.getTransitions().contains(t2));
		assertTrue(sendingMessage.getTransitions().contains(t3));
		assertTrue(sendingMessage.getTransitions().contains(t4));
		assertTrue(sendingMessage.getTransitions().contains(t5));
		assertTrue(sendingMessage.getTransitions().contains(t6));
		assertTrue(sendingMessage.getTransitions().contains(t7));
	}
	
	/**
	 * Test method for {@link it.polimi.automata.io.in.ModelReader#perform()}
	 * .
	 * 
	 * @throws ParserConfigurationException
	 */
	@Test
	public void testPerformStringPath() throws Exception {

		ModelReader reader=new ModelReader(getClass()
				.getClassLoader()
						.getResource("SendingMessageModel.xml").getFile());
		
		IBA sendingMessage=reader.perform();
		
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
		
		assertTrue(sendingMessage.getBlackBoxStates().contains(stateFactory.create("send1", 2)));
		assertTrue(sendingMessage.getBlackBoxStates().contains(stateFactory.create("send2", 3)));
		assertTrue(sendingMessage.getBlackBoxStates().size()==2);
		
		assertTrue(sendingMessage.getTransitions().contains(t1));
		assertTrue(sendingMessage.getTransitions().contains(t2));
		assertTrue(sendingMessage.getTransitions().contains(t3));
		assertTrue(sendingMessage.getTransitions().contains(t4));
		assertTrue(sendingMessage.getTransitions().contains(t5));
		assertTrue(sendingMessage.getTransitions().contains(t6));
		assertTrue(sendingMessage.getTransitions().contains(t7));
		
		
	}
}
