package it.polimi.view.automaton;

import it.polimi.view.Actions;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class BuchiButtonJPanel  extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected JLabel label;
	protected JButton btnLoad;
	protected JButton btnSave;
	public BuchiButtonJPanel(Dimension d, ActionListener container){
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
		 
		 btnLoad = new JButton("Open");
		 btnLoad.setSize(buttonDimension);
		 btnLoad.setOpaque(true);
		 btnLoad.setLocation(d.width/2+buttonDimension.width, d.height/2/5/20);
		 btnLoad.setVisible(true);
		 btnLoad.setBackground(Color.getColor("myColor"));
		 btnLoad.addActionListener(container);
		 this.add(btnLoad);
		 this.setLoadActionCommand();
		 
		 btnSave = new JButton("Save");
		 btnSave.setSize(buttonDimension);
		 btnSave.setOpaque(true);
		 btnSave.setLocation(d.width/2+(buttonDimension.width*2), d.height/2/5/20);
		 btnSave.setVisible(true);
		 btnSave.setBackground(Color.getColor("myColor"));
		 btnSave.addActionListener(container);
		 this.add(btnSave);
		 this.setSaveActionCommand();
	}
	protected void setLabelText(){
		this.label.setText("Specification");
	}
	
	protected void setLoadActionCommand(){
		btnLoad.setActionCommand(Actions.LOADSPECIFICATION.name());
	}
	protected void setSaveActionCommand(){
		btnSave.setActionCommand(Actions.SAVESPECIFICATION.name());
	}
}
