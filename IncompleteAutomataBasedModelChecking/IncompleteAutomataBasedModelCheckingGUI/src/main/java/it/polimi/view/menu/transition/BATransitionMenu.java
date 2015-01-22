package it.polimi.view.menu.transition;

import it.polimi.model.impl.states.State;
import it.polimi.model.impl.transitions.Transition;
import it.polimi.view.menu.ClaimActionFactory;
import it.polimi.view.menu.TransitionListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class BATransitionMenu<
	CONSTRAINEDELEMENT extends State,
	STATE extends State, 
	TRANSITION extends Transition, 
	ACTIONFACTORY extends ClaimActionFactory<CONSTRAINEDELEMENT, STATE, TRANSITION>>  extends JPopupMenu{

	protected ACTIONFACTORY actionTypesInterface;
	
	public BATransitionMenu(ACTIONFACTORY actionTypesInterface) {
		super("Edge Menu");
		this.actionTypesInterface=actionTypesInterface;
		this.setActions();
	}
	
	public void setActions(){
		this.add(new TransitionAddCharacter(this));
		this.addSeparator();
		
		this.add(new TransitionDelete());
	}
	
	
	public class TransitionDelete extends JMenuItem implements
			TransitionListener<CONSTRAINEDELEMENT, STATE, TRANSITION> {
		
		protected TRANSITION edge;
		protected ActionListener l;

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
}
