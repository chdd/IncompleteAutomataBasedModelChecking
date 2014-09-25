package it.polimi.view.automaton.editing;

import it.polimi.view.actions.BuchiAddCharacterAction;

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

public class BuchiAutomatonJCharacterCreator extends JDialog implements ActionListener{


	protected JTextField character;
	
	
	protected ActionListener actionListener = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BuchiAutomatonJCharacterCreator(String title, ActionListener container){
		super();
		this.setPreferredSize(new Dimension(300,100));
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
		JLabel sourceLabel=new JLabel("character");
		contentPane.add(sourceLabel);
		character=new JTextField(20);
		contentPane.add(character);
	}
	protected void addStateButton(Container contentPane){
		
		 JButton addState=new JButton("Add Character");
		 contentPane.add(addState);
		 addState.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		BuchiAddCharacterAction s=new BuchiAddCharacterAction(e.getSource(), e.getID(), e.getActionCommand(), character.getText());
		actionListener.actionPerformed(s);
		BuchiAutomatonJCharacterCreator.this.dispatchEvent(new WindowEvent(
				BuchiAutomatonJCharacterCreator.this, WindowEvent.WINDOW_CLOSING));
	}
}
