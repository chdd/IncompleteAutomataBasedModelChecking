package it.polimi.model.impl.transitions;

import static org.junit.Assert.assertTrue;
import it.polimi.model.impl.labeling.DNFFormulaImpl;
import it.polimi.model.interfaces.labeling.DNFFormula;

import org.junit.Test;

public class TransitionFactoryTest {

	@Test
	public void testConstructor1() {
		TransitionFactoryImpl factory=new TransitionFactoryImpl();
		Transition t=factory.create();
		assertTrue(t.getId()>=0);
		assertTrue(t.getCondition()!=null);
	}
	
	@Test
	public void testConstructor2() {
		TransitionFactoryImpl factory=new TransitionFactoryImpl();
		DNFFormula f1=DNFFormulaImpl.loadFromString("a");
		Transition t=factory.create(f1);
		assertTrue(t.getId()>=0);
		assertTrue(t.getCondition().equals(f1));
	}
	
	@Test(expected=NullPointerException.class)
	public void testConstructor3() {
		TransitionFactoryImpl factory=new TransitionFactoryImpl();
		factory.create(null);
	}
	
	@Test
	public void testConstructor4() {
		TransitionFactoryImpl factory=new TransitionFactoryImpl();
		DNFFormula f1=DNFFormulaImpl.loadFromString("a");
		
		Transition t=factory.create(0, f1);
		assertTrue(t.getId()==0);
		assertTrue(t.getCondition().equals(f1));
	}
	
	@Test(expected=NullPointerException.class)
	public void testConstructor5() {
		TransitionFactoryImpl factory=new TransitionFactoryImpl();
		factory.create(0, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructor6() {
		TransitionFactoryImpl factory=new TransitionFactoryImpl();
		DNFFormula f1=DNFFormulaImpl.loadFromString("a");
		factory.create(-1, f1);
	}
}
