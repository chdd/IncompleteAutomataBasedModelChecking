package it.polimi.replacementchecker.buchiaccepting;

import static org.junit.Assert.assertEquals;
import it.polimi.automata.BA;
import it.polimi.automata.IBA;
import it.polimi.automata.io.in.ClaimReader;
import it.polimi.automata.io.in.ModelReader;
import it.polimi.automata.state.IntersectionStateFactory;
import it.polimi.checker.Checker;
import it.polimi.checker.SatisfactionValue;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy;
import it.polimi.checker.intersection.acceptingpolicies.AcceptingPolicy.AcceptingType;
import it.polimi.constraintcomputation.ConstraintGenerator;
import it.polimi.constraints.Constraint;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.components.SubProperty;
import it.polimi.constraints.io.in.replacement.ReplacementReader;
import it.polimi.constraints.transitions.LabeledPluggingTransition;
import it.polimi.replacementchecker.ReplacementChecker;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ReplacementChecker15Test {

	private static final String path = "it.polimi.replacementchecker/buchiaccepting/test15/";

	private IBA model;
	private BA claim;
	private Replacement replacement;

	@Mock
	private IntersectionStateFactory intersectionStateFactory;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.model = new ModelReader(new File(getClass().getClassLoader()
				.getResource(path + "model.xml").getFile())).perform();

		this.claim = new ClaimReader(new File(getClass().getClassLoader()
				.getResource(path + "claim.xml").getFile())).perform();
		this.replacement=new ReplacementReader(new File(getClass().getClassLoader()
				.getResource(path + "replacement.xml").getFile())).perform();

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

		
		SubProperty subproperty=constraint.getSubProperty(this.replacement.getModelState());
		assertEquals(SatisfactionValue.SATISFIED, new ReplacementChecker( replacement, subproperty,
				AcceptingPolicy.getAcceptingPolicy(
						AcceptingType.BA, replacement.getAutomaton(),
						subproperty.getAutomaton())).perform());
		
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
