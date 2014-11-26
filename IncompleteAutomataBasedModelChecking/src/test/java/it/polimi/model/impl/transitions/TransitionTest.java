package it.polimi.model.impl.transitions;

import static org.junit.Assert.assertTrue;
import it.polimi.model.impl.labeling.DNFFormulaImpl;
import it.polimi.model.interfaces.labeling.DNFFormula;

import org.junit.Test;

public class TransitionTest {

	@Test
	public void testConstructor1() {
		Transition t=new Transition(new DNFFormulaImpl(), 0);
		assertTrue(t.getId()==0);
		assertTrue(t.getCondition()!=null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor2() {
		new Transition(new DNFFormulaImpl(), -1);
	}
	
	@Test(expected = NullPointerException.class)
	public void testConstructor3() {
		new Transition(null, 0);
	}
	
	@Test
	public void testSetCondition() {
		DNFFormula f1=DNFFormulaImpl.loadFromString("a");
		Transition t=new Transition(f1, 0);
		assertTrue(t.getId()==0);
		assertTrue(t.getCondition().equals(f1));
		
		DNFFormula f2=DNFFormulaImpl.loadFromString("b");
		t.setCondition(f2);
		assertTrue(t.getCondition().equals(f2));
	}
}
