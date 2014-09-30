package it.polimi.view.incompleteautomaton;

import it.polimi.controller.actions.file.loading.LoadModel;
import it.polimi.controller.actions.file.saving.SaveModel;
import it.polimi.model.automata.ba.LabelledTransition;
import it.polimi.model.automata.iba.IncompleteBuchiAutomaton;
import it.polimi.model.graph.State;
import it.polimi.view.buchiautomaton.BuchiButtonJPanel;
import it.polimi.view.incompleteautomaton.editing.IncompleteBuchiAutomatonJCharacterCreator;
import it.polimi.view.incompleteautomaton.editing.IncompleteBuchiAutomatonJStateCreator;
import it.polimi.view.incompleteautomaton.editing.IncompleteBuchiAutomatonJTransitionCreator;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class IncompleteBuchiButtonJPanel<S extends State, T extends LabelledTransition<S>, A extends IncompleteBuchiAutomaton<S, T>> extends BuchiButtonJPanel<S,T, A> {

	public IncompleteBuchiButtonJPanel(Dimension d) {
		super(d);
	}
	
	protected void setLabelText(){
		this.label.setText("Model");
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(addState)){
			new IncompleteBuchiAutomatonJStateCreator("Incomplete Buchi state creator", l);
		}
		if(e.getSource().equals(addTransition)){
			new IncompleteBuchiAutomatonJTransitionCreator("Incomplete Buchi state creator", l);
		}
		if(e.getSource().equals(this.addCharacter)){
			new IncompleteBuchiAutomatonJCharacterCreator("Character creator", l);
		}
		if(e.getSource().equals(this.btnSave)){
			l.actionPerformed(
					new SaveModel(e.getSource(), e.getID(), e.getActionCommand(), (Frame)SwingUtilities.getRoot(this), this.xmlarea.toString())
					);
		}
		if(e.getSource().equals(this.btnLoad)){
			l.actionPerformed(
					new LoadModel(e.getSource(), e.getID(), e.getActionCommand())
					);
		}
		
		
		
	}
}
