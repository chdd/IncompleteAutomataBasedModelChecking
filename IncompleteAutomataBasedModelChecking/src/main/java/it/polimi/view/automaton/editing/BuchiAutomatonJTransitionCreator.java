package it.polimi.view.automaton.editing;

import it.polimi.view.actions.BuchiTransitionCreationAction;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class BuchiAutomatonJTransitionCreator extends JDialog implements ActionListener{


	protected JTextField source;
	protected JTextField destination;
	protected JTextField character;
	
	
	protected ActionListener actionListener = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BuchiAutomatonJTransitionCreator(String title, ActionListener container){
		super();
		this.setPreferredSize(new Dimension(300,150));
		Container contentPane=this.getContentPane();
		 
		this.updatePanel(contentPane);
		 
		 contentPane.validate();
		 contentPane.repaint();
		 this.actionListener=container;
		 this.pack();
		 this.setVisible(true);

	}
	
	protected void updatePanel(Container contentPane){
		this.setContainerLayout(contentPane);
		this.addStateInfo(contentPane);
		this.addStateButton(contentPane);
	}
	protected void setContainerLayout(Container contentPane){
		contentPane.setLayout(new GridLayout(0,2));
		contentPane.setBackground(Color.getColor("myColor"));
		contentPane.setVisible(true);
		
	}
	protected void addStateInfo(Container contentPane){
		JLabel sourceLabel=new JLabel("source");
		contentPane.add(sourceLabel);
		source=new JTextField(20);
		contentPane.add(source);
		
		JLabel destinationLabel=new JLabel("destination");
		contentPane.add(destinationLabel);
		destination=new JTextField(20);
		contentPane.add(destination);
		
		JLabel characterLabel=new JLabel("character");
		contentPane.add(characterLabel);
		character=new JTextField(20);
		contentPane.add(character);
		
	}
	protected void addStateButton(Container contentPane){
		
		 JButton addState=new JButton("Add Transition");
		 contentPane.add(addState);
		 addState.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		BuchiTransitionCreationAction s=new BuchiTransitionCreationAction(e.getSource(), e.getID(), e.getActionCommand(), source.getText(), destination.getText(), character.getText());
		actionListener.actionPerformed(s);
		BuchiAutomatonJTransitionCreator.this.dispatchEvent(new WindowEvent(
				BuchiAutomatonJTransitionCreator.this, WindowEvent.WINDOW_CLOSING));
	}
}
