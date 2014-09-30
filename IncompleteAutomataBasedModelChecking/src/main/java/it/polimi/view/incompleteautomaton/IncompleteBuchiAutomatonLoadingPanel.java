package it.polimi.view.incompleteautomaton;

import it.polimi.model.automata.ba.LabelledTransition;
import it.polimi.model.automata.iba.IncompleteBuchiAutomaton;
import it.polimi.model.graph.State;
import it.polimi.view.automaton.AutomatonButtonJPanel;
import it.polimi.view.automaton.AutomatonLoadingPanel;
import it.polimi.view.automaton.AutomatonXMLTextArea;

import java.awt.Dimension;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class IncompleteBuchiAutomatonLoadingPanel<S extends State, T extends LabelledTransition<S>, A extends IncompleteBuchiAutomaton<S,T>> extends AutomatonLoadingPanel<S,T,A>{

	public IncompleteBuchiAutomatonLoadingPanel(Dimension d, AutomatonButtonJPanel jButtonPanel, AutomatonXMLTextArea jAutomatonTextArea){
		 super(d, jButtonPanel, jAutomatonTextArea);
	}
	
	public JPanel createButtonPanel(Dimension buttonPanelDimension){
		return new IncompleteBuchiButtonJPanel<S,T,A>(buttonPanelDimension);
	}
	public void update(String automatonXML){
		
		this.xmlArea.update(automatonXML);
	}
	public String getAutomatonXML() {
		return this.xmlArea.getText();
		
	}
}
