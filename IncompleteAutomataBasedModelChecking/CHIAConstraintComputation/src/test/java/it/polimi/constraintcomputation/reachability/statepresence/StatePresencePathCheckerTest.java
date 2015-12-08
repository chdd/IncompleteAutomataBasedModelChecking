/**
 * 
 */
package it.polimi.constraintcomputation.reachability.statepresence;

import static org.junit.Assert.*;
import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ModelTransitionFactory;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * @author Claudio1
 *
 */
public class StatePresencePathCheckerTest {

    
    /**
     * Test method for {@link it.polimi.constraintcomputation.reachability.statepresence.StatePresencePathChecker#StatePresencePathChecker(null, java.util.Set, java.util.Set)}.
     */
    @Test(expected=NullPointerException.class)
    public void testStatePresencePathChecker1() {
        new StatePresencePathChecker(null, new HashSet<State>(), new HashSet<State>());
    }
    
    /**
     * Test method for {@link it.polimi.constraintcomputation.reachability.statepresence.StatePresencePathChecker#StatePresencePathChecker(it.polimi.automata.BA, null, java.util.Set)}.
     */
    @Test(expected=NullPointerException.class)
    public void testStatePresencePathChecker2() {
        new StatePresencePathChecker(new BA(new ModelTransitionFactory()), null, new HashSet<State>());
    }
    /**
     * Test method for {@link it.polimi.constraintcomputation.reachability.statepresence.StatePresencePathChecker#StatePresencePathChecker(it.polimi.automata.BA, java.util.Set, null)}.
     */
    @Test(expected=NullPointerException.class)
    public void testStatePresencePathChecker3() {
        new StatePresencePathChecker(new BA(new ModelTransitionFactory()), new HashSet<State>(), null);
    }
    
    
    /**
     * Test method for {@link it.polimi.constraintcomputation.reachability.statepresence.StatePresencePathChecker#StatePresencePathChecker(it.polimi.automata.BA, java.util.Set, java.util.Set)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testStatePresencePathChecker4() {
        
        BA ba =new BA(new ModelTransitionFactory());
        State state1=new StateFactory().create("name", 1);
        State state2=new StateFactory().create("name", 2);
        State state3=new StateFactory().create("name", 3);
        
        ba.addState(state1);
        ba.addState(state2);
        ba.addState(state3);
        
        Set<State> statesThatCanBeVisited=new HashSet<State>();
        statesThatCanBeVisited.add(state1);
        statesThatCanBeVisited.add(state2);
        statesThatCanBeVisited.add(state3);
        
        Set<State> interestingStates=new HashSet<State>();
        interestingStates.add(new StateFactory().create());
        new StatePresencePathChecker(ba, statesThatCanBeVisited, interestingStates);
    }
    
    /**
     * Test method for {@link it.polimi.constraintcomputation.reachability.statepresence.StatePresencePathChecker#StatePresencePathChecker(it.polimi.automata.BA, java.util.Set, java.util.Set)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testStatePresencePathChecker5() {
        
        BA ba =new BA(new ModelTransitionFactory());
        State state1=new StateFactory().create("name", 1);
        State state2=new StateFactory().create("name", 2);
        State state3=new StateFactory().create("name", 3);
        State state4=new StateFactory().create("name", 4);
        
        ba.addState(state1);
        ba.addState(state2);
        ba.addState(state3);
        ba.addState(state4);
        
        Set<State> statesThatCanBeVisited=new HashSet<State>();
        statesThatCanBeVisited.add(state1);
        statesThatCanBeVisited.add(state2);
        statesThatCanBeVisited.add(state3);
        
        Set<State> interestingStates=new HashSet<State>();
        interestingStates.add(new StateFactory().create());
        new StatePresencePathChecker(ba, statesThatCanBeVisited, interestingStates);
    }
    
    /**
     * Test method for {@link it.polimi.constraintcomputation.reachability.statepresence.StatePresencePathChecker#StatePresencePathChecker(it.polimi.automata.BA, java.util.Set, java.util.Set)}.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testStatePresencePathChecker6() {
        
        BA ba =new BA(new ModelTransitionFactory());
        State state1=new StateFactory().create("name", 1);
        State state2=new StateFactory().create("name", 2);
        State state3=new StateFactory().create("name", 3);
        
        
        ba.addState(state1);
        ba.addState(state2);
        ba.addState(state3);
        
        Set<State> statesThatCanBeVisited=new HashSet<State>();
        statesThatCanBeVisited.add(state1);
        statesThatCanBeVisited.add(state2);
        statesThatCanBeVisited.add(state3);
        
        Set<State> interestingStates=new HashSet<State>();
        interestingStates.add(new StateFactory().create());
        new StatePresencePathChecker(ba, statesThatCanBeVisited, interestingStates);
    }
    
    
    
    /**
     * Test method for {@link it.polimi.constraintcomputation.reachability.statepresence.StatePresencePathChecker#StatePresencePathChecker(it.polimi.automata.BA, java.util.Set, java.util.Set)}.
     */
    @Test
    public void testStatePresencePathChecker7() {
        BA ba =new BA(new ModelTransitionFactory());
        State state1=new StateFactory().create("name", 1);
        State state2=new StateFactory().create("name", 2);
        State state3=new StateFactory().create("name", 3);
        
        
        ba.addState(state1);
        ba.addState(state2);
        ba.addState(state3);
        
        Set<State> statesThatCanBeVisited=new HashSet<State>();
        statesThatCanBeVisited.add(state1);
        statesThatCanBeVisited.add(state2);
        statesThatCanBeVisited.add(state3);
        
        Set<State> interestingStates=new HashSet<State>();
        interestingStates.add(state2);
        new StatePresencePathChecker(ba, statesThatCanBeVisited, interestingStates);
    }
    
    
    
