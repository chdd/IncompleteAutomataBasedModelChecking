package it.polimi.constraints;

import it.polimi.automata.BA;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;

/**
 * Contains a component. A component is a sub part of the intersection automaton
 * that corresponds with a transparent state of the model and will generate a
 * constraint
 * 
 * @author claudiomenghi
 * @param S
 *            is the type of the state of the component
 * @param T
 *            is the type of the transitions of the component
 * 
 */
public interface Component<S extends State, T extends Transition, A extends BA<S, T>>
		extends State {

	/**
	 * returns the model of the state
	 * 
	 * @return the modelState
	 */
	public S getModelState();

	/**
	 * returns the BA automaton. The initial states of the automata contains the
	 * states reached by an incoming transition, no additional accepting states
	 * are added
	 * 
	 * @return the automaton that is read from the BA, the initial states of the
	 *         automata contains the states reached by an incoming transition,
	 *         no additional accepting states are added
	 */
	public A getAutomaton();
	
	
	public void setAutomaton(A automaton);

}
