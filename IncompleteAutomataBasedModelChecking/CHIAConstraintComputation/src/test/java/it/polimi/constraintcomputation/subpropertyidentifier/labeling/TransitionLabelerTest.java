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
import it.polimi.constraints.transitions.LabeledPluggingTransition;

import org.junit.Test;

/**
 * @author Claudio Menghi
 *
 */
public class TransitionLabelerTest {

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.labeling.TransitionLabeler#TransitionLabeler(null)}
     * .
     */
    @Test(expected = NullPointerException.class)
    public void testTransitionLabeler1() {
        new TransitionLabeler(null);
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.labeling.TransitionLabeler#TransitionLabeler(it.polimi.constraintcomputation.subpropertyidentifier.SubPropertyIdentifier)}
     * .
     */
    @Test
    public void testTransitionLabeler() {
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
        assertNotNull(new TransitionLabeler(identifier));
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.labeling.TransitionLabeler#perform()}
     * .
     */
    @Test
    public void testPerform1() {
        IBA model = new IBA(new ModelTransitionFactory());
        State modelstate = new StateFactory().create("regular1", 1);
        State blackState2 = new StateFactory().create("black2", 2);
        State blackState3 = new StateFactory().create("black3", 3);
        model.addBlackBoxState(blackState2);
        model.addBlackBoxState(blackState3);
        model.addAcceptState(blackState3);
        model.addInitialState(modelstate);
        model.addTransition(modelstate, blackState2,
                new ModelTransitionFactory().create());
        model.addTransition(blackState2, blackState3,
                new ModelTransitionFactory().create());
        model.addTransition(blackState3, blackState3,
                new ModelTransitionFactory().create());
        model.addTransition(modelstate, blackState3,
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
                blackState3);
        SubProperty subProperty = identifier.perform();

        TransitionLabeler transitionLabeler = new TransitionLabeler(identifier);
        transitionLabeler.perform();

        for (LabeledPluggingTransition trans : subProperty
                .getIncomingTransitions()) {
            if (trans.getSource().equals(modelstate)) {
                assertEquals(Label.G, trans.getLabel());
            }
            if (trans.getSource().equals(blackState2)) {
                assertEquals(Label.Y, trans.getLabel());
            }
        }

    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.labeling.TransitionLabeler#perform()}
     * .
     */
    @Test
    public void testPerform2() {
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

        TransitionLabeler labeler = new TransitionLabeler(identifier);
        labeler.perform();

        assertTrue(subProperty.getOutgoingTransitions().size() == 1);
        assertEquals(Label.Y, subProperty.getOutgoingTransitions().iterator()
                .next().getLabel());
    }

    /**
     * Test method for
     * {@link it.polimi.constraintcomputation.subpropertyidentifier.labeling.TransitionLabeler#perform()}
     * .
     */
    @Test
    public void testPerform3() {
        IBA model = new IBA(new ModelTransitionFactory());
        State blackstate2 = new StateFactory().create("black", 1);
        State blackState = new StateFactory().create("black", 2);
        State blackstate3 = new StateFactory().create("black", 3);
        State regular = new StateFactory().create("regular", 4);

        model.addBlackBoxState(blackState);
        model.addInitialState(blackState);

        model.addBlackBoxState(blackstate2);

        model.addState(regular);
        model.addBlackBoxState(blackstate3);
        model.addAcceptState(blackstate3);

        model.addTransition(blackState, blackstate2,
                new ModelTransitionFactory().create());
        model.addTransition(blackstate2, regular,
                new ModelTransitionFactory().create());
        model.addTransition(regular, blackstate2,
                new ModelTransitionFactory().create());
        model.addTransition(blackstate2, blackstate3,
                new ModelTransitionFactory().create());
        model.addTransition(blackstate3, blackstate3,
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
                blackstate2);
        SubProperty subProperty = identifier.perform();

        TransitionLabeler labeler = new TransitionLabeler(identifier);
        labeler.perform();


        assertTrue(subProperty.getOutgoingTransitions().size() == 2);
        assertTrue(subProperty.getIncomingTransitions().size() == 2);
        for(LabeledPluggingTransition incoming: subProperty.getIncomingTransitions()){
            if(incoming.getSource().equals(blackState)){
                assertEquals(Label.Y, incoming.getLabel());
            }
            if(incoming.getSource().equals(regular)){
                assertEquals(Label.B, incoming.getLabel());
            }
        }
        for(LabeledPluggingTransition outgoing: subProperty.getOutgoingTransitions()){
            if(outgoing.getDestination().equals(blackstate3)){
                assertEquals(Label.Y, outgoing.getLabel());
            }
            if(outgoing.getDestination().equals(regular)){
                assertEquals(Label.B, outgoing.getLabel());
            }
        }
    }

}
