package it.polimi.constraintcomputation;

import static org.junit.Assert.assertEquals;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.io.in.ClaimReader;
import it.polimi.automata.io.in.ModelReader;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.transitions.Label;
import it.polimi.constraints.transitions.LabeledPluggingTransition;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

public class ConstraintComputation2Test {

	private static final String path = "it.polimi.constraintcomputation/";

	private IBA model;
	private BA claim;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.model = new ModelReader(new File(getClass().getClassLoader()
				.getResource(path + "test2/model.xml").getFile())).perform();

		this.claim = new ClaimReader(new File(getClass().getClassLoader()
				.getResource(path + "test2/claim.xml").getFile())).perform();

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

		
		
		for (LabeledPluggingTransition incomingTransition : this
				.getIncomingTransitions(constraint)) {
			assertEquals(Label.Y, incomingTransition.getLabel());
		}
		for (LabeledPluggingTransition outgoingTransition : this
				.getOutgoingTransitions(constraint)) {
			assertEquals(Label.Y, outgoingTransition.getLabel());
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
