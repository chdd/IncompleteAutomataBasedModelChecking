package it.polimi.automata.io.in.states;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.w3c.dom.Element;

public class IBAElementToStateTransformerTest {

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
	
	public IBAElementToStateTransformerTest(){
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
		when(noInitialNoAcceptingState.getAttribute("blackbox")).thenReturn("false");
		
		
		
		when(noInitialNoAcceptingStateEmptyString.hasAttribute("id")).thenReturn(true);
		when(noInitialNoAcceptingStateEmptyString.hasAttribute("name")).thenReturn(true);
		when(noInitialNoAcceptingStateEmptyString.getAttribute("id")).thenReturn("5");
		when(noInitialNoAcceptingStateEmptyString.getAttribute("name")).thenReturn("name");
		when(noInitialNoAcceptingStateEmptyString.getAttribute("initial")).thenReturn("");
		when(noInitialNoAcceptingStateEmptyString.getAttribute("accepting")).thenReturn("");
		when(noInitialNoAcceptingStateEmptyString.getAttribute("blackbox")).thenReturn("");
		
		
		when(stateElement.hasAttribute("id")).thenReturn(true);
		when(stateElement.hasAttribute("name")).thenReturn(true);
		when(stateElement.getAttribute("id")).thenReturn("1");
		when(stateElement.getAttribute("name")).thenReturn("one");
		when(stateElement.getAttribute("initial")).thenReturn("true");
		when(stateElement.getAttribute("accepting")).thenReturn("true");
		when(stateElement.getAttribute("blackbox")).thenReturn("true");
		
	}
	/**
	 * Test method for {@link  it.polimi.automata.io.in.propositions.IBAElementToStateTransformer#BAElementToStateTransformer(BA)}.
	 */
	@Test(expected=NullPointerException.class)
	public void test() {
		new IBAElementToStateTransformer(null);
	}

	
	/**
	 * Test method for {@link  it.polimi.automata.io.in.propositions.IBAElementToStateTransformer#transform(Element)}.
	 */
	@Test(expected=NullPointerException.class)
	public void transform_Null() {
		IBA ba=new IBA(new ClaimTransitionFactory());
		IBAElementToStateTransformer elementToStateTransformer=new IBAElementToStateTransformer(ba);
		elementToStateTransformer.transform(null);
	}
	
	/**
	 * Test method for {@link  it.polimi.automata.io.in.propositions.IBAElementToStateTransformer#transform(Element)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void transform_NoName() {
		IBA ba=new IBA(new ClaimTransitionFactory());
		IBAElementToStateTransformer elementToStateTransformer=new IBAElementToStateTransformer(ba);
		elementToStateTransformer.transform(nullNameElement);
	}
	
	/**
	 * Test method for {@link  it.polimi.automata.io.in.propositions.IBAElementToStateTransformer#transform(Element)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void transform_NoId() {
		IBA ba=new IBA(new ClaimTransitionFactory());
		IBAElementToStateTransformer elementToStateTransformer=new IBAElementToStateTransformer(ba);
		elementToStateTransformer.transform(nullIdElement);
	}
	
	/**
	 * Test method for {@link  it.polimi.automata.io.in.propositions.IBAElementToStateTransformer#transform(Element)}.
	 */
	@Test
	public void transformNoInitialNoAccepting() {
		IBA iba=new IBA(new ClaimTransitionFactory());
		IBAElementToStateTransformer elementToStateTransformer=new IBAElementToStateTransformer(iba);
		elementToStateTransformer.transform(noInitialNoAcceptingStateEmptyString);
		elementToStateTransformer.transform(noInitialNoAcceptingState);
		
		State state=new StateFactory().create("name", 5);
		assertTrue("The state must be contained into the set of the states", iba.getStates().contains(state));
		assertTrue("The state must not be contained into the set of the accepting states", !iba.getAcceptStates().contains(state));
		assertTrue("The state must not be contained into the set of the initial states", !iba.getInitialStates().contains(state));
		assertTrue("The state must be contained into the set of the black box states", !iba.getBlackBoxStates().contains(state));
		
		State state2=new StateFactory().create("name2", 6);
		assertTrue("The state must be contained into the set of the states", iba.getStates().contains(state2));
		assertTrue("The state must not be contained into the set of the accepting states", !iba.getAcceptStates().contains(state2));
		assertTrue("The state must not be contained into the set of the initial states", !iba.getInitialStates().contains(state2));
		assertTrue("The state must be contained into the set of the black box states", !iba.getBlackBoxStates().contains(state2));
	}
	
	/**
	 * Test method for {@link  it.polimi.automata.io.in.propositions.IBAElementToStateTransformer#transform(Element)}.
	 */
	@Test
	public void transform() {
		IBA iba=new IBA(new ClaimTransitionFactory());
		IBAElementToStateTransformer elementToStateTransformer=new IBAElementToStateTransformer(iba);
		elementToStateTransformer.transform(stateElement);
		
		State state=new StateFactory().create("one", 1);
		assertTrue("The state must be contained into the set of the states", iba.getStates().contains(state));
		assertTrue("The state must be contained into the set of the accepting states", iba.getAcceptStates().contains(state));
		assertTrue("The state must be contained into the set of the initial states", iba.getInitialStates().contains(state));
		assertTrue("The state must be contained into the set of the black box states", iba.getBlackBoxStates().contains(state));
		
	}
	
}
