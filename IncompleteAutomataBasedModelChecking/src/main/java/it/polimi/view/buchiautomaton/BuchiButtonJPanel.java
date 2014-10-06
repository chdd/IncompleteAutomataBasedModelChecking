package it.polimi.view.buchiautomaton;

import it.polimi.controller.actions.file.loading.LoadSpecification;
import it.polimi.controller.actions.file.saving.SaveSpecification;
import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.LabelledTransition;
import it.polimi.model.automata.ba.State;
import it.polimi.view.automaton.AutomatonButtonJPanel;
import it.polimi.view.automaton.AutomatonXMLTextArea;
import it.polimi.view.automaton.editing.BuchiAutomatonJCharacterCreator;
import it.polimi.view.automaton.editing.BuchiAutomatonJStateCreator;
import it.polimi.view.automaton.editing.BuchiAutomatonJTransitionCreator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;


public class BuchiButtonJPanel<S extends State, T extends LabelledTransition<S>, A extends BuchiAutomaton<S,T>>  extends AutomatonButtonJPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected JLabel label;
	protected JButton addState;
	protected JButton addTransition;
	protected JButton addCharacter;
	protected JButton btnLoad;
	protected JButton btnSave;
	
	protected AutomatonXMLTextArea xmlarea;
	
	public void setXMLarea(AutomatonXMLTextArea xmlarea){
		this.xmlarea=xmlarea;
	}
	
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
		 
		 btnLoad = new JButton("Open");
		 btnLoad.setSize(buttonDimension);
		 btnLoad.setOpaque(true);
		 btnLoad.setLocation(d.width/2+buttonDimension.width, d.height/2/5/20);
		 btnLoad.setVisible(true);
		 btnLoad.setBackground(Color.getColor("myColor"));
		 btnLoad.addActionListener(this);
		 this.add(btnLoad);
		 
		 btnSave = new JButton("Save");
		 btnSave.setSize(buttonDimension);
		 btnSave.setOpaque(true);
		 btnSave.setLocation(d.width/2+(buttonDimension.width*2), d.height/2/5/20);
		 btnSave.setVisible(true);
		 btnSave.setBackground(Color.getColor("myColor"));
		 btnSave.addActionListener(this);
		 this.add(btnSave);
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
		if(e.getSource().equals(this.btnSave)){
			l.actionPerformed(
					new SaveSpecification(e.getSource(), e.getID(), e.getActionCommand(), (Frame)SwingUtilities.getRoot(this), this.xmlarea.toString())
			);
		}
		if(e.getSource().equals(this.btnLoad)){
			l.actionPerformed(
					new LoadSpecification(e.getSource(), e.getID(), e.getActionCommand())
					);
		}
		
	}
}
