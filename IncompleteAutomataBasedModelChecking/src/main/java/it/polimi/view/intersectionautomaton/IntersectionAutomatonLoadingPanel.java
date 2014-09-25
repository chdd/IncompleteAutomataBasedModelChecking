package it.polimi.view.intersectionautomaton;

import it.polimi.model.IntersectionAutomaton;
import it.polimi.model.IntersectionState;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.view.incompleteautomaton.IncompleteBuchiAutomatonLoadingPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.xml.bind.JAXBException;

@SuppressWarnings("serial")
public class IntersectionAutomatonLoadingPanel<S extends State, T extends Transition<S>, S1 extends IntersectionState<S>, T1 extends Transition<S1>, A  extends IntersectionAutomaton<S, T, S1, T1>> extends
		IncompleteBuchiAutomatonLoadingPanel<S1, T1, A> {

	public IntersectionAutomatonLoadingPanel(Dimension d,
			ActionListener container) throws JAXBException {
		super(d, container);
	}
	
	public JPanel createButtonPanel(Dimension buttonPanelDimension, ActionListener container){
		JPanel ret=new JPanel();
		JLabel label=new JLabel();
		ret.setBackground(Color.getColor("myColor"));
		ret.setSize(buttonPanelDimension);
		ret.setMinimumSize(buttonPanelDimension);
		ret.setMaximumSize(buttonPanelDimension);
		ret.setPreferredSize(buttonPanelDimension);
		 Dimension buttonDimension=new Dimension(buttonPanelDimension.width/2/4, buttonPanelDimension.height/2/5);
		label.setSize(buttonDimension);
		label.setForeground(Color.white);
		label.setFont(new Font("Verdana", Font.BOLD, 32));
		label.setText("Verification results");
		ret.add(label);
		
		return ret;
	}

}
