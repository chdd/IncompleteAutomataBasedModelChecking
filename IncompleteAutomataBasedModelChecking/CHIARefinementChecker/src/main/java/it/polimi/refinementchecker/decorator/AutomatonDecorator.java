package it.polimi.refinementchecker.decorator;

import it.polimi.automata.BA;
import it.polimi.constraints.Port;

import java.util.Set;

import com.google.common.base.Preconditions;

/**
 * Given an automaton, its in-coming and out-coming ports it sets the state
 * which are destinations of in-coming ports as initial and the one which are
 * sources of out-coming ports as final (accepting)
 * 
 * @author claudiomenghi
 * @param A
 *            contains the automaton to be decorated
 *
 */
public class AutomatonDecorator<A extends BA> {

	/**
	 * contains the automaton to be decorated
	 */
	private final A automaton;

	/**
	 * creates a new Automaton decorator
	 * 
	 * @param automaton
	 *            is the automaton to be decorated
	 * @throws NullPointerException
	 *             if the automaton to be decorated is null
	 */
	public AutomatonDecorator(A automaton) {
		Preconditions.checkNotNull(automaton,
				"The automaton to be decorated cannot be null");

		this.automaton = automaton;
	}

	/**
	 * decorates the automaton considering the set of incoming and out-coming
	 * Ports
	 * 
	 * @param incomingPorts
	 *            is the set of incomingPorts to be considered
	 * @param outComingPorts
	 *            is the set of outomingPorts to be considered
	 * @throws NullPointerException
	 *             if one of the parameters is null
	 * @throws IllegalArgumentException
	 *             if the destination of an incomingPort is not in the automaton
	 *             or if the source of an outcomingPort is not in the automaton
	 */
	public void decorate(Set<Port> incomingPorts, Set<Port> outComingPorts) {
		Preconditions.checkNotNull(incomingPorts,
				"The set of the incoming ports cannot be null");
		Preconditions.checkNotNull(outComingPorts,
				"The set of the outcoming ports cannot be null");
		for (Port incomingPort : incomingPorts) {
			Preconditions.checkArgument(this.automaton.getStates().contains(incomingPort.getDestination()));
			this.automaton.addInitialState(incomingPort.getDestination());
		}
		for (Port outcomingPort : outComingPorts) {
			Preconditions.checkArgument(this.automaton.getStates().contains(outcomingPort.getSource()));
			this.automaton.addAcceptState(outcomingPort.getSource());
		}
	}
}
