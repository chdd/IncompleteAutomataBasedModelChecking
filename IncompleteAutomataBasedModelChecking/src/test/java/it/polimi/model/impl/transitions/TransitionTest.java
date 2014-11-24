package it.polimi.model.impl.transitions;

import static org.junit.Assert.*;
import it.polimi.model.impl.labeling.DNFFormula;
import it.polimi.model.impl.states.State;

import org.junit.Test;

public class TransitionTest {

	@Test
	public void testConstructor1() {
		Transition t=new Transition(new DNFFormula<State>(), 0);
		assertTrue(t.getId()==0);
		assertTrue(t.getCondition()!=null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testConstructor2() {
		new Transition(new DNFFormula<State>(), -1);
	}
	
	@Test(expected = NullPointerException.class)
	public void testConstructor3() {
		new Transition(null, 0);
	}
}
