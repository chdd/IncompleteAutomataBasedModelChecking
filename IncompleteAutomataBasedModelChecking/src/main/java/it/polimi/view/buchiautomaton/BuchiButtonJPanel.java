package it.polimi.view.buchiautomaton;

import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.LabelledTransition;
import it.polimi.model.automata.ba.state.State;
import it.polimi.view.automaton.AutomatonButtonJPanel;
import it.polimi.view.automaton.editing.BuchiAutomatonJCharacterCreator;
import it.polimi.view.automaton.editing.BuchiAutomatonJStateCreator;
import it.polimi.view.automaton.editing.BuchiAutomatonJTransitionCreator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;


public class BuchiButtonJPanel<S extends State, T extends LabelledTransition<S>, A extends BuchiAutomaton<S,T>>  extends AutomatonButtonJPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected JLabel label;
	protected JButton addState;
	protected JButton addTransition;
	protected JButton addCharacter;
	
	
	public BuchiButtonJPanel(Dimension d){
		 super();
		 this.setBackground(Color.getColor("myColor"));
		 this.setSize(d);
		 this.setMinimumSize(d);
		this.setMaximumSize(d);
		this.setPreferredSize(d);
		
		 Dimension buttonDimension=new Dimension(d.width/2/4, d.height/2/5);
		 
		 this.label=new JLabel();
		 this.label.setSize(buttonDimension);
		 this.label.setForeground(Color.white);
		 this.label.setFont(new Font("Verdana", Font.BOLD, 32));
		 this.setLabelText();
		 this.add(label);
		 
		 addState = new JButton("Add State");
		 addState.setSize(buttonDimension);
		 addState.setOpaque(true);
		 addState.setLocation(d.width/2+buttonDimension.width, d.height/2/5/20);
		 addState.setVisible(true);
		 addState.setBackground(Color.getColor("myColor"));
		 addState.addActionListener(this);
		 this.add(addState);
		 
		 addTransition = new JButton("Add Transition");
		 addTransition.setSize(buttonDimension);
		 addTransition.setOpaque(true);
		 addTransition.setLocation(d.width/2+buttonDimension.width, d.height/2/5/20);
		 addTransition.setVisible(true);
		 addTransition.setBackground(Color.getColor("myColor"));
		 addTransition.addActionListener(this);
		 this.add(addTransition);
		 
		 addCharacter = new JButton("Add Character");
		 addCharacter.setSize(buttonDimension);
		 addCharacter.setOpaque(true);
		 addCharacter.setLocation(d.width/2+buttonDimension.width, d.height/2/5/20);
		 addCharacter.setVisible(true);
		 addCharacter.setBackground(Color.getColor("myColor"));
		 addCharacter.addActionListener(this);
		 this.add(addCharacter);
		 
		 
	}
	protected void setLabelText(){
		this.label.setText("Specification");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(addState)){
			new BuchiAutomatonJStateCreator("Incomplete Buchi state creator", l);
		}
		if(e.getSource().equals(addTransition)){
			new BuchiAutomatonJTransitionCreator("Incomplete Buchi state creator", l);
		}
		if(e.getSource().equals(this.addCharacter)){
			new BuchiAutomatonJCharacterCreator("Character creator", l);
		}
	}
}
