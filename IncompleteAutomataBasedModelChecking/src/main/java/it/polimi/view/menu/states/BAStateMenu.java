package it.polimi.view.menu.states;

import it.polimi.controller.actions.automata.states.RenameStateAction;
import it.polimi.model.impl.states.State;
import it.polimi.model.impl.states.StateFactory;
import it.polimi.model.impl.transitions.LabelledTransition;
import it.polimi.model.interfaces.transitions.LabelledTransitionFactory;
import it.polimi.view.menu.StateListener;
import it.polimi.view.menu.TransitionListener;
import it.polimi.view.menu.actions.ClaimActionFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class BAStateMenu
	<
	CONSTRAINEDELEMENT extends State,
	STATE extends State, 
	STATEFACTORY extends StateFactory<STATE>, 
	TRANSITION extends LabelledTransition<CONSTRAINEDELEMENT>, 
	TRANSITIONFACTORY extends LabelledTransitionFactory<CONSTRAINEDELEMENT, TRANSITION>,
	ACTIONFACTORY extends ClaimActionFactory<CONSTRAINEDELEMENT, STATE, STATEFACTORY, TRANSITION, TRANSITIONFACTORY>>
	extends JPopupMenu {

	ACTIONFACTORY actionTypesInterface;

   public BAStateMenu(ACTIONFACTORY factory) {
       super("State Menu");
       actionTypesInterface=factory;
      this.populate();
    }
   
   protected void populate(){
	   this.stateType(actionTypesInterface);
       this.addSeparator();
       this.add(new StateDelete());
       this.addSeparator();
       this.add(new Rename(this));
   }
   
    protected void stateType(ACTIONFACTORY factory){
    	 this.add(new StateInitial());
         this.add(new StateAccepting());
    }


	public class StateDelete extends JMenuItem implements
		StateListener<CONSTRAINEDELEMENT, STATE, TRANSITION> {
		protected STATE state;
		protected ActionListener l;
		
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
	public class StateAccepting extends JMenuItem implements
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
}