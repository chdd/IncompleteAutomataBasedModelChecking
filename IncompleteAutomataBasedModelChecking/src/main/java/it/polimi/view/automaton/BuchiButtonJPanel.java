package it.polimi.view.automaton;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;


public class BuchiButtonJPanel  extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BuchiButtonJPanel(Dimension d){
		 super();
		 this.setBackground(Color.getColor("myColor"));
		 this.setSize(d);
		 this.setMinimumSize(d);
		this.setMaximumSize(d);
		this.setPreferredSize(d);
		 Dimension buttonDimension=new Dimension(d.width/2/4, d.height/2/5);
		 JButton btnInsert = new JButton("Update Automaton");
		 btnInsert.setSize(buttonDimension);
		 btnInsert.setOpaque(true);
		 btnInsert.setLocation(d.width/2, d.height/3/5/20);
		 btnInsert.setVisible(true);
		 btnInsert.setBackground(Color.getColor("myColor"));
		// this.addAcationListener(btnInsert);
		 this.add(btnInsert);
		 
		 JButton btnLoad = new JButton("Open");
		 btnLoad.setSize(buttonDimension);
		 btnLoad.setOpaque(true);
		 btnLoad.setLocation(d.width/2+buttonDimension.width, d.height/2/5/20);
		 btnLoad.setVisible(true);
		 btnLoad.setBackground(Color.getColor("myColor"));
		// this.addAcationListener(btnLoad);
		 this.add(btnLoad);
		 
		 JButton btnSave = new JButton("Save");
		 btnSave.setSize(buttonDimension);
		 btnSave.setOpaque(true);
		 btnSave.setLocation(d.width/2+(buttonDimension.width*2), d.height/2/5/20);
		 btnSave.setVisible(true);
		 btnSave.setBackground(Color.getColor("myColor"));
		// this.addAcationListener(btnSave);
		 this.add(btnSave);
	}
}
