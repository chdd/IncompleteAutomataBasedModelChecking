package it.polimi.view.incompleteautomaton.editing;

import it.polimi.controller.actions.IncompleteBuchiActionStateCreation;
import it.polimi.view.automaton.editing.BuchiAutomatonJStateCreator;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

public class IncompleteBuchiAutomatonJStateCreator extends BuchiAutomatonJStateCreator{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JRadioButton regular;
	
	public IncompleteBuchiAutomatonJStateCreator(String title,
			ActionListener container) {
		super(title, container);
		
	}

	protected void updatePanel(Container contentPane){
		this.setContainerLayout(contentPane);
		this.addStateInfo(contentPane);
		this.addRegular(contentPane);
		this.addStateButton(contentPane);
	}
	
	protected void addRegular(Container contentPane){
		regular= new JRadioButton("Regular");
		regular.setActionCommand("Regular");
		regular.setSelected(true);
		
		JRadioButton transparent = new JRadioButton("Transparent");
		transparent.setActionCommand("Transparent");
		transparent.setSelected(true);
		
		 ButtonGroup group = new ButtonGroup();
		 group.add(regular);
		 group.add(transparent);
		 contentPane.add(regular);
		 contentPane.add(transparent);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		IncompleteBuchiActionStateCreation s=new IncompleteBuchiActionStateCreation(e.getSource(), e.getID(), e.getActionCommand(), textField.getText(), regular.isSelected(), initial.isSelected(), accepting.isSelected());
		actionListener.actionPerformed(s);
		IncompleteBuchiAutomatonJStateCreator.this.dispatchEvent(new WindowEvent(
				IncompleteBuchiAutomatonJStateCreator.this, WindowEvent.WINDOW_CLOSING));
	}
	

}
