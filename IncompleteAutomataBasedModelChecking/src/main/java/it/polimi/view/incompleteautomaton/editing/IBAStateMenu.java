package it.polimi.view.incompleteautomaton.editing;

import java.awt.event.ActionListener;


@SuppressWarnings("serial")
public class IBAStateMenu extends BAStateMenu{
	
	public IBAStateMenu(ActionListener l){
		super(l);
	    
	}
	
	  protected void stateType(ActionListener l){
		  super.stateType(l);
		  this.add(new Actions.Transparent());
	 }

}
