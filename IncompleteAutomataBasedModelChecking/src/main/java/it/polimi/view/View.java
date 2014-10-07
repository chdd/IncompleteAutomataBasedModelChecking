package it.polimi.view;

import it.polimi.controller.actions.file.loading.LoadModel;
import it.polimi.controller.actions.file.loading.LoadSpecification;
import it.polimi.controller.actions.file.saving.SaveModel;
import it.polimi.controller.actions.file.saving.SaveSpecification;
import it.polimi.model.automata.ba.BuchiAutomaton;
import it.polimi.model.automata.ba.state.State;
import it.polimi.model.automata.ba.transition.ConstrainedTransition;
import it.polimi.model.automata.ba.transition.LabelledTransition;
import it.polimi.model.automata.iba.IncompleteBuchiAutomaton;
import it.polimi.model.automata.intersection.IntersectionAutomaton;
import it.polimi.model.automata.intersection.IntersectionState;
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
import java.awt.event.KeyEvent;
import java.util.Observable;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.xml.bind.JAXBException;

public class View<S1 extends State, T1 extends LabelledTransition, S extends IntersectionState<S1>, T extends ConstrainedTransition<S1>> extends Observable implements ViewInterface<S1,T1,S,T>, ActionListener{

	private IncompleteBuchiAutomatonManagementJPanel<S1,T1, IncompleteBuchiAutomaton<S1, T1>>  modelPanel;
	private BuchiAutomatonManagementJPanel<S1,T1, BuchiAutomaton<S1, T1>>  specificationPanel;
	private IntersectionAutomatonManagementJPanel<S1, T1, S, T, IntersectionAutomaton<S1, T1, S, T>> intersectionPanel;
	private Dimension panelDimensions;
	private JFrame jframe;
	public View() throws JAXBException {
		
		
		 jframe=new JFrame();
		 
		 
		 this.createMenuBar(jframe);
		 System.setProperty("myColor", "0XAABBCC");
		 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		 jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 jframe.getContentPane().setBackground(Color.getColor("myColor"));
		 jframe.setSize(screenSize);
		 Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(jframe.getGraphicsConfiguration());
		
		 Dimension contentPanelSize=new Dimension(screenSize.width - scnMax.left -scnMax.right , screenSize.height - scnMax.bottom -scnMax.top - this.modelMenu.getHeight());	
			
		 System.out.println(this.modelMenu.getHeight());
		 panelDimensions=new Dimension(contentPanelSize.width/2, contentPanelSize.height/2);
		 
		 
		 
		 JPanel container = new JPanel();
		 JScrollPane scrPane = new JScrollPane(container);
		 jframe.add(scrPane);
		 
		 Dimension automatonManagementPanelDimension=new Dimension(contentPanelSize.width/2, contentPanelSize.height);
		 
		 container.setLayout(new BoxLayout(container,BoxLayout.X_AXIS));
		
		 
		 JPanel c = new JPanel();
		 c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));
		 IncompleteAutomatonFactory<S1, T1, IncompleteBuchiAutomaton<S1,T1>> factoryIncompleteBa=
				 new IncompleteAutomatonFactory<S1, T1, IncompleteBuchiAutomaton<S1,T1>>();
		 
		 this.modelPanel=
				 factoryIncompleteBa.getPanel(panelDimensions);
		 c.add(modelPanel);
		 this.modelPanel.setActionListener(this);
		 c.setVisible(true);
		 
		 BuchiAutomatonFactory<S1,T1, BuchiAutomaton<S1, T1>> factoryBa=new BuchiAutomatonFactory<S1,T1, BuchiAutomaton<S1, T1>>();
		 this.specificationPanel=
				 factoryBa.getPanel(panelDimensions);
		 this.specificationPanel.setActionListener(this);
		 c.add(specificationPanel);
		 
		 container.add(c);
		 
		 
		
		 
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
		
		if(e.getSource().equals(this.specificationSaveItem)){
			this.notifyObservers(new SaveSpecification(e.getSource(), e.getID(), e.getActionCommand()));
		}
		else{
			if(e.getSource().equals(this.specificationOpenItem)){
				this.notifyObservers(new LoadSpecification(e.getSource(), e.getID(), e.getActionCommand()));
			}
			else{
				if(e.getSource().equals(this.modelSaveItem)){
					this.notifyObservers(new SaveModel(e.getSource(), e.getID(), e.getActionCommand()));
				}
				else{
					if(e.getSource().equals(this.modelOpenItem)){
						this.notifyObservers(new LoadModel(e.getSource(), e.getID(), e.getActionCommand()));
					}
					else{
						this.notifyObservers(e);
					}
				}
			}
		}
	}

	public void displayErrorMessage(String message){
		JOptionPane.showMessageDialog(null, message);
	}
	
	private JMenuBar menuBar;
	private JMenu modelMenu;
	private JMenuItem modelOpenItem;
	private JMenuItem modelSaveItem;
	private JMenuItem specificationOpenItem;
	private JMenuItem specificationSaveItem;
	
	private void createMenuBar(JFrame jframe){
		menuBar=new JMenuBar();
		modelMenu=new JMenu("File");
		modelMenu.addActionListener(this);
		
		modelOpenItem = new JMenuItem("Open Model", KeyEvent.VK_T);
		modelMenu.add(modelOpenItem);
		modelOpenItem.setMnemonic(KeyEvent.VK_O);
		modelOpenItem.addActionListener(this);
		
		modelSaveItem = new JMenuItem("Save Model", KeyEvent.VK_T);
		modelMenu.add(modelSaveItem);
		modelSaveItem.setMnemonic(KeyEvent.VK_O);
		modelSaveItem.addActionListener(this);
		
		
		specificationOpenItem = new JMenuItem("Open Specification", KeyEvent.VK_T);
		modelMenu.add(specificationOpenItem);
		specificationOpenItem.setMnemonic(KeyEvent.VK_O);
		specificationOpenItem.addActionListener(this);
		
		
		specificationSaveItem = new JMenuItem("Save Specification", KeyEvent.VK_T);
		modelMenu.add(specificationSaveItem);
		specificationSaveItem.setMnemonic(KeyEvent.VK_O);
		specificationSaveItem.addActionListener(this);
		
		
		
		menuBar.add(modelMenu);
		jframe.setJMenuBar(menuBar);
		menuBar.setVisible(true);
		
	}
}
