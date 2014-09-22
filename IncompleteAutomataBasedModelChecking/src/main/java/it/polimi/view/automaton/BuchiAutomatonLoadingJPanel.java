package it.polimi.view.automaton;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.modelchecker.ModelCheckerParameters;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.xml.bind.JAXBException;

@SuppressWarnings("serial")
public class BuchiAutomatonLoadingJPanel<S extends State, T extends Transition<S>, A extends BuchiAutomaton<S,T>> extends JPanel {

	private BuchiAutomatonXMLTextArea<S,T,A> xmlArea;
	public BuchiAutomatonLoadingJPanel(Dimension d, ActionListener container) throws JAXBException{
		 super();
		 this.setBackground(Color.getColor("myColor"));
		 this.setSize(d);
		 this.setMinimumSize(d);
		 this.setMaximumSize(d);
		 this.setPreferredSize(d);
		 
		 this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		 Dimension buttonPanelDimension=new Dimension(d.width, d.height/4);
		 BuchiButtonJPanel buttonPanel=this.createButtonPanel(buttonPanelDimension, container);
		 this.add(buttonPanel);
		 
		 Dimension xmlAreaDimension=new Dimension(d.width, d.height/4*3);
		 xmlArea=new BuchiAutomatonXMLTextArea<S,T,A>(xmlAreaDimension);
		 this.add(xmlArea);
	}
	
	public BuchiButtonJPanel createButtonPanel(Dimension buttonPanelDimension, ActionListener container){
		return new BuchiButtonJPanel(buttonPanelDimension, container);
	}
	
	public void update(A a){
		this.xmlArea.update(a);
	}
		
	public void updateResults(ModelCheckerParameters results){
		if(results.getResult()==1){
			this.xmlArea.setText("The property is satisfied");
		}
		if(results.getResult()==0){
			this.xmlArea.setText("The property is not satisfied");
		}
		if(results.getResult()==-1){
			this.xmlArea.setText("The property is possibly satisfied\n\n");
			this.xmlArea.setText(this.xmlArea.getText()+"Constraint:\n"+results.getConstraint());
		}

	}

	public String getAutomatonXML() {
		return this.xmlArea.getText();
		
	}
}
