package it.polimi.view.automaton;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.transition.LabelledTransition;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class AutomatonManagementJPanel<S extends State, T extends LabelledTransition, A extends BuchiAutomaton<S, T>> extends JPanel {

	protected AutomatonLoadingPanel<S, T, A> loadingPanel;
	protected AutomatonJPanel<S,T, A> automatonPanel;
	
	public AutomatonManagementJPanel(LayoutManager l, Dimension d, AutomatonJPanel<S,T, A> automatonPanel, AutomatonLoadingPanel<S, T, A> loadingPanel){
		super(l);
		 this.setBackground(Color.getColor("myColor"));
		 this.setSize(d);
		 this.setMinimumSize(d);
		 this.setMaximumSize(d);
		 this.setPreferredSize(d);
		 this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		
		 this.automatonPanel=automatonPanel;
		 this.loadingPanel=loadingPanel;
		 this.add(automatonPanel);
		 this.add(loadingPanel);
	}
	
	public void setActionListener(ActionListener l){
		this.loadingPanel.setActionListener(l);
	}
}
