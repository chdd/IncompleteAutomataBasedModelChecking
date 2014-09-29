package it.polimi.view.buchiautomaton;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.modelchecker.ModelCheckerParameters;
import it.polimi.view.automaton.AutomatonLoadingPanel;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.xml.bind.JAXBException;

@SuppressWarnings("serial")
public class BuchiAutomatonLoadingJPanel<S extends State, T extends Transition<S>, A extends BuchiAutomaton<S,T>> extends AutomatonLoadingPanel {

	
	public BuchiAutomatonLoadingJPanel(Dimension d, ActionListener container) throws JAXBException{
		 super(d, container);
		 Dimension buttonPanelDimension=new Dimension(d.width, d.height/4);
		 JPanel buttonPanel=this.createButtonPanel(buttonPanelDimension, container);
		 this.add(buttonPanel);
		 
		 if(buttonPanel instanceof BuchiButtonJPanel){
			 ((BuchiButtonJPanel) buttonPanel).setXMLarea(xmlArea);
		 }
		
	}
	
	public JPanel createButtonPanel(Dimension buttonPanelDimension, ActionListener container){
		return new BuchiButtonJPanel(buttonPanelDimension, container);
	}
	
	public void update(A a){
		this.xmlArea.update(a.toXMLString());
	}
		
	public void updateResults(@SuppressWarnings("rawtypes") ModelCheckerParameters results){
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
