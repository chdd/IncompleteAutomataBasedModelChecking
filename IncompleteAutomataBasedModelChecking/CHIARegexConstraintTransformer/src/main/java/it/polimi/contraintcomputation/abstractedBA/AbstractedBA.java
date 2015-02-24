package it.polimi.contraintcomputation.abstractedBA;

import it.polimi.automata.impl.IntBAImpl;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.constraints.Component;

/**
 * contains the abstracted version of the intersection automaton
 * 
 * @author claudiomenghi
 *
 * @param <S>
 *            is the type of the state of the intersection automaton
 * @param <T>
 *            is the type of the transitions of the intersection automaton
 * @param <C>
 *            is the type of the component of the abstracted BA
 */
public class AbstractedBA<S extends State, T extends Transition, C extends Component<S, T>>
		extends IntBAImpl<C, T> {

	/**
	 * creates a new Abstracted Buchi automaton
	 * 
	 * @param transitionFactory
	 *            is the factory which is used to create the transitions of the
	 *            abstracted automaton
	 * @throws NullPointerException
	 *             if the transition factory is null
	 */
	public AbstractedBA(TransitionFactory<C, T> transitionFactory) {
		super(transitionFactory);
	}

	/**
	 * returns the string representation of the abstracted automaton
	 * 
	 * @return the string representation of the abstracted automaton
	 */
	@Override
	public String toString() {
		String ret = "";
		ret = ret + "ALPHABET: " + this.getAlphabet() + "\n";
		ret = ret + "STATES: ";
		for (C c : this.getStates()) {
			ret = ret + "{" + c.getId() + "}: " + c.getName() + "\t";
		}
		ret = ret + "\n";
		ret = ret + "INITIAL STATES: ";
		for (C c : this.getInitialStates()) {
			ret = ret + "{" + c.getId() + "}: " + c.getName() + "\t";
		}
		ret = ret + "\n";

		ret = ret + "ACCEPTING STATES: ";
		for (C c : this.getAcceptStates()) {
			ret = ret + "{" + c.getId() + "}: " + c.getName() + "\t";
		}
		ret = ret + "\n";

		ret = ret + "TRANSITIONS\n";
		for (C s : this.getStates()) {
			ret = ret + "{" + s.getId() + "}: " + s.getName() + " ->\n";
			for (T outEdge : this.getOutTransitions(s)) {
				ret = ret + "\t \t[e:{" + outEdge.getId() + "}:"
						+ outEdge.getPropositions() + "- s{"
						+ this.getTransitionDestination(outEdge).getId()
						+ "}: "
						+ this.getTransitionDestination(outEdge).getName()
						+ "]";
			}
			ret = ret + "\n";

		}
		return ret;
	}
}
