package it.polimi.view.menu;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;

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
       this.add(new Actions().new DeleteVertexMenuItem<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>());
       this.addSeparator();
       this.add(new Actions().new Rename<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>());
    }
   
    protected void stateType(){
    	 this.add(new Actions().new Initial<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>());
         this.add(new Actions().new Accepting());
    }
}