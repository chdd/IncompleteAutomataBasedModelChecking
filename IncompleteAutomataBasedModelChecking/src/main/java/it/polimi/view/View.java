package it.polimi.view;

import it.polimi.model.BuchiAutomaton;
import it.polimi.model.IncompleteBuchiAutomaton;
import it.polimi.model.IntersectionAutomaton;
import it.polimi.model.IntersectionState;
import it.polimi.model.State;
import it.polimi.model.LabelledTransition;
import it.polimi.modelchecker.ModelCheckerParameters;
import it.polimi.view.buchiautomaton.BuchiAutomatonManagementJPanel;
import it.polimi.view.factories.BuchiAutomatonFactory;
import it.polimi.view.factories.IncompleteAutomatonFactory;
import it.polimi.view.factories.IntersectionAutomatonFactory;
import it.polimi.view.incompleteautomaton.IncompleteBuchiAutomatonManagementJPanel;
import it.polimi.view.intersectionautomaton.IntersectionAutomatonManagementJPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.xml.bind.JAXBException;

public class View<S1 extends State, T1 extends LabelledTransition<S1>, S extends IntersectionState<S1>, T extends LabelledTransition<S>> extends Observable implements ViewInterface<S1,T1,S,T>, ActionListener{

	private IncompleteBuchiAutomatonManagementJPanel<S1,T1, IncompleteBuchiAutomaton<S1, T1>>  modelPanel;
	private BuchiAutomatonManagementJPanel<S1,T1, BuchiAutomaton<S1, T1>>  specificationPanel;
	private IntersectionAutomatonManagementJPanel<S1, T1, S, T, IntersectionAutomaton<S1, T1, S, T>> intersectionPanel;
	private JFrame jframe;
	public View() throws JAXBException {
		 jframe=new JFrame();
		 
		 System.setProperty("myColor", "0XAABBCC");
		 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		 jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 jframe.getContentPane().setBackground(Color.getColor("myColor"));
		 jframe.setSize(screenSize);
		 Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(jframe.getGraphicsConfiguration());
		 
		 Dimension contentPanelSize=new Dimension(screenSize.width - scnMax.left -scnMax.right, screenSize.height - scnMax.bottom -scnMax.top);	
		 
		 JPanel container = new JPanel();
		 JScrollPane scrPane = new JScrollPane(container);
		 jframe.add(scrPane);
		 
		 Dimension automatonManagementPanelDimension=new Dimension(contentPanelSize.width, contentPanelSize.height/3);
		 
		 container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));
		
		 
		 IncompleteAutomatonFactory<S1, T1, IncompleteBuchiAutomaton<S1,T1>> factoryIncompleteBa=
				 new IncompleteAutomatonFactory<S1, T1, IncompleteBuchiAutomaton<S1,T1>>();
		 
		 this.modelPanel=
				 factoryIncompleteBa.getPanel(automatonManagementPanelDimension);
		 container.add(modelPanel);
		 this.modelPanel.setActionListener(this);
		 container.setVisible(true);
		 
		 BuchiAutomatonFactory<S1,T1, BuchiAutomaton<S1, T1>> factoryBa=new BuchiAutomatonFactory<S1,T1, BuchiAutomaton<S1, T1>>();
		 this.specificationPanel=
				 factoryBa.getPanel(automatonManagementPanelDimension);
		 this.specificationPanel.setActionListener(this);
		 container.add(specificationPanel);
		 
		 
		
		 
		 IntersectionAutomatonFactory<S1, T1, S, T, IntersectionAutomaton<S1, T1, S, T>> factoryIntersection=
				 new  IntersectionAutomatonFactory<S1, T1, S, T, IntersectionAutomaton<S1, T1, S, T>>();
		
		 this.intersectionPanel=factoryIntersection.getPanel(automatonManagementPanelDimension);
		
		 container.add(intersectionPanel);
		 container.setVisible(true);
		 container.setVisible(true);
		 jframe.add(container);
		 jframe.setResizable(true);
		 jframe.setVisible(true);
		 

	
	}
	
	@Override
	public void updateModel(IncompleteBuchiAutomaton<S1, T1> model){
		this.modelPanel.updateAutomatonPanel(model);
		this.modelPanel.updateLoadingPanel(model);
		
		jframe.repaint();
	}
	@Override
	public void updateSpecification(BuchiAutomaton<S1, T1> specification){
		this.specificationPanel.updateAutomatonPanel(specification);
		this.specificationPanel.updateLoadingPanel(specification);
		
		jframe.repaint();
		
	}

	@Override
	public void updateIntersection(IntersectionAutomaton<S1, T1,S,T> intersection){
		this.intersectionPanel.updateAutomatonPanel(intersection);
		jframe.repaint();
		
	}

	@Override
	public void updateVerificationResults(ModelCheckerParameters<S1> verificationResults) {
		this.intersectionPanel.updateVerificationResults(verificationResults);
		jframe.repaint();
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.setChanged();
		this.notifyObservers(e);
	}

	public void displayErrorMessage(String message){
		JOptionPane.showMessageDialog(null, message);
	}
	
}
