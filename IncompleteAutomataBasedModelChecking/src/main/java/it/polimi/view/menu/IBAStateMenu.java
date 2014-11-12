package it.polimi.view.menu;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.automaton.RefinementTree;
import it.polimi.view.menu.actions.ModelActionFactory;

import javax.swing.JPopupMenu;



@SuppressWarnings("serial")
public class IBAStateMenu
	<
	CONSTRAINEDELEMENT extends State, 
	STATE extends State, 
	STATEFACTORY extends StateFactory<STATE>, 
	TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>>
	extends JPopupMenu{
	
	
	public IBAStateMenu(RefinementTree treeP) {
       super("State Menu");
       
       this.stateType(treeP);
       this.addSeparator();
       this.add(new IBAActions<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(new ModelActionFactory<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>()).new StateDelete(treeP));
       this.addSeparator();
       this.add(new BAActions<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(new ModelActionFactory<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>()).new Rename(this));
       
    }
   
    protected void stateType(RefinementTree treeP){
    	 this.add(new BAActions<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(new ModelActionFactory<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>()).new StateInitial());
         this.add(new BAActions<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(new ModelActionFactory<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>()).new StateAccepting());
         this.add(new IBAActions<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>(new ModelActionFactory<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>()).new StateTransparent(treeP));
    }


}
