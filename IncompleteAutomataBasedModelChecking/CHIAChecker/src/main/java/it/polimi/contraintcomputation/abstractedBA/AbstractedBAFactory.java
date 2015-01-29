package it.polimi.contraintcomputation.abstractedBA;

import it.polimi.automata.labeling.Label;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.contraintcomputation.component.Component;

public class AbstractedBAFactory<L extends Label, S extends State, T extends Transition<L>, C extends Component<L, S, T>> {

	public AbstractedBA<L,S,T,C> create(){
		return new AbstractedBA<L,S,T,C>();
	}
}
