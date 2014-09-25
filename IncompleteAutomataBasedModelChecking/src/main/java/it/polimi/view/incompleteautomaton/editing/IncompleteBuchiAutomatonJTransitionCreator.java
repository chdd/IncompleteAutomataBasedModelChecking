package it.polimi.view.incompleteautomaton.editing;

import it.polimi.view.actions.IncompleteBuchiTransitionCreationAction;
import it.polimi.view.automaton.editing.BuchiAutomatonJTransitionCreator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

public class IncompleteBuchiAutomatonJTransitionCreator extends
		BuchiAutomatonJTransitionCreator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IncompleteBuchiAutomatonJTransitionCreator(String title,
			ActionListener container) {
		super(title, container);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		IncompleteBuchiTransitionCreationAction s=new IncompleteBuchiTransitionCreationAction(e.getSource(), e.getID(), e.getActionCommand(), source.getText(), destination.getText(), character.getText());
		actionListener.actionPerformed(s);
		IncompleteBuchiAutomatonJTransitionCreator.this.dispatchEvent(new WindowEvent(
				IncompleteBuchiAutomatonJTransitionCreator.this, WindowEvent.WINDOW_CLOSING));
	}

}
