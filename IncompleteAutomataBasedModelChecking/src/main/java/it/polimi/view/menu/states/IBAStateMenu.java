package it.polimi.view.menu.states;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.automaton.RefinementTree;
import it.polimi.view.menu.StateListener;
import it.polimi.view.menu.TransitionListener;
import it.polimi.view.menu.actions.ModelActionFactory;
import it.polimi.view.menu.states.BAStateMenu.Rename;
import it.polimi.view.menu.states.BAStateMenu.StateDelete;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;



@SuppressWarnings("serial")
public class IBAStateMenu
	<
	CONSTRAINEDELEMENT extends State, 
	STATE extends State, 
	STATEFACTORY extends StateFactory<STATE>, 
	TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>,
	ACTIONFACTORY extends ModelActionFactory<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>>
	extends BAStateMenu
		<CONSTRAINEDELEMENT, 
		STATE, STATEFACTORY, 
		TRANSITION, 
		TRANSITIONFACTORY, 
		ACTIONFACTORY>{
	

	public IBAStateMenu(ACTIONFACTORY actionfactory, RefinementTree treeP) {
       super(actionfactory);
       this.populate(treeP);
    }
	
	@Override
	protected void populate(){
		  
	 }
	protected void populate(RefinementTree treeP){
		   this.stateType(this.actionTypesInterface, treeP);
	       this.addSeparator();
	       this.add(new StateDelete(treeP));
	       this.addSeparator();
	       this.add(new Rename(this));
	 }
   
    protected void stateType(ACTIONFACTORY actionfactory, RefinementTree treeP){
    	 this.add(new StateInitial());
         this.add(new StateAccepting());
         this.add(new StateTransparent(treeP));
    }
    
    public class StateDelete extends JMenuItem implements
    	StateListener<CONSTRAINEDELEMENT, STATE, TRANSITION> {
		private STATE state;
		private ActionListener l;
		RefinementTree tree;
		
		/** Creates a new instance of DeleteVertexMenuItem */
		public StateDelete(RefinementTree treeP) {
			super("Delete State");
			this.tree=treeP;
			this.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					l.actionPerformed
					(actionTypesInterface.getDeleteStateAction(e.getSource(), e.getID(), e.getActionCommand(), state, (DefaultMutableTreeNode) 
							tree.getTree().getLastSelectedPathComponent()));
				
				}
			});
		}
		
		/**
		 * Implements the VertexMenuListener interface.
		 * 
		 * @param v
		 * @param visComp
		 */
		public void setVertexAndView(STATE state, ActionListener l ) {
			this.state = state;
			this.l=l;
			this.setText("Delete State");
		}
	}
    @SuppressWarnings("serial")
	public class StateTransparent extends JMenuItem implements
			StateListener<CONSTRAINEDELEMENT, STATE, TRANSITION> {
		STATE v;
		ActionListener l;
		RefinementTree treePanel;
		
		public StateTransparent(RefinementTree treeP) {
			super("Transparent");
			this.treePanel=treeP;
			this.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					if(treePanel.getTree().getLastSelectedPathComponent()==null){
						l.actionPerformed(actionTypesInterface.setTransparent(e.getSource(), e.getID(),e.getActionCommand(), v,
								(DefaultMutableTreeNode) treePanel.getTree().getModel().getRoot()));
					}
					else{
						l.actionPerformed(actionTypesInterface.setTransparent(e.getSource(), e.getID(),e.getActionCommand(), v,
								(DefaultMutableTreeNode) treePanel.getTree().getLastSelectedPathComponent()));
								
					}
					
				
				}

			});
		}

		public void setVertexAndView(STATE v, ActionListener l) {
			this.v = v;
			this.l = l;
		}

	}
}
