package it.polimi.view.incompleteautomaton;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.view.buchiautomaton.BuchiAutomatonLoadingJPanel;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.xml.bind.JAXBException;

@SuppressWarnings("serial")
public class IncompleteBuchiAutomatonLoadingPanel<S extends State, T extends Transition<S>, A extends BuchiAutomaton<S,T>> extends BuchiAutomatonLoadingJPanel<S,T,A>{

	public IncompleteBuchiAutomatonLoadingPanel(Dimension d, ActionListener container) throws JAXBException{
		super(d,container);
	}
	public JPanel createButtonPanel(Dimension buttonPanelDimension, ActionListener container){
		return new IncompleteBuchiButtonJPanel(buttonPanelDimension, container);
	}
}
