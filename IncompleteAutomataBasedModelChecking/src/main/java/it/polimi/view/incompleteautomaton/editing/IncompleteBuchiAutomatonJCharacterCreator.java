package it.polimi.view.incompleteautomaton.editing;

import it.polimi.view.actions.IncompleteBuchiAddCharacterAction;
import it.polimi.view.automaton.editing.BuchiAutomatonJCharacterCreator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class IncompleteBuchiAutomatonJCharacterCreator extends
		BuchiAutomatonJCharacterCreator {

	public IncompleteBuchiAutomatonJCharacterCreator(String title,
			ActionListener container) {
		super(title, container);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		IncompleteBuchiAddCharacterAction s=new IncompleteBuchiAddCharacterAction(e.getSource(), e.getID(), e.getActionCommand(), character.getText());
		actionListener.actionPerformed(s);
		IncompleteBuchiAutomatonJCharacterCreator.this.dispatchEvent(new WindowEvent(
				IncompleteBuchiAutomatonJCharacterCreator.this, WindowEvent.WINDOW_CLOSING));
	}
}
