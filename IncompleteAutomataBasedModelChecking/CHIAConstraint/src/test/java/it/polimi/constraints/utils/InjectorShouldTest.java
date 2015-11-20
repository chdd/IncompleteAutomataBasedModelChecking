/**
 * 
 */
package it.polimi.constraints.utils;

import static org.junit.Assert.assertTrue;
import it.polimi.automata.IBA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ModelTransitionFactory;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.constraints.components.Replacement;
import it.polimi.constraints.transitions.PluggingTransition;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import rwth.i2.ltl2ba4j.model.IGraphProposition;
import rwth.i2.ltl2ba4j.model.impl.GraphProposition;

/**
 * @author Claudio1
 *
 */
public class InjectorShouldTest {

	private IBA result;
	private IBA model;
	private Replacement replacement;
	private State q1;
	private State send1;
	private State send2;
	private State q2;
	private State q3;
	private State state14;

	private StateFactory stateFactory;

	private TransitionFactory<State, Transition> transitionFactory;

	@Before
	public void setUp() throws Exception {
		model = this.createSendModel();
		replacement = this.createSend1ReplacementModel();

	}

	@Test
	public void notRemoveStatesFromTheIBA() throws Exception {

		Injector injector = new Injector(model, replacement);
		result = injector.perform();
		Set<State> states = new HashSet<State>(model.getStates());
		states.remove(replacement.getModelState());
		assertTrue(result.getStates().containsAll(states));
	}

	@Test
	public void notRemoveBlackBoxStatesFromTheIBA() throws Exception {

		Injector injector = new Injector(model, replacement);
		result = injector.perform();
		Set<State> blackBoxStates = new HashSet<State>(
				model.getBlackBoxStates());
		blackBoxStates.remove(replacement.getModelState());
		assertTrue(result.getBlackBoxStates().containsAll(blackBoxStates));
	}

	@Test
	public void notRemoveAcceptingStatesFromTheIBA() throws Exception {

		Injector injector = new Injector(model, replacement);
		result = injector.perform();
		Set<State> acceptingStates = new HashSet<State>(model.getAcceptStates());
		acceptingStates.remove(replacement.getModelState());
		assertTrue(result.getAcceptStates().containsAll(acceptingStates));
	}

	@Test
	public void notRemoveInitialStatesFromTheIBA() throws Exception {

		Injector injector = new Injector(model, replacement);
		result = injector.perform();
		Set<State> initialStates = new HashSet<State>(model.getInitialStates());
		initialStates.remove(replacement.getModelState());
		assertTrue(result.getInitialStates().containsAll(initialStates));
	}

	@Test
	public void insertTheReplacementStatesIntoTheIBA() throws Exception {

		Injector injector = new Injector(model, replacement);
		result = injector.perform();
		assertTrue(result.getStates().containsAll(
				replacement.getAutomaton().getStates()));
	}

	@Test
	public void insertTheBlackBoxReplacementStatesIntoTheIBA() throws Exception {

		Injector injector = new Injector(model, replacement);
		result = injector.perform();
		assertTrue(result.getBlackBoxStates().containsAll(
				replacement.getAutomaton().getBlackBoxStates()));
	}

	@Test
	public void notRemoveTheInitialStatesOfTheReplacement() throws Exception {

		Injector injector = new Injector(model, replacement);
		result = injector.perform();
		assertTrue(result.getInitialStates().containsAll(
				replacement.getAutomaton().getInitialStates()));
	}

	@Test
	public void notAddOtherAcceTheInitialReplacementStatesIntoTheIBA()
			throws Exception {

		replacement.getAutomaton().addInitialState(state14);
		Injector injector = new Injector(model, replacement);
		result = injector.perform();
		assertTrue(result.getInitialStates().containsAll(
				replacement.getAutomaton().getInitialStates()));
		assertTrue(result.getInitialStates().contains(state14));
	}

	@Test(expected = IllegalArgumentException.class)
	public void throwAnExceptionIfThePredecessorOfTheIncomingTransitionWasNotAPredecessorOfTheBlackBoxState()
			throws Exception {

		State state = new StateFactory().create();
		replacement.getAutomaton().addState(state);
		replacement.addIncomingTransition(new PluggingTransition(q3, state,
				new ModelTransitionFactory().create(), true));
		Injector injector = new Injector(model, replacement);
		result = injector.perform();

	}

	@Test(expected = IllegalArgumentException.class)
	public void throwAnExceptionIfThePredecessorOfTheOutgoingTransitionWasNotAPredecessorOfTheBlackBoxState()
			throws Exception {

		State state = new StateFactory().create();
		replacement.getAutomaton().addState(state);
		replacement.addOutgoingTransition(new PluggingTransition(state, q1,
				new ModelTransitionFactory().create(), true));
		Injector injector = new Injector(model, replacement);
		result = injector.perform();

	}

