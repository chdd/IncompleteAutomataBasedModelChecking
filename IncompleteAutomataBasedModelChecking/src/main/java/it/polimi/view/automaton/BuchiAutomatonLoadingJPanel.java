package it.polimi.view.automaton;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.xml.bind.JAXBException;

@SuppressWarnings("serial")
public class BuchiAutomatonLoadingJPanel<S extends State, T extends Transition<S>, A extends BuchiAutomaton<S,T>> extends JPanel {

	private BuchiAutomatonXMLTextArea<S,T,A> xmlArea;
	public BuchiAutomatonLoadingJPanel(Dimension d) throws JAXBException{
		 super();
		 this.setBackground(Color.getColor("myColor"));
		 this.setSize(d);
		 this.setMinimumSize(d);
		 this.setMaximumSize(d);
		 this.setPreferredSize(d);
		 
		 this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		 Dimension buttonPanelDimension=new Dimension(d.width, d.height/4);
		 BuchiButtonJPanel buttonPanel=new BuchiButtonJPanel(buttonPanelDimension);
		 this.add(buttonPanel);
		 
		 Dimension xmlAreaDimension=new Dimension(d.width, d.height/4*3);
		 xmlArea=new BuchiAutomatonXMLTextArea<S,T,A>(xmlAreaDimension);
		 this.add(xmlArea);
	}
	
	public void update(A a) throws JAXBException{
		this.xmlArea.update(a);
	}
		
}
