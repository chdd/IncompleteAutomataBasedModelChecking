package it.polimi.model.ltltoba;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.state.impl.StateImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.impl.TransitionFactoryClaimImpl;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

public class LTLtoBATransformerTest1 {

	@Mock
	private StateFactoryImpl stateFactory;
	
	@Mock
	private TransitionFactoryClaimImpl<State> transitionFactory;

	@Mock
	private StateImpl state;
	
	@Mock
	private Transition transition;
	
	
	@Before
	public void setUp()  {
		MockitoAnnotations.initMocks(this);
		when(stateFactory.create()).thenReturn(state);
		Set<IGraphProposition> propositions=new HashSet<IGraphProposition>();
		propositions.add(new GraphProposition("a", false));
		when(transitionFactory.create(propositions)).thenReturn(transition);
		when(transition.getLabels()).thenReturn(propositions);
	}
	
	@Test
	public void test() {
		LTLtoBATransformer<State, Transition> ltlToBATransformer;
		ltlToBATransformer=new LTLtoBATransformer<State, Transition>(
				stateFactory, transitionFactory);
		
		BA<State, Transition> ba=ltlToBATransformer.transform("[]a");
		
		assertTrue(ba.getInitialStates().size()==1);
		assertTrue(ba.getAcceptStates().size()==1);
		assertTrue(ba.getStates().size()==1);
		assertTrue(ba.getTransitions().size()==1);
		assertTrue(ba.getInitialStates().contains(state));
		assertTrue(ba.getAcceptStates().contains(state));
		assertTrue(ba.getStates().contains(state));
		assertTrue(ba.getTransitions().size()==1);
		assertTrue(ba.getTransitions().contains(transition));
	}
	
}