	@Test
	public void insertTheAcceptingReplacementStatesIntoTheIBA()
			throws Exception {

		Injector injector = new Injector(model, replacement);
		result = injector.perform();
		assertTrue(result.getAcceptStates().containsAll(
				replacement.getAutomaton().getAcceptStates()));
	}

	@Test
	public void insertTheReplacementTransitionsIntoTheIBA() throws Exception {

		Injector injector = new Injector(model, replacement);
		result = injector.perform();
		assertTrue(result.getTransitions().containsAll(
				replacement.getAutomaton().getTransitions()));
	}

	@Test
	public void injectTheIncomingTransitionsOfTheReplacement() throws Exception {

		Injector injector = new Injector(model, replacement);
		result = injector.perform();
		for (PluggingTransition plugTransition : replacement
				.getIncomingTransitions()) {
			assertTrue(result.getTransitions().contains(
					plugTransition.getTransition()));
			assertTrue(result.getTransitionSource(
					plugTransition.getTransition()).equals(
					plugTransition.getSource()));
			assertTrue(result.getTransitionDestination(
					plugTransition.getTransition()).equals(
					plugTransition.getDestination()));
		}
	}

	@Test
	public void injectTheOutgoingTransitionsOfTheReplacement() throws Exception {

		Injector injector = new Injector(model, replacement);
		result = injector.perform();
		for (PluggingTransition plugTransition : replacement
				.getOutgoingTransitions()) {
			assertTrue(result.getTransitions().contains(
					plugTransition.getTransition()));
			assertTrue(result.getTransitionSource(
					plugTransition.getTransition()).equals(
					plugTransition.getSource()));
			assertTrue(result.getTransitionDestination(
					plugTransition.getTransition()).equals(
					plugTransition.getDestination()));
		}
	}

	private IBA createSendModel() {

		transitionFactory = new ModelTransitionFactory();
		model = new IBA(transitionFactory);
		IGraphProposition startProposition = new GraphProposition("start",
				false);
		IGraphProposition failProposition = new GraphProposition("fail", false);
		IGraphProposition okProposition = new GraphProposition("ok", false);
		IGraphProposition abortProposition = new GraphProposition("abort",
				false);
		IGraphProposition successProposition = new GraphProposition("success",
				false);

		model.addProposition(startProposition);
		model.addProposition(failProposition);
		model.addProposition(okProposition);
		model.addProposition(abortProposition);
		model.addProposition(successProposition);

		stateFactory = new StateFactory();
		q1 = stateFactory.create("q1", 1);
		send1 = stateFactory.create("send_1", 2);
		send2 = stateFactory.create("send_2", 3);
		q2 = stateFactory.create("q2", 4);
		q3 = stateFactory.create("q3", 5);

		model.addInitialState(q1);
		model.addBlackBoxState(send1);
		model.addBlackBoxState(send2);
		model.addAcceptState(q2);
		model.addAcceptState(q3);

		Set<IGraphProposition> propositionsTransition1 = new HashSet<IGraphProposition>();
		propositionsTransition1.add(startProposition);
		Transition transition1 = transitionFactory.create(1,
				propositionsTransition1);
		model.addTransition(q1, send1, transition1);

		Set<IGraphProposition> propositionsTransition2 = new HashSet<IGraphProposition>();
		propositionsTransition2.add(failProposition);
		Transition transition2 = transitionFactory.create(2,
				propositionsTransition2);
		model.addTransition(send1, send2, transition2);

		Set<IGraphProposition> propositionsTransition3 = new HashSet<IGraphProposition>();
		propositionsTransition3.add(okProposition);
		Transition transition3 = transitionFactory.create(3,
				propositionsTransition3);
		model.addTransition(send1, q3, transition3);

		Set<IGraphProposition> propositionsTransition4 = new HashSet<IGraphProposition>();
		propositionsTransition4.add(okProposition);
		Transition transition4 = transitionFactory.create(4,
				propositionsTransition4);
		model.addTransition(send2, q3, transition4);

		Set<IGraphProposition> propositionsTransition5 = new HashSet<IGraphProposition>();
		propositionsTransition5.add(failProposition);
		Transition transition5 = transitionFactory.create(5,
				propositionsTransition5);
		model.addTransition(send2, q2, transition5);

		Set<IGraphProposition> propositionsTransition6 = new HashSet<IGraphProposition>();
		propositionsTransition6.add(successProposition);
		Transition transition6 = transitionFactory.create(6,
				propositionsTransition6);
		model.addTransition(q3, q3, transition6);

		Set<IGraphProposition> propositionsTransition7 = new HashSet<IGraphProposition>();
		propositionsTransition7.add(abortProposition);
		Transition transition7 = transitionFactory.create(7,
				propositionsTransition7);
		model.addTransition(q2, q2, transition7);
		return model;
	}

