package it.polimi.view.automaton;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.xml.bind.JAXBException;

@SuppressWarnings("serial")
public class BuchiAutomatonManagementJPanel<S extends State, T extends Transition<S>, A extends BuchiAutomaton<S, T>> extends JPanel {

	protected BuchiAutomatonLoadingJPanel<S, T, A> loadingPanel;
	protected BuchiAutomatonJPanel<S,T, A> automatonPanel;
	
	public BuchiAutomatonManagementJPanel(){
		super(new FlowLayout());
	}
	
	protected void addGraph(){
		
	}
	public BuchiAutomatonManagementJPanel(Dimension d, ActionListener container) throws JAXBException{
		
		 super();
		 this.setBackground(Color.getColor("myColor"));
		 this.setSize(d);
		 this.setMinimumSize(d);
		 this.setMaximumSize(d);
		 this.setPreferredSize(d);
		 this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		 
		 Dimension automatonJPanelDimension=new Dimension(d.width/2, d.height);
		 automatonPanel=this.getAutomatonPanel(automatonJPanelDimension);
		 this.add(automatonPanel);
		 this.addLoadingPanel(automatonJPanelDimension, container);
	}
	
	protected void addLoadingPanel(Dimension d, ActionListener container) throws JAXBException{
		 Dimension automatonLoadingPanelDimension=new Dimension(d.width, d.height);
		 loadingPanel=new BuchiAutomatonLoadingJPanel<S,T,A>(automatonLoadingPanelDimension, container);
		 this.add(loadingPanel);
	}
	
	public void updateAutomatonPanel(A a) throws JAXBException{
		this.automatonPanel.update(a);
		
	}
	public void updateLoadingPanel(A a) throws JAXBException{
		this.loadingPanel.update(a);
	}
	
	protected BuchiAutomatonJPanel<S,T, A> getAutomatonPanel(Dimension automatonJPanelDimension){
		 return new  BuchiAutomatonJPanel<S,T, A>(automatonJPanelDimension);
	}
	public String getAutomatonXML() {
		return this.loadingPanel.getAutomatonXML();
	}
}
