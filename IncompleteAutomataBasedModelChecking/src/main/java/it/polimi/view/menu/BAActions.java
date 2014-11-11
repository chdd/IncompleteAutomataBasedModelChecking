package it.polimi.view.menu;

import it.polimi.controller.actions.automata.states.RenameStateAction;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.menu.actions.ClaimActionFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

public class BAActions<
CONSTRAINEDELEMENT extends State,
STATE extends State, 
STATEFACTORY extends StateFactory<STATE>, 
TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>>  {

	private ClaimActionFactory<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> actionTypesInterface;
	
	public BAActions(ClaimActionFactory<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY> claimActionFactory){
		this.actionTypesInterface=claimActionFactory;
	}
	
	

	@SuppressWarnings("serial")
	public class StateDelete extends JMenuItem implements
			StateListener<CONSTRAINEDELEMENT, STATE, TRANSITION> {
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
	@SuppressWarnings("serial")
	public class TransitionAddCharacter extends Box implements TransitionListener<CONSTRAINEDELEMENT, STATE, TRANSITION> {
		private TRANSITION edge;
		private ActionListener listener;
		private JTextField character;
		JPopupMenu popupmenu;
		

		/** Creates a new instance of DeleteEdgeMenuItem */
		public TransitionAddCharacter(JPopupMenu popupmenuPar) {
			super(BoxLayout.X_AXIS);
			this.popupmenu=popupmenuPar;

			this.add(new JLabel("New DNFFormula: "));
			this.character = new JTextField("Example: (a&&b)||(c&&d)");
			this.add(character);

			this.character.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
	 						
							listener.actionPerformed
							(actionTypesInterface.getChangingLabelAction(e.getSource(), e.getID(), e.getActionCommand(), character.getText(), edge));
							popupmenu.setVisible(false);
				}
			});
		}
		
		public void setEdgeAndView(TRANSITION edge, ActionListener l) {
			this.edge = edge;
			this.listener=l;
		}
	}

	
	@SuppressWarnings("serial")
	public class Rename extends Box implements
			StateListener<CONSTRAINEDELEMENT, STATE, TRANSITION> {
		STATE v;
		ActionListener l;
		JTextField name;
		JPopupMenu popupmenu;
		
		public Rename(JPopupMenu popupmenuPar) {
			super(BoxLayout.X_AXIS);
			this.popupmenu=popupmenuPar;
		
			this.add(new JLabel("Rename: "));
			name = new JTextField("New name");
			this.add(name);

			name.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					l.actionPerformed(new RenameStateAction<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>
					(e.getSource(), e.getID(), e.getActionCommand(), name.getText(), v));
					popupmenu.setVisible(false);
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
			StateListener<CONSTRAINEDELEMENT, STATE, TRANSITION> {
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
	public  class StateAccepting extends JMenuItem implements
			StateListener<CONSTRAINEDELEMENT, STATE, TRANSITION> {
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
	public class TransitionMenu extends JPopupMenu {
		// private JFrame frame;
		public TransitionMenu() {
			super("Edge Menu");
			// this.frame = frame;
			this.add(new TransitionAddCharacter(this));
			this.addSeparator();
			
			this.add(new TransitionDelete());
		}
	}

	@SuppressWarnings("serial")
	public class TransitionDelete extends JMenuItem implements
			TransitionListener<CONSTRAINEDELEMENT, STATE, TRANSITION> {
		
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
}
