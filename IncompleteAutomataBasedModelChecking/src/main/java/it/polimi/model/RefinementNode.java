package it.polimi.model;

import it.polimi.model.impl.automata.IBAImpl;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.automata.drawable.DrawableIBA;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

public class RefinementNode<
	CONSTRAINEDELEMENT extends State,
	STATE extends State, 
	TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>>
	{

	
	private STATE s;
	
	private DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> automaton;
	
	public RefinementNode(
			STATE s,
			DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> automaton){
		this.s=s;
		this.automaton=automaton;
	}
	public RefinementNode(
			STATE s, TRANSITIONFACTORY transitionFactory){
		this.s=s;
		this.automaton=new IBAImpl<CONSTRAINEDELEMENT, STATE, TRANSITION, 
				TRANSITIONFACTORY>(transitionFactory);
	}
	
	public STATE getState(){
		return this.s;
	}
	
	public  DrawableIBA<CONSTRAINEDELEMENT, STATE, TRANSITION, TRANSITIONFACTORY> getAutomaton(){
		return this.automaton;
	}
	
	
	public String toString(){
		if(s==null){
			return "Model";
		}
		return this.s.getName().toString();
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((s == null) ? 0 : s.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		RefinementNode other = (RefinementNode) obj;
		if (s == null) {
			if (other.s != null)
				return false;
		} else if (!s.equals(other.s))
			return false;
		return true;
	}
	


	

}
