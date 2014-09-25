package it.polimi.view.incompleteautomaton;

import it.polimi.view.Actions;
import it.polimi.view.automaton.BuchiButtonJPanel;
import it.polimi.view.incompleteautomaton.editing.IncompleteBuchiAutomatonJCharacterCreator;
import it.polimi.view.incompleteautomaton.editing.IncompleteBuchiAutomatonJStateCreator;
import it.polimi.view.incompleteautomaton.editing.IncompleteBuchiAutomatonJTransitionCreator;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class IncompleteBuchiButtonJPanel extends BuchiButtonJPanel {

	public IncompleteBuchiButtonJPanel(Dimension d, ActionListener container) {
		super(d, container);
	}
	protected void setLoadActionCommand(){
		btnLoad.setActionCommand(Actions.LOADMODEL.name());
	}
	protected void setSaveActionCommand(){
		btnSave.setActionCommand(Actions.SAVEMODEL.name());
	}
	protected void setLabelText(){
		this.label.setText("Model");
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(addState)){
			new IncompleteBuchiAutomatonJStateCreator("Incomplete Buchi state creator", container);
		}
		if(e.getSource().equals(addTransition)){
			new IncompleteBuchiAutomatonJTransitionCreator("Incomplete Buchi state creator", container);
		}
		if(e.getSource().equals(this.addCharacter)){
			new IncompleteBuchiAutomatonJCharacterCreator("Character creator", container);
		}
		else{
			container.actionPerformed(e);
		}
		
	}
}
