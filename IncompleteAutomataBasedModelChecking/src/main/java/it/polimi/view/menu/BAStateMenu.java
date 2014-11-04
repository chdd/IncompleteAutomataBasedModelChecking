package it.polimi.view.menu;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.menu.actions.ClaimActionFactory;

import javax.swing.JPopupMenu;

@SuppressWarnings("serial")
public class BAStateMenu
	<
	STATE extends State, 
	STATEFACTORY extends StateFactory<STATE>, 
	TRANSITION extends LabelledTransition, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>>
	extends JPopupMenu {

   public BAStateMenu() {
       super("State Menu");
       
       this.stateType();
       this.addSeparator();
       this.add(new Actions<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(new ClaimActionFactory<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>()).new StateDelete());
       this.addSeparator();
       this.add(new Actions<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(new ClaimActionFactory<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>()).new Rename());
    }
   
    protected void stateType(){
    	 this.add(new Actions<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(new ClaimActionFactory<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>()).new StateInitial());
         this.add(new Actions<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(new ClaimActionFactory<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>()).new StateAccepting());
    }
}