package it.polimi.view.incompleteautomaton;

import it.polimi.view.Actions;
import it.polimi.view.automaton.BuchiButtonJPanel;

import java.awt.Dimension;
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
}
