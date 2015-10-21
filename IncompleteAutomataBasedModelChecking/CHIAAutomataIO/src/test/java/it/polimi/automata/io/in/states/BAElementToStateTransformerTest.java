package it.polimi.automata.io.in.states;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.w3c.dom.Element;

public class BAElementToStateTransformerTest {

	@Mock
	private Element nullNameElement;
	
	@Mock
	private Element nullIdElement;
	
	@Mock
	private Element stateElement;
	
	@Mock
	private Element noInitialNoAcceptingState;
	
	@Mock
	private Element noInitialNoAcceptingStateEmptyString;
	
	public BAElementToStateTransformerTest(){
		MockitoAnnotations.initMocks(this);
		when(nullNameElement.hasAttribute("name")).thenReturn(false);
		when(nullNameElement.hasAttribute("id")).thenReturn(true);
		when(nullIdElement.hasAttribute("name")).thenReturn(true);
		when(nullIdElement.hasAttribute("id")).thenReturn(false);
		
		when(noInitialNoAcceptingState.hasAttribute("id")).thenReturn(true);
		when(noInitialNoAcceptingState.hasAttribute("name")).thenReturn(true);
		when(noInitialNoAcceptingState.getAttribute("id")).thenReturn("6");
		when(noInitialNoAcceptingState.getAttribute("name")).thenReturn("name2");
		when(noInitialNoAcceptingState.getAttribute("initial")).thenReturn("false");
		when(noInitialNoAcceptingState.getAttribute("accepting")).thenReturn("false");
		
		
		
		when(noInitialNoAcceptingStateEmptyString.hasAttribute("id")).thenReturn(true);
		when(noInitialNoAcceptingStateEmptyString.hasAttribute("name")).thenReturn(true);
		when(noInitialNoAcceptingStateEmptyString.getAttribute("id")).thenReturn("5");
		when(noInitialNoAcceptingStateEmptyString.getAttribute("name")).thenReturn("name");
		when(noInitialNoAcceptingStateEmptyString.getAttribute("initial")).thenReturn("");
		when(noInitialNoAcceptingStateEmptyString.getAttribute("accepting")).thenReturn("");
		
		when(stateElement.hasAttribute("id")).thenReturn(true);
		when(stateElement.hasAttribute("name")).thenReturn(true);
		when(stateElement.getAttribute("id")).thenReturn("1");
		when(stateElement.getAttribute("name")).thenReturn("one");
		when(stateElement.getAttribute("initial")).thenReturn("true");
		when(stateElement.getAttribute("accepting")).thenReturn("true");
		
	}
	/**
	 * Test method for {@link  it.polimi.automata.io.in.propositions.BAElementToStateTransformer#BAElementToStateTransformer(BA)}.
	 */
	@Test(expected=NullPointerException.class)
	public void test() {
		new BAElementToStateTransformer(null);
	}

	
	/**
	 * Test method for {@link  it.polimi.automata.io.in.propositions.BAElementToStateTransformer#transform(Element)}.
	 */
	@Test(expected=NullPointerException.class)
	public void transform_Null() {
		BA ba=new BA(new ClaimTransitionFactory());
		BAElementToStateTransformer elementToStateTransformer=new BAElementToStateTransformer(ba);
		elementToStateTransformer.transform(null);
	}
	
	/**
	 * Test method for {@link  it.polimi.automata.io.in.propositions.BAElementToStateTransformer#transform(Element)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void transform_NoName() {
		BA ba=new BA(new ClaimTransitionFactory());
		BAElementToStateTransformer elementToStateTransformer=new BAElementToStateTransformer(ba);
		elementToStateTransformer.transform(nullNameElement);
	}
	
	/**
	 * Test method for {@link  it.polimi.automata.io.in.propositions.BAElementToStateTransformer#transform(Element)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void transform_NoId() {
		BA ba=new BA(new ClaimTransitionFactory());
		BAElementToStateTransformer elementToStateTransformer=new BAElementToStateTransformer(ba);
		elementToStateTransformer.transform(nullIdElement);
	}
	
	/**
	 * Test method for {@link  it.polimi.automata.io.in.propositions.BAElementToStateTransformer#transform(Element)}.
	 */
	@Test
	public void transformNoInitialNoAccepting() {
		BA ba=new BA(new ClaimTransitionFactory());
		BAElementToStateTransformer elementToStateTransformer=new BAElementToStateTransformer(ba);
		elementToStateTransformer.transform(noInitialNoAcceptingStateEmptyString);
		elementToStateTransformer.transform(noInitialNoAcceptingState);
		
		State state=new StateFactory().create("name", 5);
		assertTrue("The state must be contained into the set of the states", ba.getStates().contains(state));
		assertTrue("The state must not be contained into the set of the accepting states", !ba.getAcceptStates().contains(state));
		assertTrue("The state must not be contained into the set of the initial states", !ba.getInitialStates().contains(state));
		
		State state2=new StateFactory().create("name2", 6);
		assertTrue("The state must be contained into the set of the states", ba.getStates().contains(state2));
		assertTrue("The state must not be contained into the set of the accepting states", !ba.getAcceptStates().contains(state2));
		assertTrue("The state must not be contained into the set of the initial states", !ba.getInitialStates().contains(state2));
	}
	
	/**
	 * Test method for {@link  it.polimi.automata.io.in.propositions.BAElementToStateTransformer#transform(Element)}.
	 */
	@Test
	public void transform() {
		BA ba=new BA(new ClaimTransitionFactory());
		BAElementToStateTransformer elementToStateTransformer=new BAElementToStateTransformer(ba);
		elementToStateTransformer.transform(stateElement);
		
		State state=new StateFactory().create("one", 1);
		assertTrue("The state must be contained into the set of the states", ba.getStates().contains(state));
		assertTrue("The state must be contained into the set of the accepting states", ba.getAcceptStates().contains(state));
		assertTrue("The state must be contained into the set of the initial states", ba.getInitialStates().contains(state));
		
	}
	
}
