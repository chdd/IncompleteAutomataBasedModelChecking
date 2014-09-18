package it.polimi.view;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.IncompleteBuchiAutomaton;
import it.polimi.model.IntersectionAutomaton;
import it.polimi.model.IntersectionState;
import it.polimi.model.State;
import it.polimi.model.Transition;
import it.polimi.modelchecker.ModelCheckerParameters;
import it.polimi.view.automaton.BuchiAutomatonManagementJPanel;
import it.polimi.view.incompleteautomaton.IncompleteBuchiAutomatonManagementJPanel;
import it.polimi.view.intersectionautomaton.IntersectionAutomatonManagementJPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.xml.bind.JAXBException;

@SuppressWarnings("serial")
public class View<S1 extends State, T1 extends Transition<S1>, S extends IntersectionState<S1>, T extends Transition<S>> extends JFrame implements ViewInterface{

	private IncompleteBuchiAutomatonManagementJPanel<S1,T1, IncompleteBuchiAutomaton<S1, T1>>  modelPanel;
	private BuchiAutomatonManagementJPanel<S1,T1, BuchiAutomaton<S1, T1>>  specificationPanel;
	private IntersectionAutomatonManagementJPanel<S1, T1, S, T, IntersectionAutomaton<S1, T1, S, T>> intersectionPanel;
	public View() throws JAXBException{
		 System.setProperty("myColor", "0XAABBCC");
		 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 this.getContentPane().setBackground(Color.getColor("myColor"));
		 this.setSize(screenSize);
		 Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
		 
		 Dimension contentPanelSize=new Dimension(screenSize.width - scnMax.left -scnMax.right, screenSize.height - scnMax.bottom -scnMax.top);	
		 
		 JPanel container = new JPanel();
		 JScrollPane scrPane = new JScrollPane(container);
		 add(scrPane);
		 
		 Dimension automatonManagementPanelDimension=new Dimension(contentPanelSize.width, contentPanelSize.height/3);
		 
		 container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));
		
		 modelPanel=new  IncompleteBuchiAutomatonManagementJPanel<S1,T1, IncompleteBuchiAutomaton<S1, T1>> (automatonManagementPanelDimension);
		 modelPanel.setSize(automatonManagementPanelDimension);
		 modelPanel.setMinimumSize(automatonManagementPanelDimension);
		 modelPanel.setMaximumSize(automatonManagementPanelDimension);
		 modelPanel.setPreferredSize(automatonManagementPanelDimension);
		 container.add(modelPanel);
		 container.setVisible(true);
		 
		 specificationPanel= new  BuchiAutomatonManagementJPanel<S1,T1, BuchiAutomaton<S1, T1>> (automatonManagementPanelDimension);
		 specificationPanel.setSize(automatonManagementPanelDimension);
		 specificationPanel.setMinimumSize(automatonManagementPanelDimension);
		 specificationPanel.setMaximumSize(automatonManagementPanelDimension);
		 specificationPanel.setPreferredSize(automatonManagementPanelDimension);
		 container.add(specificationPanel);
		 container.setVisible(true);
		 
		
		intersectionPanel=
				new IntersectionAutomatonManagementJPanel<S1, T1,S, T, IntersectionAutomaton<S1, T1, S, T>>(automatonManagementPanelDimension);
		 intersectionPanel.setSize(automatonManagementPanelDimension);
		 intersectionPanel.setMinimumSize(automatonManagementPanelDimension);
		 intersectionPanel.setMaximumSize(automatonManagementPanelDimension);
		 intersectionPanel.setPreferredSize(automatonManagementPanelDimension);
		 container.add(intersectionPanel);
		 container.setVisible(true);
		 this.add(container);
		 this.setResizable(true);
		 this.setVisible(true);
	
	}
	
	@Override
	public void updateModel(IncompleteBuchiAutomaton model) throws JAXBException {
		this.modelPanel.updateAutomatonPanel(model);
		this.modelPanel.updateLoadingPanel(model);
		
	}
	@Override
	public void updateSpecification(BuchiAutomaton specification) throws JAXBException {
		this.specificationPanel.updateAutomatonPanel(specification);
		this.specificationPanel.updateLoadingPanel(specification);
	}

	@Override
	public void updateIntersection(IntersectionAutomaton intersection) throws JAXBException {
		this.intersectionPanel.updateAutomatonPanel(intersection);
	}

	@Override
	public void updateVerificationResults(ModelCheckerParameters verificationResults) {
		this.intersectionPanel.updateVerificationResults(verificationResults);
		
	}
	
	

}
