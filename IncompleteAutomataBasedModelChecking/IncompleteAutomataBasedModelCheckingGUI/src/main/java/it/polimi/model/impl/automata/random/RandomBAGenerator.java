package it.polimi.model.impl.automata.random;

import it.polimi.constraints.Proposition;
import it.polimi.model.impl.automata.BAImpl;
import it.polimi.model.impl.labeling.ConjunctiveClauseImpl;
import it.polimi.model.impl.labeling.DNFFormulaImpl;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.BA;
import it.polimi.model.interfaces.states.StateFactory;
import it.polimi.model.interfaces.transitions.TransitionFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import org.apache.commons.collections15.Factory;

/**
 * contains a {@link Random} generator of {@link BA}
 * 
 * @author claudiomenghi
 * 
 * @param <STATE>
 *            is the type of the {@link State} of the {@link BA}
 * @param <TRANSITION>
 *            is the type of the {@link Transition} of the {@link BA}
 */
public class RandomBAGenerator<STATE extends State, TRANSITION extends Transition> {

	/**
	 * will contain the {@link BA} generated
	 */
	private BA<STATE, TRANSITION> ba;

	/**
	 * contains the {@link Factory} which creates the {@link State} of the
	 * {@link BA}
	 */
	private StateFactory<STATE> stateFactory;

	/**
	 * contains the {@link Factory} that creates the {@link Transition} of the
	 * {@link BA}
	 */
	private TransitionFactory<TRANSITION> transitionFactory;

	/**
	 * creates a new {@link RandomBAGenerator} with the specified
	 * {@link TransitionFactory} and {@link StateFactory}
	 * 
	 * @param transitionFactory
	 *            is the {@link TransitionFactory} of the {@link BA}
	 * @param stateFactory
	 *            is the {@link StateFactory} of the {@link BA}
	 * @throws NullPointerException
	 *             if the {@link TransitionFactory} or the {@link StateFactory}
	 *             is null
	 */
	public RandomBAGenerator(TransitionFactory<TRANSITION> transitionFactory,
			StateFactory<STATE> stateFactory) {
		if (transitionFactory == null) {
			throw new NullPointerException(
					"The transition factory cannot be null");
		}
		if (stateFactory == null) {
			throw new NullPointerException("The state factory cannot be null");
		}
		this.stateFactory = stateFactory;
		this.transitionFactory = transitionFactory;
	}

	/**
	 * generates a new random graph (note that almost every graph is connected
	 * with the parameters n, 2ln(n)/n
	 * 
	 * @param n
	 *            : number of nodes
	 * @param p
	 *            : probability through which each transition is included in the
	 *            graph
	 * @return a new random graph
	 */
	public void getRandomAutomaton2(int n, double transitionProbability,
			int numInitial, int numAccepting, Set<Proposition> alphabet) {
		if (transitionProbability >= 1 || transitionProbability < 0) {
			throw new IllegalArgumentException(
					"The value of p must be included in the trange [0,1]");
		}

		this.ba = new BAImpl<STATE, TRANSITION>(transitionFactory, stateFactory);

		this.ba.addPropositions(alphabet);
		Random r = new Random();

		for (int i = 0; i < n; i++) {

			STATE s = stateFactory.create("s" + i);
			this.ba.addState(s);
		}

		for (int i = 0; i < numInitial; i++) {
			int transp = r.nextInt(this.ba.getStates().size());
			Iterator<STATE> it = this.ba.getStates().iterator();
			for (int j = 0; j < transp; j++) {
				it.next();
			}
			this.ba.addInitialState(it.next());
		}
		for (int i = 0; i < numAccepting; i++) {
			int transp = r.nextInt(this.ba.getStates().size());
			Iterator<STATE> it = this.ba.getStates().iterator();
			for (int j = 0; j < transp; j++) {
				it.next();
			}
			this.ba.addAcceptState(it.next());
		}
		for (STATE s1 : this.ba.getStates()) {
			for (STATE s2 : this.ba.getStates()) {
				double randInt = r.nextInt(11) / 10.0;
				if (randInt <= transitionProbability) {

					Proposition character = new ArrayList<Proposition>(alphabet)
							.get(r.nextInt(alphabet.size()));

					this.ba.addTransition(s1, s2, this.transitionFactory
							.create(new DNFFormulaImpl(
									new ConjunctiveClauseImpl(character))));

				}

			}
		}
	}
}
