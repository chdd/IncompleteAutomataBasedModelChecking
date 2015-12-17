package it.polimi.chia.scalability;

import it.polimi.action.CHIAAction;
import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.state.StateFactory;
import it.polimi.automata.transition.ClaimTransitionFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import rwth.i2.ltl2ba4j.model.IGraphProposition;

import com.google.common.base.Preconditions;

/**
 * is used to generate a random BA. a random Buchi automaton is generated over
 * an alphabet made by two propositions a, b. For each proposition of the
 * alphabet a random directed graph with a single initial state and $k$
 * transition is generated. The ``hardness" of the problem is changed by
 * controlling the density of the transitions, i.e., the ratio between the
 * number of transitions per input letter and the number of the states
 * ($r=|\Delta|/|Q|$), and the density of \emph{accepting} states, i.e., the
 * ratio between the accepting states and the total number of the states
 * ($f=|F|/|Q|$). The transition density represents the expected number of
 * transition that exits a state. The generation procedure also imposes that the
 * initial state has an outgoing transition for each proposition of the
 * alphabet, which allows to not consider trivial cases. The number of accepting
 * states, which are selected randomly between the set of the states of the
 * BA/IBA, is a linear function of the number of the states of the system.
 * 
 * @author Claudio Menghi
 *
 */
public class BARandomGenerator extends CHIAAction<BA>{

	/**
	 * contains the number of the states to be added to the BA
	 */
	private final int nStates;

	/**
	 * is the expected density of transitions for the BA
	 */
	private final double transitionDensity;

	/**
	 * is the expected density of accepting states for the ba
	 */
	private final double acceptanceDensity;

	/**
	 * is the random element used to choose the states to be connected in the
	 * automaton
	 */
	private final Random randSingleton;

	/**
	 * is the factory which is used to create the states of the automaton
	 */
	private final StateFactory stateFactory;

	/**
	 * is the set of propositions to be used in labeling the transitions of the
	 * automaton
	 */
	private final Set<IGraphProposition> propositions;

	/**
	 * contains the BA to be returned
	 */
	private BA ba;

	/**
	 * is the transition factory to be used in the creation of the transitions
	 * of the automaton
	 */
	private ClaimTransitionFactory transitionFactory;
	
	private static final String ACTION_NAME="BA random generation";

	/**
	 * Creates a random generator for a Buchi automaton
	 * 
	 * @param propositions
	 *            is the set of propositions to be added to the BA
	 * @param stateFactory
	 *            is the factory which is used to create the states of the BA
	 * @param transitionDensity
	 *            is the expected transition density for the BA
	 * @param acceptanceDensity
	 *            is the expected density of accepting states
	 * @param nStates
	 *            is the expected number of states of the ba
	 * @param transitionsStateChooser
	 *            is the random element used to choose the states to be
	 *            connected by the transitions
	 * @throws NullPointerException
	 *             if the set of propositions is null, if the state factory is
	 *             null
	 * @throws IllegalArgumentException
	 *             if the transition density is not grater then or equal to zero
	 *             OR <br>
	 *             if the accepting density is not grater then or equal to zero
	 *             OR <br>
	 *             if the number of states is not grater then or equal to zero
	 */
	public BARandomGenerator(Set<IGraphProposition> propositions,
			StateFactory stateFactory, double transitionDensity,
			double acceptanceDensity, int nStates,
			Random transitionsStateChooser) {
		super(ACTION_NAME);
		Preconditions.checkNotNull(propositions,
				"The set of propositions cannot be null");
		Preconditions.checkNotNull(stateFactory,
				"The state factory cannot be null");

		Preconditions.checkArgument(transitionDensity >= 0,
				"The transition density must be greater or equal to zero");
		Preconditions.checkArgument(acceptanceDensity >= 0,
				"The acceptance density must be greater or equal to zero");
		Preconditions.checkArgument(nStates >= 0,
				"The number of states must be greater or equal to zero");
		this.nStates = nStates;
		this.transitionDensity = transitionDensity;
		this.acceptanceDensity = acceptanceDensity;
		this.randSingleton = transitionsStateChooser;
		this.stateFactory = stateFactory;
		this.propositions = propositions;
		this.transitionFactory = new ClaimTransitionFactory();
	}

	/**
	 * Generates a random Buchi Automaton in relation with the parameters passed
	 * to the {@link BARandomGenerator} constructor
	 * 
	 * @return the randomly generated BA
	 */
	public BA perform() {

		this.ba = new BA(transitionFactory);

		// adds the propositions to the ba to be returned
		this.addPropositions();
		// adds the randomly generated states to the BA
		this.createStates();
		// adds the accepting states to the BA
		this.addAcceptingStates();
		// creates the transitions between the states of the BA
		this.createTransitions();
		// creates a new state which is the initial state for the BA
		State initState = this.addInitialState();

		// creates the transitions which exit the initial state
		this.createTransitionsInitialState(initState);
		return ba;
	}

	/**
	 * adds the propositions to the BA to be returned
	 */
	private void addPropositions() {
		for (IGraphProposition proposition : propositions) {
			ba.addProposition(proposition);
		}
	}

	/**
	 * @param initialState
	 *            creates the transitions which exit the initial state
	 * @throws NullPointerException
	 *             if the initial state is null
	 * @throws IllegalArgumentException
	 *             if the initials State is not contained in the BA
	 */
	private void createTransitionsInitialState(State initialState) {

		Preconditions.checkNotNull(initialState,
				"The initial state cannot be null");
		Preconditions
				.checkArgument(
						this.ba.getInitialStates().contains(initialState),
						"The initial state must be contained into the set of initial states of the automaton");

		List<State> baStates = new ArrayList<State>(this.ba.getStates());
		baStates.removeAll(ba.getInitialStates());

		int j = 0;
		for (IGraphProposition proposition : propositions) {
			Set<IGraphProposition> transitionPropositions = new HashSet<IGraphProposition>();
			transitionPropositions.add(proposition);
			ba.addTransition(initialState, baStates.get(j),
					transitionFactory.create(transitionPropositions));
			j++;
		}
	}

	/**
	 * creates the transitions between the states of the BA
	 */
	private void createTransitions() {

		int numTransition = (int) Math.round((nStates - 1) * transitionDensity);
		List<State> states = new ArrayList<State>(ba.getStates());
		for (IGraphProposition proposition : propositions) {
			Set<IGraphProposition> transitionPropositions = new HashSet<IGraphProposition>();
			transitionPropositions.add(proposition);
			for (int i = 0; i < numTransition; ++i) {
				ba.addTransition(
						states.get(randSingleton.nextInt(nStates - 1)),
						states.get(randSingleton.nextInt(nStates - 1)),
						transitionFactory.create(transitionPropositions));
			}

		}
	}

	/**
	 * adds the randomly generated states to the BA
	 */
	private void createStates() {
		for (int i = 0; i < (nStates - 1); i++) {
			ba.addState(this.stateFactory.create());
		}
	}

	/**
	 * adds the accepting states to the BA
	 */
	private void addAcceptingStates() {
		int i = 0;
		int numAcceptingStates = (int) Math.round(nStates * acceptanceDensity);

		ArrayList<State> baStates = new ArrayList<State>(ba.getStates());
		Collections.shuffle(baStates);
		for (State s : baStates) {
			if (i < numAcceptingStates) {
				ba.addAcceptState(s);
				i++;
			}
		}
	}

	/**
	 * creates a new state which is the initial state for the BA
	 * 
	 * @return the initial state of the BA
	 */
	private State addInitialState() {
		State initState = this.stateFactory.create();
		this.ba.addInitialState(initState);
		return initState;
	}
}
