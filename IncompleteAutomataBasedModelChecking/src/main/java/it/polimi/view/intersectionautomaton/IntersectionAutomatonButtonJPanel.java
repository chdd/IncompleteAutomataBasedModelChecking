package it.polimi.view.intersectionautomaton;

import it.polimi.view.automaton.AutomatonButtonJPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class IntersectionAutomatonButtonJPanel extends AutomatonButtonJPanel {

	public IntersectionAutomatonButtonJPanel(Dimension buttonPanelDimension){
		JLabel label=new JLabel();
		this.setBackground(Color.getColor("myColor"));
		this.setSize(buttonPanelDimension);
		this.setMinimumSize(buttonPanelDimension);
		this.setMaximumSize(buttonPanelDimension);
		this.setPreferredSize(buttonPanelDimension);
		Dimension buttonDimension=new Dimension(buttonPanelDimension.width/2/4, buttonPanelDimension.height/2/5);
		label.setSize(buttonDimension);
		label.setForeground(Color.white);
		label.setFont(new Font("Verdana", Font.BOLD, 32));
		label.setText("Verification results");
		this.add(label);
	}
	
}
