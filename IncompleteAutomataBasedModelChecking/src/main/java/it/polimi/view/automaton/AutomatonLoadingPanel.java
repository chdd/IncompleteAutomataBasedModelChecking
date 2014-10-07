package it.polimi.view.automaton;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.LabelledTransition;
import it.polimi.model.automata.ba.state.State;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class AutomatonLoadingPanel<S extends State, T extends LabelledTransition<S>, A extends BuchiAutomaton<S, T>> extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected AutomatonButtonJPanel jButtonPanel;
	
	public AutomatonLoadingPanel(Dimension d, AutomatonButtonJPanel jButtonPanel){
		
		 this.setBackground(Color.getColor("myColor"));
		 this.setSize(d);
		 this.setMinimumSize(d);
		 this.setMaximumSize(d);
		 this.setPreferredSize(d);
		 
		 this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		 this.add(jButtonPanel);
		 this.jButtonPanel=jButtonPanel;
		 
		 this.repaint();
	}
	protected void setActionListener(ActionListener l){
		this.jButtonPanel.setActionListener(l);
	}
}
