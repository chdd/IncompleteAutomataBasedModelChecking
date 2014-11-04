package it.polimi.view.menu;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;



@SuppressWarnings("serial")
public class IBAStateMenu
	<
	STATE extends State, 
	STATEFACTORY extends StateFactory<STATE>, 
	TRANSITION extends LabelledTransition, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>>
	extends BAStateMenu<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>{
	
	public IBAStateMenu(){
		super();
	    
	}
	
	  protected void stateType(){
		  super.stateType();
		  this.add(new Actions().new Transparent<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>());
	 }

}
