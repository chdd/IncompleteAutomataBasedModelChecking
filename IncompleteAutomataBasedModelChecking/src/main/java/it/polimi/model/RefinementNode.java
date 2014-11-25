package it.polimi.model;

import it.polimi.model.impl.automata.IBAImpl;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.model.interfaces.automata.IBA;
import it.polimi.model.interfaces.states.StateFactory;
import it.polimi.model.interfaces.transitions.TransitionFactory;

public class RefinementNode<
	STATE extends State, 
	TRANSITION extends Transition>
	{

	
	private STATE s;
	
	private IBA< STATE, TRANSITION> automaton;
	
	public RefinementNode(
			STATE s,
			IBA<STATE, TRANSITION> automaton){
		this.s=s;
		this.automaton=automaton;
	}
	public RefinementNode(
			STATE s, TransitionFactory<TRANSITION> transitionFactory, StateFactory<STATE> stateFactory){
		this.s=s;
		this.automaton=new IBAImpl<STATE, TRANSITION>(transitionFactory, stateFactory);
	}
	
	public STATE getState(){
		return this.s;
	}
	
	public IBA<STATE, TRANSITION> getAutomaton(){
		return this.automaton;
	}
	
	
	public String toString(){
		if(s==null){
			return "Model";
		}
		return this.s.getId()+":"+this.s.getName().toString();
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
