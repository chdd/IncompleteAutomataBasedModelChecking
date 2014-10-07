package it.polimi.model.automata.ba;

import it.polimi.model.automata.ba.state.State;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;

public class Transition<S extends State> {

	/**
	 * the destination state of the transition
	 */
	private final S state;
	
	protected Transition(){
		this.state=null;
	}
	
	public Transition(S to)	{
		if(to==null){
			throw new IllegalArgumentException("The destination state of the transition cannot be null");
		}
		this.state=to;
	}
	
	/**
	 * @return the destination of the transition
	 */
	public S getDestination() {
		return this.state;
	}
}
