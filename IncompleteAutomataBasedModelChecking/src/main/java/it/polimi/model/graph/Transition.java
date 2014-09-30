package it.polimi.model.graph;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;

import it.polimi.model.State;

public class Transition<S extends State> {

	/**
	 * the destination state of the transition
	 */
	@XmlElement(name="destinationState", type=State.class)
	@XmlIDREF
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
