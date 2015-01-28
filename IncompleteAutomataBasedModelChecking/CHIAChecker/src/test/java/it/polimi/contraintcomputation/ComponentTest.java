/**
 * 
 */
package it.polimi.contraintcomputation;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import it.polimi.automata.state.State;
import it.polimi.automata.state.impl.StateFactoryImpl;

import org.junit.Test;

/**
 * @author claudiomenghi
 *
 */
public class ComponentTest {

	

	/**
	 * Test method for {@link it.polimi.contraintcomputation.Component#Component()}.
	 */
	@Test
	public void testComponent() {
		Component<State> component=new Component<State>();
		assertNotNull(component);
		assertTrue(component.getStates().isEmpty());
	}

	/**
	 * Test method for {@link it.polimi.contraintcomputation.Component#Component(null;)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testComponentSetOfSNull() {
		new Component<State>(null);
	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.Component#Component(java.util.Set)}.
	 */
	@Test
	public void testComponentSetOfS() {
		Set<State> states=new HashSet<State>();
		State state1=new StateFactoryImpl().create("state1");
		State state2=new StateFactoryImpl().create("state2");
		states.add(state1);
		states.add(state2);
		
		Component<State> component=new Component<State>(states);
		assertNotNull(component);
		assertFalse(component.getStates().isEmpty());
		assertTrue(component.getStates().contains(state1));
		assertTrue(component.getStates().contains(state2));
		assertTrue(component.getStates().size()==2);
	}

	/**
	 * Test method for {@link it.polimi.contraintcomputation.Component#add(null)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testAddNull() {
		new Component<State>().add(null);
	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.Component#add(it.polimi.automata.state.State)}.
	 */
	@Test
	public void testAdd() {
		Set<State> states=new HashSet<State>();
		State state1=new StateFactoryImpl().create("state1");
		State state2=new StateFactoryImpl().create("state2");
		State state3=new StateFactoryImpl().create("state3");
		states.add(state1);
		states.add(state2);
		
		Component<State> component=new Component<State>(states);
		component.add(state3);
		assertNotNull(component);
		assertFalse(component.getStates().isEmpty());
		assertTrue(component.getStates().contains(state1));
		assertTrue(component.getStates().contains(state2));
		assertTrue(component.getStates().contains(state3));
		assertTrue(component.getStates().size()==3);
	}

	/**
	 * Test method for {@link it.polimi.contraintcomputation.Component#addAll(java.util.Set)}.
	 */
	@Test(expected=NullPointerException.class)
	public void testAddAllNull() {
		Component<State> component=new Component<State>();
		component.addAll(null);
		
	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.Component#addAll(java.util.Set)}.
	 */
	@Test
	public void testAddAll() {
		Set<State> set1=new HashSet<State>();
		Set<State> set2=new HashSet<State>();
		
		State state1=new StateFactoryImpl().create("state1");
		State state2=new StateFactoryImpl().create("state2");
		State state3=new StateFactoryImpl().create("state3");
		State state4=new StateFactoryImpl().create("state4");
		set1.add(state1);
		set1.add(state2);
		set2.add(state3);
		set2.add(state4);
		
		Component<State> component=new Component<State>(set1);
		component.addAll(set2);
		assertNotNull(component);
		assertFalse(component.getStates().isEmpty());
		assertTrue(component.getStates().contains(state1));
		assertTrue(component.getStates().contains(state2));
		assertTrue(component.getStates().contains(state3));
		assertTrue(component.getStates().contains(state4));
		assertTrue(component.getStates().size()==4);
	}


	/**
	 * Test method for {@link it.polimi.contraintcomputation.Component#getStates()}.
	 */
	@Test
	public void testGetStates() {
		Set<State> set1=new HashSet<State>();
		Set<State> set2=new HashSet<State>();
		
		State state1=new StateFactoryImpl().create("state1");
		State state2=new StateFactoryImpl().create("state2");
		State state3=new StateFactoryImpl().create("state3");
		State state4=new StateFactoryImpl().create("state4");
		set1.add(state1);
		set1.add(state2);
		set2.add(state3);
		set2.add(state4);
		
		Component<State> component=new Component<State>(set1);
		component.addAll(set2);
		assertNotNull(component);
		assertFalse(component.getStates().isEmpty());
		assertTrue(component.getStates().contains(state1));
		assertTrue(component.getStates().contains(state2));
		assertTrue(component.getStates().contains(state3));
		assertTrue(component.getStates().contains(state4));
		assertTrue(component.getStates().size()==4);
	}

	/**
	 * Test method for {@link it.polimi.contraintcomputation.Component#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		Set<State> set1=new HashSet<State>();
		Set<State> set2=new HashSet<State>();
		
		State state1=new StateFactoryImpl().create("state1");
		State state2=new StateFactoryImpl().create("state2");
		set1.add(state1);
		set1.add(state2);
		set2.add(state1);
		set2.add(state2);
		
		Component<State> component1=new Component<State>(set1);
		Component<State> component2=new Component<State>(set2);
		
		assertTrue(component1.hashCode()==component2.hashCode());
		
	}
	

	/**
	 * Test method for {@link it.polimi.contraintcomputation.Component#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		Set<State> set1=new HashSet<State>();
		Set<State> set2=new HashSet<State>();
		
		State state1=new StateFactoryImpl().create("state1");
		State state2=new StateFactoryImpl().create("state2");
		set1.add(state1);
		set1.add(state2);
		set2.add(state1);
		set2.add(state2);
		
		Component<State> component1=new Component<State>(set1);
		Component<State> component2=new Component<State>(set2);
		
		assertFalse(component1.equals(null));
		assertFalse(component1.equals(set1));
		assertTrue(component1.equals(component2));
	}
	
	/**
	 * Test method for {@link it.polimi.contraintcomputation.Component#toString()}.
	 */
	@Test
	public void testToString() {
		Set<State> set1=new HashSet<State>();
		
		State state1=new StateFactoryImpl().create("state1", 1);
		State state2=new StateFactoryImpl().create("state2", 2);
		set1.add(state1);
		set1.add(state2);
		
		
		Component<State> component1=new Component<State>(set1);
		
		assertEquals("<1: state1, 2: state2>", component1.toString());
		
		Component<State> component2=new Component<State>();
		
		assertEquals("<>", component2.toString());
	}

}
