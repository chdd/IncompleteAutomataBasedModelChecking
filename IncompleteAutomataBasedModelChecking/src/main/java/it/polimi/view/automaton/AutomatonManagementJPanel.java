package it.polimi.view.automaton;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.LabelledTransition;
import it.polimi.model.graph.State;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class AutomatonManagementJPanel<S extends State, T extends LabelledTransition<S>, A extends BuchiAutomaton<S, T>> extends JPanel {

	protected AutomatonLoadingPanel<S, T, A> loadingPanel;
	protected AutomatonJPanel<S,T, A> automatonPanel;
	
	public AutomatonManagementJPanel(LayoutManager l, Dimension d, AutomatonJPanel<S,T, A> automatonPanel, AutomatonLoadingPanel<S, T, A> loadingPanel){
		super(l);
		 this.setBackground(Color.getColor("myColor"));
		 this.setSize(d);
		 this.setMinimumSize(d);
		 this.setMaximumSize(d);
		 this.setPreferredSize(d);
		 this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		 this.automatonPanel=automatonPanel;
		 this.loadingPanel=loadingPanel;
		 this.add(automatonPanel);
		 this.add(loadingPanel);
	}
	
	public void setActionListener(ActionListener l){
		this.loadingPanel.setActionListener(l);
	}
}