	private Replacement createSend1ReplacementModel() {
		State send1 = stateFactory.create("send_1", 2);

		IBA automaton = new IBA(transitionFactory);
		IGraphProposition bootingProposition = new GraphProposition("booting",
				false);
		IGraphProposition readyProposition = new GraphProposition("ready",
				false);
		IGraphProposition waitProposition = new GraphProposition("wait", false);
		IGraphProposition sendProposition = new GraphProposition("send", false);
		IGraphProposition timeoutProposition = new GraphProposition("timeout",
				false);
		IGraphProposition ackProposition = new GraphProposition("ack", false);

		automaton.addProposition(bootingProposition);
		automaton.addProposition(readyProposition);
		automaton.addProposition(waitProposition);
		automaton.addProposition(sendProposition);
		automaton.addProposition(timeoutProposition);
		automaton.addProposition(ackProposition);

		state14 = stateFactory.create("q14", 14);
		State q15 = stateFactory.create("q15", 15);
		State state16 = stateFactory.create("q16", 16);
		State state17 = stateFactory.create("q17", 17);
		State q18 = stateFactory.create("q18", 18);
		State q19 = stateFactory.create("q19", 19);

		automaton.addState(state14);
		automaton.addAcceptState(q15);
		automaton.addState(state16);
		automaton.addBlackBoxState(state17);
		automaton.addState(q18);
		automaton.addState(q19);

		Set<IGraphProposition> propositionsTransition9 = new HashSet<IGraphProposition>();
		propositionsTransition9.add(bootingProposition);
		automaton.addTransition(state14, state14,
				this.transitionFactory.create(9, propositionsTransition9));

		Set<IGraphProposition> propositionsTransition10 = new HashSet<IGraphProposition>();
		propositionsTransition10.add(readyProposition);
		automaton.addTransition(state14, q15,
				this.transitionFactory.create(10, propositionsTransition10));

		Set<IGraphProposition> propositionsTransition11 = new HashSet<IGraphProposition>();
		propositionsTransition11.add(waitProposition);
		automaton.addTransition(q15, q15,
				this.transitionFactory.create(11, propositionsTransition11));

		Set<IGraphProposition> propositionsTransition12 = new HashSet<IGraphProposition>();
		propositionsTransition12.add(sendProposition);
		automaton.addTransition(q15, state16,
				this.transitionFactory.create(12, propositionsTransition12));

		Set<IGraphProposition> propositionsTransition13 = new HashSet<IGraphProposition>();
		propositionsTransition13.add(waitProposition);
		automaton.addTransition(state16, state17,
				this.transitionFactory.create(13, propositionsTransition13));

		Set<IGraphProposition> propositionsTransition14 = new HashSet<IGraphProposition>();
		propositionsTransition14.add(timeoutProposition);
		automaton.addTransition(state17, q18,
				this.transitionFactory.create(14, propositionsTransition14));

		Set<IGraphProposition> propositionsTransition15 = new HashSet<IGraphProposition>();
		propositionsTransition15.add(ackProposition);
		automaton.addTransition(state17, q19,
				this.transitionFactory.create(15, propositionsTransition15));

		Set<PluggingTransition> incomingPluggingTransition = new HashSet<PluggingTransition>();

		Set<IGraphProposition> propositionsTransition16 = new HashSet<IGraphProposition>();
		propositionsTransition16.add(new GraphProposition("start", false));
		incomingPluggingTransition.add(new PluggingTransition(q1, q15,
				this.transitionFactory.create(16, propositionsTransition16),
				true));

		Set<PluggingTransition> outgoingPluggingTransition = new HashSet<PluggingTransition>();

		Set<IGraphProposition> propositionsTransition17 = new HashSet<IGraphProposition>();
		propositionsTransition17.add(new GraphProposition("fail", false));
		outgoingPluggingTransition.add(new PluggingTransition(q18, send2,
				this.transitionFactory.create(17, propositionsTransition17),
				false));

		Set<IGraphProposition> propositionsTransition18 = new HashSet<IGraphProposition>();
		propositionsTransition18.add(new GraphProposition("ok", false));
		outgoingPluggingTransition.add(new PluggingTransition(q19, q3,
				this.transitionFactory.create(18, propositionsTransition18),
				false));

		Replacement replacement = new Replacement(send1, automaton,
				incomingPluggingTransition, outgoingPluggingTransition);
		return replacement;
	}
}
