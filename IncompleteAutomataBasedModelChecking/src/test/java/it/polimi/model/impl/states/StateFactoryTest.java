package it.polimi.model.impl.states;

import static org.junit.Assert.*;

import org.junit.Test;

public class StateFactoryTest {

	@Test
	public void testConstructor1() {
		State s=new StateFactoryImpl().create();
		assertTrue(s.getId()>=0);
		assertTrue(s.getName()!=null);
	}
	
	@Test
	public void testConstructor2() {
		State s=new StateFactoryImpl().create("name");
		assertTrue(s.getId()>=0);
		assertTrue(s.getName().equals("name"));
	}
	
	@Test
	public void testConstructor3() {
		State s=new StateFactoryImpl().create("name", 5);
		assertTrue(s.getId()==5);
		assertTrue(s.getName().equals("name"));
	}
	
	@Test(expected=NullPointerException.class)
	public void testConstructor4() {
		new StateFactoryImpl().create(null);
	}
	
	@Test(expected=NullPointerException.class)
	public void testConstructor5() {
		new StateFactoryImpl().create(null, 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructor6() {
		new StateFactoryImpl().create("prova", -10);
	}
	

}
