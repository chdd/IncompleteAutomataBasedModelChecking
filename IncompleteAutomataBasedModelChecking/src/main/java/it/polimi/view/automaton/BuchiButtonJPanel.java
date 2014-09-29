package it.polimi.view.automaton;

import it.polimi.controller.actions.file.loading.LoadSpecification;
import it.polimi.controller.actions.file.saving.SaveSpecification;
import it.polimi.model.BuchiAutomaton;
import it.polimi.model.IntersectionState;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.view.ViewInterface;
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
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class BuchiButtonJPanel<S extends State, T extends Transition<S>, A extends BuchiAutomaton<S,T>>  extends JPanel implements ActionListener{

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
	protected ActionListener container;
	protected BuchiAutomatonXMLTextArea<S,T,A> xmlarea;
	
	public void setXMLarea(BuchiAutomatonXMLTextArea<S,T,A> xmlarea){
		this.xmlarea=xmlarea;
	}
	
	public BuchiButtonJPanel(Dimension d, ActionListener container){
		 super();
		 this.container=container;
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
			new BuchiAutomatonJStateCreator("Incomplete Buchi state creator", container);
		}
		if(e.getSource().equals(addTransition)){
			new BuchiAutomatonJTransitionCreator("Incomplete Buchi state creator", container);
		}
		if(e.getSource().equals(this.addCharacter)){
			new BuchiAutomatonJCharacterCreator("Character creator", container);
		}
		if(e.getSource().equals(this.btnSave)){
			container.actionPerformed(
					new SaveSpecification(e.getSource(), e.getID(), e.getActionCommand(), (Frame)SwingUtilities.getRoot(this), this.xmlarea.toString())
			);
		}
		if(e.getSource().equals(this.btnLoad)){
			container.actionPerformed(
					new LoadSpecification(e.getSource(), e.getID(), e.getActionCommand())
					);
		}
		
	}
}
