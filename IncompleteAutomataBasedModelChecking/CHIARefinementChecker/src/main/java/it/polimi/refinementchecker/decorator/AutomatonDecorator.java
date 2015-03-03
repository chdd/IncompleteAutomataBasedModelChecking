package it.polimi.refinementchecker.decorator;

import java.util.Set;

import com.google.common.base.Preconditions;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.constraints.Port;

/**
 * Given an automaton, its in-coming and out-coming ports it sets the state
 * which are destinations of in-coming ports as initial and the one which are
 * sources of out-coming ports as final (accepting)
 * 
 * @author claudiomenghi
 *
 * @param <S>
 *            is the type of the state of the automaton to be considered
 * @param <T>
 *            is the type of the transition of the automaton to be considered
 */
public class AutomatonDecorator<S extends State, T extends Transition, A extends BA<S,T>> {

	private final A automaton;
	
	public AutomatonDecorator(A automaton){
		Preconditions.checkNotNull(automaton,
				"The automaton to be decorated cannot be null");
	
		this.automaton=automaton;
	}
	
	public void decorate(Set<Port<S, T>> incomingPorts, Set<Port<S, T>> outComingPorts){
		Preconditions.checkNotNull(incomingPorts,
				"The set of the incoming ports cannot be null");
		Preconditions.checkNotNull(outComingPorts,
				"The set of the outcoming ports cannot be null");
		for(Port<S, T> incomingPort: incomingPorts){
			this.automaton.addInitialState(incomingPort.getDestination());
		}
		for(Port<S, T> outcomingPort: outComingPorts){
			this.automaton.addAcceptState(outcomingPort.getSource());
		}
	
	}
	
}
