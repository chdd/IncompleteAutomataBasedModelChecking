package it.polimi.view.incompleteautomaton;

import it.polimi.model.automata.ba.LabelledTransition;
import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.iba.IncompleteBuchiAutomaton;
import it.polimi.view.buchiautomaton.BuchiButtonJPanel;
import it.polimi.view.incompleteautomaton.editing.IncompleteBuchiAutomatonJCharacterCreator;
import it.polimi.view.incompleteautomaton.editing.IncompleteBuchiAutomatonJStateCreator;
import it.polimi.view.incompleteautomaton.editing.IncompleteBuchiAutomatonJTransitionCreator;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

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
		
	}
}
