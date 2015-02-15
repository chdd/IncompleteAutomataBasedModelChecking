package it.polimi.model.ltltoba;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import it.polimi.automata.BA;
import it.polimi.automata.labeling.Label;
import it.polimi.automata.labeling.impl.LabelFactoryImpl;
import it.polimi.automata.state.State;
import it.polimi.automata.state.impl.StateFactoryImpl;
import it.polimi.automata.state.impl.StateImpl;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.impl.ClaimTransitionFactoryImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;
import edu.uci.ics.jung.io.GraphIOException;

public class LTLtoBATransformerTest1 {

	@Mock
	private StateFactoryImpl stateFactory;
	
	@Mock
	private ClaimTransitionFactoryImpl<Label> transitionFactory;

	@Mock
	private StateImpl state;
	
	@Mock
	private Transition<Label> transition;
	
	@Mock
	private LabelFactoryImpl labelFactory;
	
	@Before
	public void setUp() throws GraphIOException {
		MockitoAnnotations.initMocks(this);
		when(stateFactory.create()).thenReturn(state);
		Set<IGraphProposition> propositions=new HashSet<IGraphProposition>();
		propositions.add(new GraphProposition("a", false));
		Label label=new LabelFactoryImpl().create(propositions);
		Set<Label> labels=new HashSet<Label>();
		labels.add(label);
		when(transitionFactory.create(labels)).thenReturn(transition);
		when(labelFactory.create(propositions)).thenReturn(label);
		when(transition.getLabels()).thenReturn(labels);
	}
	
	@Test
	public void test() {
		LTLtoBATransformer<Label, State, Transition<Label>> ltlToBATransformer;
		ltlToBATransformer=new LTLtoBATransformer<Label, State, Transition<Label>>(
				stateFactory, transitionFactory, labelFactory);
		
		BA<Label, State, Transition<Label>> ba=ltlToBATransformer.transform("[]a");
		
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
