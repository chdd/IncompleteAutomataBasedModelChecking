package it.polimi.model.ltltoba;

import static org.junit.Assert.assertTrue;
import it.polimi.automata.BA;

import org.junit.Before;
import org.junit.Test;

public class LTLtoBATransformerTest1 {

	
	
	@Before
	public void setUp()  {
	}
	
	@Test
	public void test() {
		LTLtoBATransformer ltlToBATransformer;
		ltlToBATransformer=new LTLtoBATransformer("[]a");
		
		BA ba=ltlToBATransformer.perform();
		
		assertTrue(ba.getInitialStates().size()==1);
		assertTrue(ba.getAcceptStates().size()==1);
		assertTrue(ba.getStates().size()==1);
		assertTrue(ba.getTransitions().size()==1);
		assertTrue(!ba.getInitialStates().isEmpty());
		assertTrue(!ba.getAcceptStates().isEmpty());
		assertTrue(!ba.getStates().isEmpty());
		assertTrue(ba.getTransitions().size()==1);
	}
	
}
