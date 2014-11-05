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
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.menu.actions.ActionTypesInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

public class Actions<
	STATE extends State, 
	STATEFACTORY extends StateFactory<STATE>, 
	TRANSITION extends LabelledTransition, 
TRANSITIONFACTORY extends LabelledTransitionFactory<TRANSITION>>  {

	private ActionTypesInterface<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> actionTypesInterface;
	
	public Actions(ActionTypesInterface<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> actionTypesInterface){
		this.actionTypesInterface=actionTypesInterface;
	}
	
	
	@SuppressWarnings("serial")
	public class TransitionMenu
	extends JPopupMenu {
		// private JFrame frame;
		public TransitionMenu() {
			super("Edge Menu");
			// this.frame = frame;
			this.add(new TransitionAddCharacter());
			this.addSeparator();
			
			this.add(new TransitionDelete());
		}
	}

	@SuppressWarnings("serial")
	public class TransitionAddCharacter
	
	extends Box implements TransitionListener<STATE, TRANSITION> {
		private TRANSITION edge;
		private ActionListener listener;
		private JTextField character;

		/** Creates a new instance of DeleteEdgeMenuItem */
		public TransitionAddCharacter() {
			super(BoxLayout.X_AXIS);

			this.add(new JLabel("New DNFFormula: "));
			this.character = new JTextField("Example: (a&&b)||(c&&d)");
			this.add(character);

			this.character.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
	 						
							listener.actionPerformed
							(actionTypesInterface.getChangingLabelAction(e.getSource(), e.getID(), e.getActionCommand(), character.getText(), edge));
	 				
				}
			});
		}
		
		public void setEdgeAndView(TRANSITION edge, ActionListener l) {
			this.edge = edge;
			this.listener=l;
		}
	}

	
	

	@SuppressWarnings("serial")
	public class TransitionDelete extends JMenuItem implements
			TransitionListener<STATE, TRANSITION> {
		
		private TRANSITION edge;
		private ActionListener l;

		/** Creates a new instance of DeleteEdgeMenuItem */
		public TransitionDelete() {
			super("Delete Transition");
			this.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					l.actionPerformed(actionTypesInterface.deleteEdgeAction(e.getSource(), e.getID(), e.getActionCommand(), edge));
					
				}
			});
		}

		/**
		 * Implements the EdgeMenuListener interface to update the menu item
		 * with info on the currently chosen edge.
		 * 
		 * @param edge
		 * @param visComp
		 */
		public void setEdgeAndView(TRANSITION edge, ActionListener l) {
			this.edge = edge;
			this.l = l;
			this.setText("Delete Transition");
		}

	}

	@SuppressWarnings("serial")
	public class Rename extends Box implements
			StateListener<STATE, TRANSITION> {
		STATE v;
		ActionListener l;
		JTextField name;
	
		public Rename() {
			super(BoxLayout.X_AXIS);
		
			this.add(new JLabel("Rename: "));
			name = new JTextField("New name");
			this.add(name);

			name.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					l.actionPerformed(new RenameStateAction<STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>
					(e.getSource(), e.getID(), e.getActionCommand(), name.getText(), v));

				}

			});
		}

		public void setVertexAndView(STATE v, ActionListener l) {
			this.v = v;
			this.l=l;
		}
	}

	@SuppressWarnings("serial")
	public class StateInitial extends JMenuItem implements
			StateListener<STATE, TRANSITION> {
		private STATE v;
		private ActionListener l;
		
		public StateInitial() {
			super("Initial");
			this.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					l.actionPerformed(actionTypesInterface.setInitial(e.getSource(), e.getID(), e.getActionCommand(), v));
						
		
				}

			});
		}

		public void setVertexAndView(STATE v, ActionListener l) {
			this.v = v;
			this.l=l;
		}

	}

	@SuppressWarnings("serial")
	public class StateTransparent extends JMenuItem implements
			StateListener<STATE, TRANSITION> {
		STATE v;
		ActionListener l;

		public StateTransparent() {
			super("Transparent");
			this.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					l.actionPerformed(actionTypesInterface.setTransparent(e.getSource(), e.getID(),e.getActionCommand(), v));
				
				}

			});
		}

		public void setVertexAndView(STATE v, ActionListener l) {
			this.v = v;
			this.l = l;
		}

	}

	@SuppressWarnings("serial")
	public  class StateAccepting extends JMenuItem implements
			StateListener<STATE, TRANSITION> {
		STATE state;
		ActionListener l;

		public StateAccepting() {
			super("Accepting");

			this.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					l.actionPerformed
					(actionTypesInterface.setAccepting(e.getSource(), e.getID(), e.getActionCommand(), state));
				
				}

			});
		}

		public void setVertexAndView(STATE v, ActionListener l) {
			this.state = v;
			this.l=l;
		}

	}

	@SuppressWarnings("serial")
	public class StateDelete extends JMenuItem implements
			StateListener<STATE, TRANSITION> {
		private STATE state;
		private ActionListener l;
		
		/** Creates a new instance of DeleteVertexMenuItem */
		public StateDelete() {
			super("Delete State");
			this.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					l.actionPerformed
					(actionTypesInterface.getDeleteStateAction(e.getSource(), e.getID(), e.getActionCommand(), state));
				
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
}