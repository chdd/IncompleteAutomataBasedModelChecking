package it.polimi.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;

public class ConstrainedTransition<S1 extends State,S extends IntersectionState<S1>> extends Transition<S> {

	@XmlElement(name="constrainedState", type=State.class)
	@XmlIDREF
	private S1 constrainedState;
	
	public ConstrainedTransition(String c, S to, S1 constrainedState) {
		super(c, to);
		this.constrainedState=constrainedState;
	}

	/**
	 * @return the constrainedState
	 */
	public S1 getConstrainedState() {
		return constrainedState;
	}

	/**
	 * @param constrainedState the constrainedState to set
	 */
	public void setConstrainedState(S1 constrainedState) {
		this.constrainedState = constrainedState;
	}

	@Override
	public String toString() {
		return "<"+constrainedState.getName()+","+super.getCharacter()+">" + "->"+ this.getDestination();
	}
}
