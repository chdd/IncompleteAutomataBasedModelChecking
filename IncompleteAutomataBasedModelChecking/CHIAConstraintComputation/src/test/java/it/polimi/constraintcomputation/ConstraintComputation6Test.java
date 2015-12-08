/**
 * 
 */
package it.polimi.constraintcomputation;

import static org.junit.Assert.assertNotNull;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.checker.Checker;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;

import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * @author Claudio1
 *
 */
public class ConstraintComputation6Test {

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.ConstraintGenerator#ConstraintGenerator(null)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testConstraintGenerator1() {
        new ConstraintGenerator(null);
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.ConstraintGenerator#ConstraintGenerator(it.polimi.checker.Checker)}
     * .
     */
    @Test(expected = IllegalStateException.class)
    public void testConstraintGenerator2() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State regularState2 = new StateFactory().create();
        State regularState1 = new StateFactory().create();
        model.addState(regularState2);
        model.addInitialState(regularState2);
        model.addAcceptState(regularState2);
        model.addState(regularState1);
        model.addAcceptState(regularState1);
        model.addTransition(regularState2, regularState1,
                new ModelTransitionFactory().create());
        model.addTransition(regularState1, regularState1,
                new ModelTransitionFactory().create());
        model.addTransition(regularState1, regularState2,
                new ModelTransitionFactory().create());
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        State claimState = new StateFactory().create();
        claim.addInitialState(claimState);
        claim.addAcceptState(claimState);
        claim.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        new ConstraintGenerator(checker);

    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.ConstraintGenerator#ConstraintGenerator(it.polimi.checker.Checker)}
     * .
     */
    @Test
    public void testConstraintGenerator3() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackState);
        model.addState(regularState);
        model.addAcceptState(regularState);
        model.addTransition(blackState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, blackState,
                new ModelTransitionFactory().create());
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        State claimState = new StateFactory().create();
        claim.addInitialState(claimState);
        claim.addAcceptState(claimState);
        claim.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        assertNotNull(new ConstraintGenerator(checker));
    }
    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.ConstraintGenerator#perform()}.
     */
    @Test
    public void testConstraintGenerator4() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackState);
        model.addState(regularState);
        model.addAcceptState(regularState);
        model.addTransition(blackState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, blackState,
                new ModelTransitionFactory().create());
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        State claimState = new StateFactory().create();
        claim.addInitialState(claimState);
        claim.addAcceptState(claimState);
        claim.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        ConstraintGenerator cg=new ConstraintGenerator(checker);
        assertNotNull(cg.perform());
    }
    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.ConstraintGenerator#perform()}.
     */
    @Test
    public void testConstraintGenerator5() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        State regularState2 = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackState);
        model.addState(regularState2);
        model.addState(regularState);
        model.addAcceptState(regularState);
        model.addTransition(blackState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, blackState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, regularState2,
                new ModelTransitionFactory().create());
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        State claimState = new StateFactory().create();
        claim.addInitialState(claimState);
        claim.addAcceptState(claimState);
        claim.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        ConstraintGenerator cg=new ConstraintGenerator(checker);
        assertNotNull(cg.perform(blackState));
    }
    
    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.ConstraintGenerator#perform()}.
     */
    @Test
    public void testConstraintGenerator6() {
        IGraphProposition aproposition = new GraphProposition("a", false);
        IGraphProposition bproposition = new GraphProposition("b", false);
        IBA model = new IBA(new ModelTransitionFactory());
        State blackState = new StateFactory().create();
        State regularState = new StateFactory().create();
        State blackState2 = new StateFactory().create();
        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);
        model.addAcceptState(blackState);
        model.addBlackBoxState(blackState2);
        model.addInitialState(blackState2);
        model.addState(regularState);
        model.addAcceptState(regularState);
        model.addTransition(blackState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, regularState,
                new ModelTransitionFactory().create());
        model.addTransition(regularState, blackState,
                new ModelTransitionFactory().create());
        model.addTransition(blackState2, regularState, 
                new ModelTransitionFactory().create());
        BA claim = new BA(new ClaimTransitionFactory());
        claim.addProposition(aproposition);
        claim.addProposition(bproposition);

        State claimState = new StateFactory().create();
        claim.addInitialState(claimState);
        claim.addAcceptState(claimState);
        claim.addTransition(claimState, claimState,
                new ClaimTransitionFactory().create());

        Checker checker = new Checker(model, claim,
                AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
                        claim));
        checker.perform();
        ConstraintGenerator cg=new ConstraintGenerator(checker);
        assertNotNull(cg.perform(blackState));
    }
    

}
