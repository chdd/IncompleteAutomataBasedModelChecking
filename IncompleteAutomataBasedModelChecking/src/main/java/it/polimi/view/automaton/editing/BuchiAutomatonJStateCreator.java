package it.polimi.view.automaton.editing;

import it.polimi.view.actions.BuchiActionStateCreation;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class BuchiAutomatonJStateCreator extends JDialog implements ActionListener{


	protected JTextField textField;
	protected JCheckBox initial;
	protected JCheckBox accepting;
	protected ActionListener actionListener = null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BuchiAutomatonJStateCreator(String title, ActionListener container){
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
		JLabel stateNameLabel=new JLabel("State name");
		contentPane.add(stateNameLabel);
		textField=new JTextField(20);
		contentPane.add(textField);
		initial=new JCheckBox("Initial");
		contentPane.add(initial);
		 
		accepting=new JCheckBox("Accepting");
		contentPane.add(accepting);
	}
	protected void addStateButton(Container contentPane){
		
		 JButton addState=new JButton("Add State");
		 contentPane.add(addState);
		 addState.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		BuchiActionStateCreation s=new BuchiActionStateCreation(e.getSource(), e.getID(), e.getActionCommand(), textField.getText(), initial.isSelected(), accepting.isSelected());
		actionListener.actionPerformed(s);
		BuchiAutomatonJStateCreator.this.dispatchEvent(new WindowEvent(
				BuchiAutomatonJStateCreator.this, WindowEvent.WINDOW_CLOSING));
	}
}
