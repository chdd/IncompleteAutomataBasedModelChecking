package it.polimi.view.menu;

import javax.swing.JPopupMenu;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.menu.Actions.Accepting;
import it.polimi.view.menu.Actions.DeleteVertexMenuItem;
import it.polimi.view.menu.Actions.Initial;
import it.polimi.view.menu.Actions.Rename;
import it.polimi.view.menu.actions.ModelActionFactory;



@SuppressWarnings("serial")
public class IBAStateMenu
	<
	STATE extends State, 
	STATEFACTORY extends StateFactory<STATE>, 
	TRANSITION extends LabelledTransition, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>>
	extends JPopupMenu{
	
	
	public IBAStateMenu() {
       super("State Menu");
       
       this.stateType();
       this.addSeparator();
       this.add(new Actions(new ModelActionFactory<>()).new DeleteVertexMenuItem<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>());
       this.addSeparator();
       this.add(new Actions(new ModelActionFactory<>()).new Rename<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>());
    }
   
    protected void stateType(){
    	 this.add(new Actions(new ModelActionFactory<>()).new Initial<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>());
         this.add(new Actions(new ModelActionFactory<>()).new Accepting());
         this.add(new Actions(new ModelActionFactory<>()).new Transparent<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>());
    }


}
