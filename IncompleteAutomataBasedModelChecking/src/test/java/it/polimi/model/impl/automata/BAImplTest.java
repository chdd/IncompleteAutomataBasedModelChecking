package it.polimi.model.impl.automata;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactoryImpl;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.impl.transitions.TransitionFactoryImpl;
import it.polimi.model.interfaces.automata.BA;

import org.junit.Test;

public class BAImplTest {

	@Test
	public void testConstructor1() {
		BA<State, Transition> ba=new BAImpl<State, Transition>(new TransitionFactoryImpl(), new StateFactoryImpl());
		assertTrue(ba.getInitialStates().isEmpty());
		assertTrue(ba.getAcceptStates().isEmpty());
		assertTrue(ba.getStates().isEmpty());
		assertFalse(ba.isInitial(new StateFactoryImpl().create()));
	}

	@Test(expected = NullPointerException.class)
	public void testConstructor2() {
		new BAImpl<State, Transition>(null, new StateFactoryImpl());
	}
	
	@Test(expected = NullPointerException.class)
	public void testConstructor3() {
		new BAImpl<State, Transition>(new TransitionFactoryImpl(), null);
	}
	
	@Test
	public void testAddInitialState1(){
		BA<State, Transition> ba=new BAImpl<State, Transition>(new TransitionFactoryImpl(), new StateFactoryImpl());
		State s1=new StateFactoryImpl().create();
		ba.addInitialState(s1);
		assertTrue(ba.getInitialStates().contains(s1));
		assertTrue(ba.getStates().contains(s1));
		assertTrue(ba.isInitial(s1));
	}
	
	@Test(expected = NullPointerException.class)
	public void testAddInitialState2(){
		BA<State, Transition> ba=new BAImpl<State, Transition>(new TransitionFactoryImpl(), new StateFactoryImpl());
		ba.addInitialState(null);
		
	}
	
	@Test
	public void testSetInitialState1(){
		BAImpl<State, Transition> ba=new BAImpl<State, Transition>(new TransitionFactoryImpl(), new StateFactoryImpl());
		State s1=new StateFactoryImpl().create();
		Set<State> newSetOfInitialStates=new HashSet<State>();
		newSetOfInitialStates.add(s1);
		ba.setInitialStates(newSetOfInitialStates);
		assertTrue(ba.getInitialStates().contains(s1));
		assertTrue(ba.getStates().contains(s1));
		assertTrue(ba.isInitial(s1));

	}
	
	@Test(expected = NullPointerException.class)
	public void testSetInitialState2(){
		BAImpl<State, Transition> ba=new BAImpl<State, Transition>(new TransitionFactoryImpl(), new StateFactoryImpl());
		ba.setInitialStates(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void testSetInitialState3(){
		BAImpl<State, Transition> ba=new BAImpl<State, Transition>(new TransitionFactoryImpl(), new StateFactoryImpl());
		Set<State> newSetOfInitialStates=new HashSet<State>();
		newSetOfInitialStates.add(null);
		ba.setInitialStates(newSetOfInitialStates);
		
	}
	
	@Test
	public void testSetAcceptState1(){
		BAImpl<State, Transition> ba=new BAImpl<State, Transition>(new TransitionFactoryImpl(), new StateFactoryImpl());
		State s1=new StateFactoryImpl().create();
		Set<State> newSetOfInitialStates=new HashSet<State>();
		newSetOfInitialStates.add(s1);
		ba.setAcceptStates(newSetOfInitialStates);
		assertTrue(ba.getAcceptStates().contains(s1));
		assertTrue(ba.getStates().contains(s1));
		assertTrue(ba.isAccept(s1));

	}
	
	@Test(expected = NullPointerException.class)
	public void testSetAcceptState2(){
		BAImpl<State, Transition> ba=new BAImpl<State, Transition>(new TransitionFactoryImpl(), new StateFactoryImpl());
		ba.setAcceptStates(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void testSetAcceptState3(){
		BAImpl<State, Transition> ba=new BAImpl<State, Transition>(new TransitionFactoryImpl(), new StateFactoryImpl());
		Set<State> newSetOfInitialStates=new HashSet<State>();
		newSetOfInitialStates.add(null);
		ba.setAcceptStates(newSetOfInitialStates);
		
	}
	
	@Test
	public void testAddState1(){
		BAImpl<State, Transition> ba=new BAImpl<State, Transition>(new TransitionFactoryImpl(), new StateFactoryImpl());
		State s1=new StateFactoryImpl().create();
		ba.addState(s1);
		assertTrue(ba.getStates().contains(s1));
	}
	
	@Test(expected = NullPointerException.class)
	public void testAddState2(){
		BAImpl<State, Transition> ba=new BAImpl<State, Transition>(new TransitionFactoryImpl(), new StateFactoryImpl());
		ba.addState(null);
	}
	
	@Test
	public void testAddStates1(){
		BAImpl<State, Transition> ba=new BAImpl<State, Transition>(new TransitionFactoryImpl(), new StateFactoryImpl());
		State s1=new StateFactoryImpl().create();
		Set<State> states=new HashSet<State>();
		states.add(s1);
		ba.addStates(states);
		assertTrue(ba.getStates().contains(s1));
	}
	
	@Test(expected = NullPointerException.class)
	public void testAddStates2(){
		BAImpl<State, Transition> ba=new BAImpl<State, Transition>(new TransitionFactoryImpl(), new StateFactoryImpl());
		ba.addStates(null);
	}
	
}

