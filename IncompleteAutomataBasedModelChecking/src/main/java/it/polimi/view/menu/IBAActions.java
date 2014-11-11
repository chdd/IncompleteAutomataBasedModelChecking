package it.polimi.view.menu;

/*
 * MyMouseMenus.java
 *
 * Created on March 21, 2007, 3:34 PM; Updated May 29, 2007
 *
 * Copyright March 21, 2007 Grotto Networking
 *
 */

import it.polimi.controller.actions.automata.states.RenameStateAction;
import it.polimi.model.RefinementNode;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.menu.actions.ActionTypesInterface;
import it.polimi.view.menu.actions.ModelActionFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class IBAActions<
CONSTRAINEDELEMENT extends State,
	STATE extends State, 
	STATEFACTORY extends StateFactory<STATE>, 
	TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>>  {

	private ModelActionFactory<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> actionTypesInterface;
	
	public IBAActions(ModelActionFactory<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> actionTypesInterface){
		this.actionTypesInterface=actionTypesInterface;
	}
	
	
	@SuppressWarnings("serial")
	public class StateDelete extends JMenuItem implements
			StateListener<CONSTRAINEDELEMENT, STATE, TRANSITION> {
		private STATE state;
		private ActionListener l;
		JTree tree;
		
		/** Creates a new instance of DeleteVertexMenuItem */
		public StateDelete(JTree treeP) {
			super("Delete State");
			this.tree=treeP;
			this.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					l.actionPerformed
					(actionTypesInterface.getDeleteStateAction(e.getSource(), e.getID(), e.getActionCommand(), state, (DefaultMutableTreeNode) 
							tree.getLastSelectedPathComponent()));
				
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
		JTree tree;
		
		public StateTransparent(JTree treeP) {
			super("Transparent");
			this.tree=treeP;
			this.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					if(tree.getLastSelectedPathComponent()==null){
						l.actionPerformed(actionTypesInterface.setTransparent(e.getSource(), e.getID(),e.getActionCommand(), v,
								(DefaultMutableTreeNode) tree.getModel().getRoot()));
					}
					else{
						l.actionPerformed(actionTypesInterface.setTransparent(e.getSource(), e.getID(),e.getActionCommand(), v,
								(DefaultMutableTreeNode) tree.getLastSelectedPathComponent()));
								
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