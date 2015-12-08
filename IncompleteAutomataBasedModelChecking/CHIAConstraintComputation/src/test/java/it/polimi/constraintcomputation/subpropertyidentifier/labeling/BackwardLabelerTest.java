/**
 * 
 */
package it.polimi.constraintcomputation.subpropertyidentifier.labeling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.checker.Checker;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.transitions.Label;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * @author Claudio Menghi
 *
 */
public class BackwardLabelerTest {

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.labeling.BackwardLabeler#BackwardLabeler(null, it.polimi.constraints.transitions.Label, it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testBackwardLabeler1() {
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        BA claim = new BA(new ClaimTransitionFactory());
        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();

        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        assertNotNull(new BackwardLabeler(null, Label.Y, identifier));
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.labeling.BackwardLabeler#BackwardLabeler(java.util.Set, null, it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testBackwardLabeler2() {
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        BA claim = new BA(new ClaimTransitionFactory());
        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();

        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        assertNotNull(new BackwardLabeler(new HashSet<State>(), null,
                identifier));
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.labeling.BackwardLabeler#BackwardLabeler(java.util.Set, it.polimi.constraints.transitions.Label, null)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testBackwardLabeler3() {
        assertNotNull(new BackwardLabeler(new HashSet<State>(), Label.Y, null));
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.labeling.BackwardLabeler#BackwardLabeler(java.util.Set, it.polimi.constraints.transitions.Label, it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier)}
     * .
     */
    @Test
    public void testBackwardLabeler4() {
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        BA claim = new BA(new ClaimTransitionFactory());
        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();

        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        assertNotNull(new BackwardLabeler(new HashSet<State>(), Label.Y,
                identifier));
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.labeling.BackwardLabeler#perform()}
     * .
     */
    @Test
    public void testPerform1() {
        IBA model = new IBA(new ModelTransitionFactory());
        State modelstate = new StateFactory().create("regular", 1);
        State blackState = new StateFactory().create("black", 2);
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(modelstate);
        model.addTransition(blackState, modelstate,
                new ModelTransitionFactory().create());
        model.addTransition(modelstate, modelstate,
                new ModelTransitionFactory().create());

        State claimstate = new StateFactory().create("claim", 1);
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addInitialState(claimstate);
        claim.addAcceptState(claimstate);
        claim.addTransition(claimstate, claimstate,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();

        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        SubProperty subProperty = identifier.perform();
        BackwardLabeler labeler = new BackwardLabeler(identifier.getChecker()
                .getUpperIntersectionBA().getPurelyRegularStates(), Label.R,
                identifier);
        labeler.perform();

        assertTrue(subProperty.getOutgoingTransitions().size() == 1);
        assertEquals(Label.R, subProperty.getOutgoingTransitions().iterator()
                .next().getLabel());
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.labeling.BackwardLabeler#perform()}
     * .
     */
    @Test
    public void testPerform2() {
        IBA model = new IBA(new ModelTransitionFactory());
        State blackstate2 = new StateFactory().create("black", 1);
        State blackState = new StateFactory().create("black", 2);
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackstate2);
        model.addBlackBoxState(blackstate2);
        model.addTransition(blackState, blackstate2,
                new ModelTransitionFactory().create());
        model.addTransition(blackstate2, blackstate2,
                new ModelTransitionFactory().create());

        State claimstate = new StateFactory().create("claim", 1);
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addInitialState(claimstate);
        claim.addAcceptState(claimstate);
        claim.addTransition(claimstate, claimstate,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();

        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        SubProperty subProperty = identifier.perform();
        Set<State> states = new HashSet<State>(identifier.getChecker()
                .getUpperIntersectionBA().getStates());
        states.removeAll(subProperty.getAutomaton().getStates());
        BackwardLabeler labeler = new BackwardLabeler(states, Label.Y,
                identifier);
        labeler.perform();

        assertTrue(subProperty.getOutgoingTransitions().size() == 1);
        assertEquals(Label.Y, subProperty.getOutgoingTransitions().iterator()
                .next().getLabel());
    }
    
    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.labeling.BackwardLabeler#perform()}
     * .
     */
    @Test
    public void testPerform3() {
        IBA model = new IBA(new ModelTransitionFactory());
        State blackstate2 = new StateFactory().create("black", 1);
        State blackState = new StateFactory().create("black", 2);
        State regularState1 = new StateFactory().create("regular", 3);
        State regularState2 = new StateFactory().create("regular", 4);

        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackstate2);
        model.addBlackBoxState(blackstate2);
        model.addState(regularState1);
        model.addState(regularState2);
        model.addAcceptState(regularState2);
        model.addTransition(blackState, regularState1,
                new ModelTransitionFactory().create());
        model.addTransition(regularState1, blackstate2,
                new ModelTransitionFactory().create());
        model.addTransition(regularState1, regularState2,
                new ModelTransitionFactory().create());
        model.addTransition(blackstate2, blackstate2,
                new ModelTransitionFactory().create());
        model.addTransition(regularState2, regularState2,
                new ModelTransitionFactory().create());

        State claimstate = new StateFactory().create("claim", 1);
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addInitialState(claimstate);
        claim.addAcceptState(claimstate);
        claim.addTransition(claimstate, claimstate,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();

        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        SubProperty subProperty = identifier.perform();
        
        BackwardLabeler labeler = new BackwardLabeler(identifier.getChecker()
                .getUpperIntersectionBA().getPurelyRegularStates(), Label.R,
                identifier);
        labeler.perform();
        
        Set<State> states = new HashSet<State>(identifier.getChecker()
                .getUpperIntersectionBA().getStates());
        states.removeAll(subProperty.getAutomaton().getStates());
        labeler = new BackwardLabeler(states, Label.Y,
                identifier);
        labeler.perform();


        
        assertTrue(subProperty.getOutgoingTransitions().size() == 1);
        assertEquals(Label.R, subProperty.getOutgoingTransitions().iterator()
                .next().getLabel());
    }    
    
    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.labeling.BackwardLabeler#perform()}
     * .
     */
    @Test
    public void testPerform4() {
        IBA model = new IBA(new ModelTransitionFactory());
        State blackstate2 = new StateFactory().create("black", 1);
        State blackState = new StateFactory().create("black", 2);
        State blackstate3 = new StateFactory().create("regular", 3);
        State regularState2 = new StateFactory().create("regular", 4);

        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackstate2);
        model.addBlackBoxState(blackstate2);
        model.addBlackBoxState(blackstate3);
        model.addState(regularState2);
        model.addAcceptState(regularState2);
        model.addTransition(blackState, blackstate3,
                new ModelTransitionFactory().create());
        model.addTransition(blackstate3, blackstate2,
                new ModelTransitionFactory().create());
        model.addTransition(blackstate3, regularState2,
                new ModelTransitionFactory().create());
        model.addTransition(blackstate2, blackstate2,
                new ModelTransitionFactory().create());
        model.addTransition(regularState2, regularState2,
                new ModelTransitionFactory().create());

        State claimstate = new StateFactory().create("claim", 1);
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addInitialState(claimstate);
        claim.addAcceptState(claimstate);
        claim.addTransition(claimstate, claimstate,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();

        SubPropertyIdentifier identifier = new SubPropertyIdentifier(checker,
                blackState);
        SubProperty subProperty = identifier.perform();
        
        BackwardLabeler labeler = new BackwardLabeler(identifier.getChecker()
                .getUpperIntersectionBA().getPurelyRegularStates(), Label.R,
                identifier);
        labeler.perform();
        
        Set<State> states = new HashSet<State>(identifier.getChecker()
                .getUpperIntersectionBA().getStates());
        states.removeAll(subProperty.getAutomaton().getStates());
        labeler = new BackwardLabeler(states, Label.Y,
                identifier);
        labeler.perform();


        
        assertTrue(subProperty.getOutgoingTransitions().size() == 1);
        assertEquals(Label.Y, subProperty.getOutgoingTransitions().iterator()
                .next().getLabel());
    }    
}
