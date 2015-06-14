package it.polimi.constraints.utils;

import static org.junit.Assert.assertTrue;
import it.polimi.automata.IBA;
import it.polimi.automata.io.in.IBAReader;
import it.polimi.automata.state.StateFactory;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.io.in.replacement.ReplacementReader;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class TestInjectorShould {

	private static final String path = "it.polimi.constraints.utils/";
	private IBA model;
	private IBA refinedModel;
	private Replacement replacement;
	private IBA generatedRefinedModel;

	@Before
	public void setUp() throws Exception {

		model = new IBAReader(new File(getClass().getClassLoader()
				.getResource(path + "model.xml").getFile())).perform();
		refinedModel = new IBAReader(new File(getClass().getClassLoader()
				.getResource(path + "refinement.xml").getFile())).perform();
		replacement = new ReplacementReader(new File(getClass()
				.getClassLoader().getResource(path + "replacement.xml")
				.getFile())).perform();
		System.out.println(replacement);
		generatedRefinedModel = new Injector(model, replacement).perform();
	}

	@Test
	public void injectTheStatesIntoTheReplacement() {
		assertTrue(generatedRefinedModel.getStates().equals(
				refinedModel.getStates()));
	}

	@Test
	public void injectTheInitialStatesIntoTheReplacement() {
		assertTrue(generatedRefinedModel.getInitialStates().equals(
				refinedModel.getInitialStates()));

	}

	@Test
	public void injectTheAcceptingStatesIntoTheReplacement() {
		assertTrue(generatedRefinedModel.getAcceptStates().equals(
				refinedModel.getAcceptStates()));

	}

	@Test
	public void injectTheTransitionsIntoTheReplacement() {
		assertTrue(generatedRefinedModel.getTransitions().equals(
				refinedModel.getTransitions()));

	}

	@Test
	public void injectTheOutgoingTransitionsIntoTheReplacement() {
		assertTrue(
				"The number of outgoing transitions of the states 1126 must be two but is "
						+ generatedRefinedModel.getOutTransitions(
								new StateFactory().create("1126", 1126)).size(),
				generatedRefinedModel.getOutTransitions(
						new StateFactory().create("1126", 1126)).size() == 2);
	}
}
