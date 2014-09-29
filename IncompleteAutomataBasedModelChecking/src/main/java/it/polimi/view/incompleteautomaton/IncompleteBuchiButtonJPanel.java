package it.polimi.view.incompleteautomaton;

import it.polimi.controller.actions.file.loading.LoadModel;
import it.polimi.controller.actions.file.saving.SaveModel;
import it.polimi.model.IncompleteBuchiAutomaton;
import it.polimi.model.IntersectionState;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.view.automaton.BuchiButtonJPanel;
import it.polimi.view.incompleteautomaton.editing.IncompleteBuchiAutomatonJCharacterCreator;
import it.polimi.view.incompleteautomaton.editing.IncompleteBuchiAutomatonJStateCreator;
import it.polimi.view.incompleteautomaton.editing.IncompleteBuchiAutomatonJTransitionCreator;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class IncompleteBuchiButtonJPanel<S extends State, T extends Transition<S>, S1 extends IntersectionState<S>, T1 extends Transition<S1>, A extends IncompleteBuchiAutomaton<S, T>> extends BuchiButtonJPanel<S,T, A> {

	public IncompleteBuchiButtonJPanel(Dimension d, ActionListener container) {
		super(d, container);
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
		if(e.getSource().equals(this.btnSave)){
			container.actionPerformed(
					new SaveModel(e.getSource(), e.getID(), e.getActionCommand(), (Frame)SwingUtilities.getRoot(this), this.xmlarea.toString())
					);
		}
		if(e.getSource().equals(this.btnLoad)){
			container.actionPerformed(
					new LoadModel(e.getSource(), e.getID(), e.getActionCommand())
					);
		}
		
		
		
	}
}
