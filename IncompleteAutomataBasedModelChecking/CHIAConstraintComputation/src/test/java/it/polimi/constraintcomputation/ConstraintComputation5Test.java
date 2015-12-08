package it.polimi.constraintcomputation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.io.in.ClaimReader;
import it.polimi.automata.io.in.ModelReader;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.transitions.LabeledPluggingTransition;
import it.polimi.constraints.transitions.PluggingTransition;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class ConstraintComputation5Test {

	private static final String path = "it.polimi.constraintcomputation/";

	private IBA model;
	private BA claim;


	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.model = new ModelReader(new File(getClass().getClassLoader()
				.getResource(path + "test5/model.xml").getFile())).perform();

		this.claim = new ClaimReader(new File(getClass().getClassLoader()
				.getResource(path + "test5/claim.xml").getFile())).perform();

	}

	@Test
	public void test() throws ParserConfigurationException, Exception {

		Checker checker = new Checker(model, claim,
				AcceptingPolicy.getAcceptingPolicy(AcceptingType.BA, model,
						claim));
		SatisfactionValue returnValue = checker.perform();
		assertEquals(SatisfactionValue.POSSIBLYSATISFIED, returnValue);

		ConstraintGenerator cg = new ConstraintGenerator(checker);
		Constraint constraint = cg.perform();

		
		System.out.println(constraint);
		SubProperty subProperty=constraint.getSubProperty(new StateFactory().create("t0", 1));
		
		State subPropertyInitialState=subProperty.getAutomaton().getInitialStates().iterator().next();
		for(PluggingTransition pluggingTransition: subProperty.getIncomingTransitions()){
			assertFalse(pluggingTransition.getSource().equals(subPropertyInitialState));
		}
		
		
	}

	public Set<LabeledPluggingTransition> getIncomingTransitions(
			Constraint constraint) {

		Set<LabeledPluggingTransition> incomingTransitionOfTheDestinationState = new HashSet<LabeledPluggingTransition>();
		for (SubProperty subProperty : constraint.getSubProperties()) {
			for (LabeledPluggingTransition incomingTransition : subProperty
					.getIncomingTransitions()) {
				incomingTransitionOfTheDestinationState.add(incomingTransition);
			}
		}
		return incomingTransitionOfTheDestinationState;
	}

	public Set<LabeledPluggingTransition> getOutgoingTransitions(
			Constraint constraint) {

		Set<LabeledPluggingTransition> outgoingTransitionOfTheDestinationState = new HashSet<LabeledPluggingTransition>();
		for (SubProperty subProperty : constraint.getSubProperties()) {
			for (LabeledPluggingTransition incomingTransition : subProperty
					.getOutgoingTransitions()) {
				outgoingTransitionOfTheDestinationState.add(incomingTransition);
			}
		}
		return outgoingTransitionOfTheDestinationState;
	}

}
