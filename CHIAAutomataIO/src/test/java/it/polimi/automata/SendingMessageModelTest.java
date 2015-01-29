/**
 * 
 */
package it.polimi.automata;

import static org.junit.Assert.assertTrue;
import it.polimi.automata.impl.IBAFactoryImpl;
import it.polimi.automata.io.IBAReader;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.LabelFactory;
import it.polimi.automata.labeling.impl.LabelFactoryImpl;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.automata.transition.impl.ModelTransitionFactoryImpl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import edu.uci.ics.jung.io.GraphIOException;

/**
 * @author claudiomenghi
 * 
 */
public class SendingMessageModelTest {

	private StateFactory<State> stateFactory;
	private TransitionFactory<Label, Transition<Label>> transitionFactory;
	private LabelFactory<Label> labelFactory;
	
	private Transition<Label> t1;
	private Transition<Label> t2;
	private Transition<Label> t3;
	private Transition<Label> t4;
	private Transition<Label> t5;
	private Transition<Label> t6;
	private Transition<Label> t7;
	
	@Before
	public void setUp() throws GraphIOException {
	
		this.stateFactory = new StateFactoryImpl();
		this.transitionFactory=new ModelTransitionFactoryImpl<Label>();
		this.labelFactory=new LabelFactoryImpl();
		
		Set<IGraphProposition> propositions1=new HashSet<IGraphProposition>();
		propositions1.add(new GraphProposition("start", false));
		Set<Label> labels1=new HashSet<Label>();
		labels1.add(this.labelFactory.create(propositions1));
		t1=this.transitionFactory.create(1, labels1);
		
		Set<IGraphProposition> propositions2=new HashSet<IGraphProposition>();
		propositions2.add(new GraphProposition("fail", false));
		Set<Label> labels2=new HashSet<Label>();
		labels2.add(this.labelFactory.create(propositions2));
		t2=this.transitionFactory.create(2, labels2);
		
		Set<IGraphProposition> propositions3=new HashSet<IGraphProposition>();
		propositions3.add(new GraphProposition("ok", false));
		Set<Label> labels3=new HashSet<Label>();
		labels3.add(this.labelFactory.create(propositions3));
		t3=this.transitionFactory.create(3, labels3);
		
		Set<IGraphProposition> propositions4=new HashSet<IGraphProposition>();
		propositions4.add(new GraphProposition("fail", false));
		Set<Label> labels4=new HashSet<Label>();
		labels4.add(this.labelFactory.create(propositions4));
		t4=this.transitionFactory.create(4, labels4);
		
		Set<IGraphProposition> propositions5=new HashSet<IGraphProposition>();
		propositions5.add(new GraphProposition("ok", false));
		Set<Label> labels5=new HashSet<Label>();
		labels5.add(this.labelFactory.create(propositions5));
		t5=this.transitionFactory.create(5, labels5);
		
		Set<IGraphProposition> propositions6=new HashSet<IGraphProposition>();
		propositions6.add(new GraphProposition("abort", false));
		Set<Label> labels6=new HashSet<Label>();
		labels6.add(this.labelFactory.create(propositions6));
		t6=this.transitionFactory.create(6, labels6);
		
		Set<IGraphProposition> propositions7=new HashSet<IGraphProposition>();
		propositions7.add(new GraphProposition("success", false));
		Set<Label> labels7=new HashSet<Label>();
		labels7.add(this.labelFactory.create(propositions7));
		t7=this.transitionFactory.create(7, labels7);
	}
	
	@Test
	public void test() throws FileNotFoundException, GraphIOException {
		IBAReader<Label, LabelFactory<Label>, State, StateFactory<State>, Transition<Label>, TransitionFactory<Label, Transition<Label>>, IBAFactory<Label, State, Transition<Label>>> reader=new IBAReader<Label, LabelFactory<Label>, State, StateFactory<State>, Transition<Label>, TransitionFactory<Label, Transition<Label>>, IBAFactory<Label, State, Transition<Label>>>(
				this.labelFactory, this.transitionFactory, this.stateFactory, new IBAFactoryImpl<Label, State, Transition<Label>>(),
				new BufferedReader(new FileReader(getClass().getClassLoader()
						.getResource("SendingMessageModel.xml").getFile())));
		
		IBA<Label, State, Transition<Label>> sendingMessage=reader.read();
		
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
