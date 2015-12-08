/**
 * 
 */
package it.polimi.constraintcomputation.cleaner;

import static org.junit.Assert.*;

import java.util.Set;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ModelTransitionFactory;

import org.junit.Test;

/**
 * @author Claudio1
 *
 */
public class AutomatonCleanerTest {

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.cleaner.AutomatonCleaner#AutomatonCleaner(null)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testAutomatonCleaner_NullPointerException() {
        new AutomatonCleaner(null);
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.cleaner.AutomatonCleaner#AutomatonCleaner(it.polimi.automata.BA)}
     * .
     */
    @Test
    public void testAutomatonCleaner() {
        assertNotNull(new AutomatonCleaner(new BA(new ModelTransitionFactory())));
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.cleaner.AutomatonCleaner#clean()}.
     */
    @Test
    public void testClean1() {

        State state1 = new StateFactory().create();
        State state2 = new StateFactory().create();
        State state3 = new StateFactory().create();
        BA ba = new BA(new ModelTransitionFactory());
        ba.addInitialState(state1);
        ba.addAcceptState(state2);
        ba.addState(state3);

        ba.addTransition(state1, state2, new ModelTransitionFactory().create());
        ba.addTransition(state1, state3, new ModelTransitionFactory().create());
        ba.addTransition(state2, state2, new ModelTransitionFactory().create());

        AutomatonCleaner cleaner = new AutomatonCleaner(ba);
        Set<State> statesToBeRemoved = cleaner.clean();

        assertTrue(statesToBeRemoved.contains(state3));
        assertTrue(statesToBeRemoved.size() == 1);
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.cleaner.AutomatonCleaner#clean()}.
     */
    @Test
    public void testClean2() {

        State state1 = new StateFactory().create();
        State state2 = new StateFactory().create();
        State state3 = new StateFactory().create();
        BA ba = new BA(new ModelTransitionFactory());
        ba.addInitialState(state1);
        ba.addAcceptState(state2);
        ba.addState(state3);

        ba.addTransition(state1, state2, new ModelTransitionFactory().create());
        ba.addTransition(state1, state3, new ModelTransitionFactory().create());
        ba.addTransition(state2, state2, new ModelTransitionFactory().create());
        ba.addTransition(state3, state3, new ModelTransitionFactory().create());

        AutomatonCleaner cleaner = new AutomatonCleaner(ba);
        Set<State> statesToBeRemoved = cleaner.clean();

        assertTrue(statesToBeRemoved.contains(state3));
        assertTrue(statesToBeRemoved.size() == 1);

    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.cleaner.AutomatonCleaner#clean()}.
     */
    @Test
    public void testClean3() {

        State state1 = new StateFactory().create();
        State state2 = new StateFactory().create();
        State state3 = new StateFactory().create();
        State state4 = new StateFactory().create();
        BA ba = new BA(new ModelTransitionFactory());
        ba.addInitialState(state1);
        ba.addAcceptState(state2);
        ba.addState(state3);
        ba.addState(state4);

        ba.addTransition(state1, state2, new ModelTransitionFactory().create());
        ba.addTransition(state1, state3, new ModelTransitionFactory().create());
        ba.addTransition(state2, state4, new ModelTransitionFactory().create());
        ba.addTransition(state4, state2, new ModelTransitionFactory().create());
        ba.addTransition(state3, state3, new ModelTransitionFactory().create());

        AutomatonCleaner cleaner = new AutomatonCleaner(ba);
        Set<State> statesToBeRemoved = cleaner.clean();

        assertTrue(statesToBeRemoved.contains(state3));
        assertTrue(statesToBeRemoved.size() == 1);

    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.cleaner.AutomatonCleaner#clean()}.
     */
    @Test
    public void testClean4() {

        State state1 = new StateFactory().create();
        State state2 = new StateFactory().create();
        State state3 = new StateFactory().create();
        State state4 = new StateFactory().create();
        State state5 = new StateFactory().create();
        BA ba = new BA(new ModelTransitionFactory());
        ba.addInitialState(state1);
        ba.addAcceptState(state2);
        ba.addState(state3);
        ba.addState(state4);
        ba.addState(state5);

        ba.addTransition(state1, state2, new ModelTransitionFactory().create());
        ba.addTransition(state1, state3, new ModelTransitionFactory().create());
        ba.addTransition(state2, state4, new ModelTransitionFactory().create());
        ba.addTransition(state4, state2, new ModelTransitionFactory().create());
        ba.addTransition(state3, state5, new ModelTransitionFactory().create());
        ba.addTransition(state5, state3, new ModelTransitionFactory().create());

        AutomatonCleaner cleaner = new AutomatonCleaner(ba);
        Set<State> statesToBeRemoved = cleaner.clean();

        assertTrue(statesToBeRemoved.contains(state3));
        assertTrue(statesToBeRemoved.contains(state5));
        assertTrue(statesToBeRemoved.size() == 2);

    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.cleaner.AutomatonCleaner#clean()}.
     */
    @Test
    public void testClean5() {

        State state1 = new StateFactory().create();
        State state2 = new StateFactory().create();
        State state3 = new StateFactory().create();
        State state4 = new StateFactory().create();
        State state5 = new StateFactory().create();
        BA ba = new BA(new ModelTransitionFactory());
        ba.addInitialState(state1);
        ba.addAcceptState(state2);
        ba.addState(state3);
        ba.addState(state4);
        ba.addState(state5);

        ba.addTransition(state1, state2, new ModelTransitionFactory().create());
        ba.addTransition(state1, state3, new ModelTransitionFactory().create());
        ba.addTransition(state3, state5, new ModelTransitionFactory().create());
        ba.addTransition(state5, state3, new ModelTransitionFactory().create());

        AutomatonCleaner cleaner = new AutomatonCleaner(ba);
        Set<State> statesToBeRemoved = cleaner.clean();

        assertTrue(statesToBeRemoved.contains(state3));
        assertTrue(statesToBeRemoved.contains(state5));
        assertTrue(statesToBeRemoved.contains(state4));
        assertTrue(statesToBeRemoved.contains(state2));
        assertTrue(statesToBeRemoved.contains(state1));
        assertTrue(statesToBeRemoved.size() == 5);

    }
}