    /**
     * Test method for {@link it.polimi.constraintcomputation.reachability.statepresence.StatePresencePathChecker#isReachableByPassingAnInterestingState(null, it.polimi.automata.state.State)}.
     */
    @Test(expected=NullPointerException.class)
    public void testIsReachableByPassingAnInterestingState1() {
        BA ba =new BA(new ModelTransitionFactory());
        State state1=new StateFactory().create("name", 1);
        State state2=new StateFactory().create("name", 2);
        State state3=new StateFactory().create("name", 3);
        
        
        ba.addState(state1);
        ba.addState(state2);
        ba.addState(state3);
        
        Set<State> statesThatCanBeVisited=new HashSet<State>();
        statesThatCanBeVisited.add(state1);
        statesThatCanBeVisited.add(state2);
        statesThatCanBeVisited.add(state3);
        
        Set<State> interestingStates=new HashSet<State>();
        interestingStates.add(state2);
        StatePresencePathChecker stateChecker=new StatePresencePathChecker(ba, statesThatCanBeVisited, interestingStates);
        stateChecker.perform();
        stateChecker.isReachableByPassingAnInterestingState(null, state3);
    }
    
    @Test(expected=NullPointerException.class)
    public void testIsReachableByPassingAnInterestingState2() {
        BA ba =new BA(new ModelTransitionFactory());
        State state1=new StateFactory().create("name", 1);
        State state2=new StateFactory().create("name", 2);
        State state3=new StateFactory().create("name", 3);
        
        
        ba.addState(state1);
        ba.addState(state2);
        ba.addState(state3);
        
        Set<State> statesThatCanBeVisited=new HashSet<State>();
        statesThatCanBeVisited.add(state1);
        statesThatCanBeVisited.add(state2);
        statesThatCanBeVisited.add(state3);
        
        Set<State> interestingStates=new HashSet<State>();
        interestingStates.add(state2);
        StatePresencePathChecker stateChecker=new StatePresencePathChecker(ba, statesThatCanBeVisited, interestingStates);
        stateChecker.perform();
        stateChecker.isReachableByPassingAnInterestingState(state3, null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testIsReachableByPassingAnInterestingState3() {
        BA ba =new BA(new ModelTransitionFactory());
        State state1=new StateFactory().create("name", 1);
        State state2=new StateFactory().create("name", 2);
        State state3=new StateFactory().create("name", 3);
        
        
        ba.addState(state1);
        ba.addState(state2);
        ba.addState(state3);
        
        Set<State> statesThatCanBeVisited=new HashSet<State>();
        statesThatCanBeVisited.add(state1);
        statesThatCanBeVisited.add(state2);
        statesThatCanBeVisited.add(state3);
        
        Set<State> interestingStates=new HashSet<State>();
        interestingStates.add(state2);
        StatePresencePathChecker stateChecker=new StatePresencePathChecker(ba, statesThatCanBeVisited, interestingStates);
        stateChecker.perform();
        stateChecker.isReachableByPassingAnInterestingState(state3, new StateFactory().create());
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testIsReachableByPassingAnInterestingState4() {
        BA ba =new BA(new ModelTransitionFactory());
        State state1=new StateFactory().create("name", 1);
        State state2=new StateFactory().create("name", 2);
        State state3=new StateFactory().create("name", 3);
        
        
        ba.addState(state1);
        ba.addState(state2);
        ba.addState(state3);
        
        Set<State> statesThatCanBeVisited=new HashSet<State>();
        statesThatCanBeVisited.add(state1);
        statesThatCanBeVisited.add(state2);
        statesThatCanBeVisited.add(state3);
        
        Set<State> interestingStates=new HashSet<State>();
        interestingStates.add(state2);
        StatePresencePathChecker stateChecker=new StatePresencePathChecker(ba, statesThatCanBeVisited, interestingStates);
        stateChecker.perform();
        stateChecker.isReachableByPassingAnInterestingState(new StateFactory().create(), state3);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testIsReachableByPassingAnInterestingState5() {
        BA ba =new BA(new ModelTransitionFactory());
        State state1=new StateFactory().create("name", 1);
        State state2=new StateFactory().create("name", 2);
        State state3=new StateFactory().create("name", 3);
        
        
        ba.addState(state1);
        ba.addState(state2);
        ba.addState(state3);
        
        Set<State> statesThatCanBeVisited=new HashSet<State>();
        statesThatCanBeVisited.add(state1);
        statesThatCanBeVisited.add(state2);
        
        Set<State> interestingStates=new HashSet<State>();
        interestingStates.add(state2);
        StatePresencePathChecker stateChecker=new StatePresencePathChecker(ba, statesThatCanBeVisited, interestingStates);
        stateChecker.perform();
        stateChecker.isReachableByPassingAnInterestingState(state2, state3);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testIsReachableByPassingAnInterestingState6() {
        BA ba =new BA(new ModelTransitionFactory());
        State state1=new StateFactory().create("name", 1);
        State state2=new StateFactory().create("name", 2);
        State state3=new StateFactory().create("name", 3);
        
        
        ba.addState(state1);
        ba.addState(state2);
        ba.addState(state3);
        
        Set<State> statesThatCanBeVisited=new HashSet<State>();
        statesThatCanBeVisited.add(state1);
        statesThatCanBeVisited.add(state2);
        
        Set<State> interestingStates=new HashSet<State>();
        interestingStates.add(state2);
        StatePresencePathChecker stateChecker=new StatePresencePathChecker(ba, statesThatCanBeVisited, interestingStates);
        stateChecker.perform();
        stateChecker.isReachableByPassingAnInterestingState(state3, state2);
    }
    
    @Test(expected=IllegalStateException.class)
    public void testIsReachableByPassingAnInterestingState7() {
        BA ba =new BA(new ModelTransitionFactory());
        State state1=new StateFactory().create("name", 1);
        State state2=new StateFactory().create("name", 2);
        State state3=new StateFactory().create("name", 3);
        
        
        ba.addState(state1);
        ba.addState(state2);
        ba.addState(state3);
        
        Set<State> statesThatCanBeVisited=new HashSet<State>();
        statesThatCanBeVisited.add(state1);
        statesThatCanBeVisited.add(state2);
        
        Set<State> interestingStates=new HashSet<State>();
        interestingStates.add(state2);
        StatePresencePathChecker stateChecker=new StatePresencePathChecker(ba, statesThatCanBeVisited, interestingStates);
        stateChecker.isReachableByPassingAnInterestingState(state1, state2);
    }
    
    
    @Test
    public void testIsReachableByPassingAnInterestingState8() {
        BA ba =new BA(new ModelTransitionFactory());
        State state1=new StateFactory().create("name", 1);
        State state2=new StateFactory().create("name", 2);
        State state3=new StateFactory().create("name", 3);
        
        
        ba.addState(state1);
        ba.addState(state2);
        ba.addState(state3);
        
        ba.addTransition(state1, state2, new ModelTransitionFactory().create());
        ba.addTransition(state2, state3, new ModelTransitionFactory().create());
        
        Set<State> statesThatCanBeVisited=new HashSet<State>();
        statesThatCanBeVisited.add(state1);
        statesThatCanBeVisited.add(state2);
        statesThatCanBeVisited.add(state3);
        
        Set<State> interestingStates=new HashSet<State>();
        interestingStates.add(state2);
        StatePresencePathChecker stateChecker=new StatePresencePathChecker(ba, statesThatCanBeVisited, interestingStates);
        stateChecker.perform();
        assertTrue(stateChecker.isReachableByPassingAnInterestingState(state1, state3));
    }
    
    
    @Test
    public void testIsReachableByPassingAnInterestingState9() {
        BA ba =new BA(new ModelTransitionFactory());
        State state1=new StateFactory().create("name", 1);
        State state2=new StateFactory().create("name", 2);
        State state3=new StateFactory().create("name", 3);
        State state4=new StateFactory().create("name", 4);
        State state5=new StateFactory().create("name", 5);
        State state6=new StateFactory().create("name", 6);        
        
        ba.addState(state1);
        ba.addState(state2);
        ba.addState(state3);
        ba.addState(state4);
        ba.addState(state5);
        ba.addState(state6);
        
        ba.addTransition(state1, state2, new ModelTransitionFactory().create());
        ba.addTransition(state2, state3, new ModelTransitionFactory().create());
        ba.addTransition(state5, state3, new ModelTransitionFactory().create());
        ba.addTransition(state4, state6, new ModelTransitionFactory().create());
        ba.addTransition(state6, state2, new ModelTransitionFactory().create());       
        
        Set<State> statesThatCanBeVisited=new HashSet<State>();
        statesThatCanBeVisited.add(state1);
        statesThatCanBeVisited.add(state2);
        statesThatCanBeVisited.add(state3);
        statesThatCanBeVisited.add(state4);
        statesThatCanBeVisited.add(state5);
        
        Set<State> interestingStates=new HashSet<State>();
        interestingStates.add(state2);
        StatePresencePathChecker stateChecker=new StatePresencePathChecker(ba, statesThatCanBeVisited, interestingStates);
        stateChecker.perform();
        assertTrue(stateChecker.isReachableByPassingAnInterestingState(state1, state3));
        assertFalse(stateChecker.isReachableByPassingAnInterestingState(state5, state3));
        assertFalse(stateChecker.isReachableByPassingAnInterestingState(state4, state3));
    }
    
    @Test
    public void testIsReachableByPassingAnInterestingState10() {
        BA ba =new BA(new ModelTransitionFactory());
        State state1=new StateFactory().create("name", 1);
        State state2=new StateFactory().create("name", 2);
        State state3=new StateFactory().create("name", 3);
        State state4=new StateFactory().create("name", 4);
        State state5=new StateFactory().create("name", 5);
        State state6=new StateFactory().create("name", 6);        
        
        ba.addState(state1);
        ba.addState(state2);
        ba.addState(state3);
        ba.addState(state4);
        ba.addState(state5);
        ba.addState(state6);
        
        ba.addTransition(state1, state2, new ModelTransitionFactory().create());
        ba.addTransition(state2, state3, new ModelTransitionFactory().create());
        ba.addTransition(state5, state3, new ModelTransitionFactory().create());
        ba.addTransition(state4, state6, new ModelTransitionFactory().create());
        ba.addTransition(state6, state2, new ModelTransitionFactory().create());  
        ba.addTransition(state3, state1, new ModelTransitionFactory().create());
        
        Set<State> statesThatCanBeVisited=new HashSet<State>();
        statesThatCanBeVisited.add(state1);
        statesThatCanBeVisited.add(state2);
        statesThatCanBeVisited.add(state3);
        statesThatCanBeVisited.add(state4);
        statesThatCanBeVisited.add(state5);
        
        Set<State> interestingStates=new HashSet<State>();
        interestingStates.add(state2);
        StatePresencePathChecker stateChecker=new StatePresencePathChecker(ba, statesThatCanBeVisited, interestingStates);
        stateChecker.perform();
        assertTrue(stateChecker.isReachableByPassingAnInterestingState(state1, state3));
        assertTrue(stateChecker.isReachableByPassingAnInterestingState(state1, state1));
        
        assertTrue(stateChecker.isReachableByPassingAnInterestingState(state5, state3));
        assertFalse(stateChecker.isReachableByPassingAnInterestingState(state4, state3));
    }
    
    @Test
    public void testIsReachableByPassingAnInterestingState11() {
        BA ba =new BA(new ModelTransitionFactory());
        State state1=new StateFactory().create("name", 1);
        State state2=new StateFactory().create("name", 2);
        State state3=new StateFactory().create("name", 3);
        State state4=new StateFactory().create("name", 4);
        State state5=new StateFactory().create("name", 5);
        State state6=new StateFactory().create("name", 6);        
        
        ba.addState(state1);
        ba.addState(state2);
        ba.addState(state3);
        ba.addState(state4);
        ba.addState(state5);
        ba.addState(state6);
        
        ba.addTransition(state1, state2, new ModelTransitionFactory().create());
        ba.addTransition(state2, state3, new ModelTransitionFactory().create());
        ba.addTransition(state5, state3, new ModelTransitionFactory().create());
        ba.addTransition(state4, state6, new ModelTransitionFactory().create());
        ba.addTransition(state6, state2, new ModelTransitionFactory().create());  
        ba.addTransition(state3, state1, new ModelTransitionFactory().create());
        
        Set<State> statesThatCanBeVisited=new HashSet<State>();
        statesThatCanBeVisited.add(state1);
        statesThatCanBeVisited.add(state2);
        statesThatCanBeVisited.add(state3);
        statesThatCanBeVisited.add(state4);
        statesThatCanBeVisited.add(state5);
        
        Set<State> interestingStates=new HashSet<State>();
        interestingStates.add(state2);
        StatePresencePathChecker stateChecker=new StatePresencePathChecker(ba, statesThatCanBeVisited, interestingStates);
        stateChecker.perform();
        assertTrue(stateChecker.isReachableByPassingAnInterestingState(state1, state3));
        assertTrue(stateChecker.isReachableByPassingAnInterestingState(state5, state3));
        assertFalse(stateChecker.isReachableByPassingAnInterestingState(state4, state3));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testIsReachableByPassingAnInterestingState12() {
        BA ba =new BA(new ModelTransitionFactory());
        State state1=new StateFactory().create("name", 1);
        State state2=new StateFactory().create("name", 2);
        State state3=new StateFactory().create("name", 3);
        State state4=new StateFactory().create("name", 4);
        State state5=new StateFactory().create("name", 5);
        State state6=new StateFactory().create("name", 6);    
        State state7=new StateFactory().create("name", 7);    
        
        ba.addState(state1);
        ba.addState(state2);
        ba.addState(state3);
        ba.addState(state4);
        ba.addState(state5);
        ba.addState(state6);
        ba.addState(state7);
        
        ba.addTransition(state1, state2, new ModelTransitionFactory().create());
        ba.addTransition(state2, state3, new ModelTransitionFactory().create());
        ba.addTransition(state5, state3, new ModelTransitionFactory().create());
        ba.addTransition(state4, state6, new ModelTransitionFactory().create());
        ba.addTransition(state6, state2, new ModelTransitionFactory().create());  
        ba.addTransition(state3, state1, new ModelTransitionFactory().create());
        ba.addTransition(state3, state7, new ModelTransitionFactory().create());
        ba.addTransition(state1, state7, new ModelTransitionFactory().create());
        
        Set<State> statesThatCanBeVisited=new HashSet<State>();
        statesThatCanBeVisited.add(state1);
        statesThatCanBeVisited.add(state2);
        statesThatCanBeVisited.add(state3);
        statesThatCanBeVisited.add(state4);
        statesThatCanBeVisited.add(state5);
        
        Set<State> interestingStates=new HashSet<State>();
        interestingStates.add(state2);
        StatePresencePathChecker stateChecker=new StatePresencePathChecker(ba, statesThatCanBeVisited, interestingStates);
        stateChecker.perform();
       stateChecker.isReachableByPassingAnInterestingState(state1, state7);
    }
    
    @Test
    public void testIsReachableByPassingAnInterestingState13() {
        BA ba =new BA(new ModelTransitionFactory());
        State state1=new StateFactory().create("name", 1);
        State state2=new StateFactory().create("name", 2);
        State state3=new StateFactory().create("name", 3);
        State state4=new StateFactory().create("name", 4);
        State state5=new StateFactory().create("name", 5);
        State state6=new StateFactory().create("name", 6);    
        State state7=new StateFactory().create("name", 7);    
        
        ba.addState(state1);
        ba.addState(state2);
        ba.addState(state3);
        ba.addState(state4);
        ba.addState(state5);
        ba.addState(state6);
        ba.addState(state7);
        
        ba.addTransition(state1, state2, new ModelTransitionFactory().create());
        ba.addTransition(state2, state3, new ModelTransitionFactory().create());
        ba.addTransition(state5, state3, new ModelTransitionFactory().create());
        ba.addTransition(state4, state6, new ModelTransitionFactory().create());
        ba.addTransition(state6, state2, new ModelTransitionFactory().create());  
        ba.addTransition(state3, state1, new ModelTransitionFactory().create());
        ba.addTransition(state3, state7, new ModelTransitionFactory().create());
        ba.addTransition(state1, state7, new ModelTransitionFactory().create());
        
        Set<State> statesThatCanBeVisited=new HashSet<State>();
        statesThatCanBeVisited.add(state1);
        statesThatCanBeVisited.add(state2);
        statesThatCanBeVisited.add(state3);
        statesThatCanBeVisited.add(state4);
        statesThatCanBeVisited.add(state5);
        
        Set<State> interestingStates=new HashSet<State>();
        interestingStates.add(state2);
        StatePresencePathChecker stateChecker=new StatePresencePathChecker(ba, statesThatCanBeVisited, interestingStates);
        stateChecker.perform();
       stateChecker.isReachableByPassingAnInterestingState(state2, state3);
    }
}
