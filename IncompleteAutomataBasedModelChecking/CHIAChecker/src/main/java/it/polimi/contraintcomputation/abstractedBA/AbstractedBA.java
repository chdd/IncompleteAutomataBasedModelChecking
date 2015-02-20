package it.polimi.contraintcomputation.abstractedBA;

import it.polimi.automata.impl.IntBAImpl;
import it.polimi.automata.state.State;
import it.polimi.automata.transition.Transition;
import it.polimi.automata.transition.TransitionFactory;
import it.polimi.contraintcomputation.component.Component;

public class AbstractedBA<S extends State, T extends Transition, C extends Component<S, T>>  extends IntBAImpl<C, T> {

	
	public AbstractedBA(TransitionFactory<C, T> transitionFactory) {
		super(transitionFactory);
	}

	public String toString(){
		String ret="";
		ret=ret+"ALPHABET: "+this.getAlphabet()+"\n";
		ret=ret+"STATES: ";
		for(C c: this.getStates()){
			ret=ret+"{"+c.getId()+"}: "+c.getName()+"\t";
		}
		ret=ret+"\n";
		ret=ret+"INITIAL STATES: ";
		for(C c: this.getInitialStates()){
			ret=ret+"{"+c.getId()+"}: "+c.getName()+"\t";
		}
		ret=ret+"\n";
		
		ret=ret+"ACCEPTING STATES: ";
		for(C c: this.getAcceptStates()){
			ret=ret+"{"+c.getId()+"}: "+c.getName()+"\t";
		}
		ret=ret+"\n";
	
		ret=ret+"TRANSITIONS\n";
		for(C s: this.getStates())	{
			ret=ret+"{"+s.getId()+"}: "+s.getName()+" ->\n";
			for(T outEdge: this.getOutTransitions(s))	{
				ret=ret+"\t \t[e:{"+outEdge.getId()+"}:"+outEdge.getLabels()+"- s{"+this.getTransitionDestination(outEdge).getId()+"}: "+this.getTransitionDestination(outEdge).getName()+"]";
			}
			ret=ret+"\n";
			
		}
		return ret;
	}
}
