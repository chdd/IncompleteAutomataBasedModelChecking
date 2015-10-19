package it.polimi.automata.state;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author Claudio Menghi
 * 
 */
@RunWith(value=Parameterized.class)
public class IntersectionStateFactoryTest {

	private String expected;
	private State state1;
	private State state2;
	private Integer number;
	
	@Parameters
	public static Collection<Object[]> getTestParameters(){
		return Arrays.asList(new Object[][] {     
                { "1 - 2 - 0", new StateFactory().create("1",1), new StateFactory().create("2",2), 0},
                { "3 - 4 - 1", new StateFactory().create("3",3), new StateFactory().create("4",4), 1},
                { "5 - 6 - 2", new StateFactory().create("5",5), new StateFactory().create("6",6), 2},
          });
	}
	
	public IntersectionStateFactoryTest(String expected, State modelState, State claimState, Integer number){
		this.expected=expected;
		this.state1=modelState;
		this.state2=claimState;
		this.number=number;
	}
	
	/**
	 * Test method for {@link it.polimi.automata.state.IntersectionStateFactory#create()}.
	 * creates an intersection state from the model state the claim state and the number passes as parameter
	 */
	@Test
	public void testCreate() {
		assertNotNull(new IntersectionStateFactory().create(state1, state2, number));
		assertEquals("The state must be correctry named", expected, new IntersectionStateFactory().create(state1, state2, number).getName());
	}
	
	/**
	 * Test method for {@link it.polimi.automata.state.IntersectionStateFactory#create()}.
	 * 
	 */
	@Test(expected=NullPointerException.class)
	public void testCreateNullModelState() {
		new IntersectionStateFactory().create(null, state2, number);
	}
	
	/**
	 * Test method for {@link it.polimi.automata.state.IntersectionStateFactory#create()}.
	 * 
	 */
	@Test(expected=NullPointerException.class)
	public void testCreateNullClaimState() {
		new IntersectionStateFactory().create(state1, null, number);
	}
	
	/**
	 * Test method for {@link it.polimi.automata.state.IntersectionStateFactory#create()}.
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCreateNumberHigherThanTwo() {
		new IntersectionStateFactory().create(state1, state2, 5);
	}
	
	/**
	 * Test method for {@link it.polimi.automata.state.IntersectionStateFactory#create()}.
	 * 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCreateNumberLowerThanZero() {
		new IntersectionStateFactory().create(state1, state2, -1);
	}
}
