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
import it.polimi.view.automaton.BuchiAutomatonJPanel;
import it.polimi.view.automaton.IncompleteBuchiAutomatonJPanel;
import it.polimi.view.intersectionautomaton.IntersectionAutomatonJPanel;

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
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.xml.bind.JAXBException;

public class View<S1 extends State, T1 extends LabelledTransition, S extends IntersectionState<S1>, T extends ConstrainedTransition<S1>> extends Observable implements ViewInterface<S1,T1,S,T>, ActionListener{

	private IncompleteBuchiAutomatonJPanel<S1,T1,IncompleteBuchiAutomaton<S1,T1>>  modelPanel;
	private BuchiAutomatonJPanel<S1,T1, BuchiAutomaton<S1, T1>>  specificationPanel;
	private IntersectionAutomatonJPanel<S1, T1, S, T,IntersectionAutomaton<S1, T1, S, T>> intersectionPanel;
	private Dimension panelDimensions;
	private JFrame jframe;
	private ResultsJPanel<S1, T1, S, T,IntersectionAutomaton<S1, T1, S, T>> resultsJPanel;
	
	private static int margin=10;
	
	public View(IncompleteBuchiAutomaton<S1, T1> model,
			BuchiAutomaton<S1, T1> specification,
			IntersectionAutomaton<S1, T1,S,T> intersection) throws JAXBException {
		
		
		 jframe=new JFrame();
		 
		 
		 this.createMenuBar(jframe);
		 System.setProperty("myColor", "0XAABBCC");
		 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		 jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 jframe.getContentPane().setBackground(Color.getColor("myColor"));
		 jframe.setSize(screenSize);
		 Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(jframe.getGraphicsConfiguration());
		
		 Dimension contentPanelSize=new Dimension(screenSize.width - scnMax.left -scnMax.right , screenSize.height - scnMax.bottom -scnMax.top - this.modelMenu.getHeight());	
			
		 panelDimensions=new Dimension(contentPanelSize.width/2, contentPanelSize.height/2);
		 
		 
		 
		 JPanel container = new JPanel();
		 JScrollPane scrPane = new JScrollPane(container);
		 jframe.add(scrPane);
		 
		 Dimension automatonManagementPanelDimension=new Dimension(contentPanelSize.width/2, contentPanelSize.height);
		 
		 container.setLayout(new BoxLayout(container,BoxLayout.X_AXIS));
		
		 
		 JPanel container1=new JPanel();
		 container1.setLayout(new BoxLayout(container1,BoxLayout.Y_AXIS));
		
		 JLabel modelLabel=new JLabel("Model");
		 container1.add(modelLabel);
		 this.modelPanel=new IncompleteBuchiAutomatonJPanel<S1,T1,IncompleteBuchiAutomaton<S1,T1>>(panelDimensions, model, this);
		 container1.add(modelPanel);
		 
		 JLabel specificationLabel=new JLabel("Specification");
		 container1.add(specificationLabel);
		 	
		 this.specificationPanel=new BuchiAutomatonJPanel<S1,T1, BuchiAutomaton<S1, T1>>(panelDimensions, specification, this);
		 container1.add(specificationPanel);
		 
		 container.add(container1);
		 
		 JPanel container2=new JPanel();
		 container2.setLayout(new BoxLayout(container2,BoxLayout.Y_AXIS));
		 
		 JLabel intersectionLabel=new JLabel("Intersection Automaton");
		 container2.add(intersectionLabel);
		 this.intersectionPanel=new IntersectionAutomatonJPanel<S1, T1, S, T,IntersectionAutomaton<S1, T1, S, T>>(
				 new Dimension(automatonManagementPanelDimension.width,
						 automatonManagementPanelDimension.height/4*3 -margin)
				 , intersection, this);
		 container2.add(intersectionPanel);
			
		 JLabel resultsLabel=new JLabel("Model Checking results");
		 container2.add(resultsLabel);
		 
		 this.resultsJPanel=new ResultsJPanel<S1, T1, S, T,IntersectionAutomaton<S1, T1, S, T>>(
				 new Dimension(automatonManagementPanelDimension.width,
						 automatonManagementPanelDimension.height/4*1 -margin)
				 );
		 container2.add(resultsJPanel);
			
		 container.add(container2);
		 container.setVisible(true);
		 container.setVisible(true);
		 jframe.add(container);
		 jframe.setResizable(true);
		 jframe.setVisible(true);
		 

	
	}
	
	@Override
	public void updateModel(IncompleteBuchiAutomaton<S1, T1> model){
		this.modelPanel.update(model);
	}
	@Override
	public void updateSpecification(BuchiAutomaton<S1, T1> specification){
		this.specificationPanel.update(specification);
	}

	@Override
	public void updateIntersection(IntersectionAutomaton<S1, T1,S,T> intersection){
		this.intersectionPanel.update(intersection);
	}

	@Override
	public void updateVerificationResults(ModelCheckerParameters<S1> verificationResults) {
		this.resultsJPanel.updateResults(verificationResults);
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
						System.out.println("notify open model");
						this.notifyObservers(new LoadModel(e.getSource(), e.getID(), e.getActionCommand()));
					}
					else{
						if(e.getSource().equals(this.editItem)){
							this.modelPanel.setEditingMode();
							this.specificationPanel.setEditingMode();
						}
						else{
							if(e.getSource().equals(this.trasformItem)){
								this.modelPanel.setTranformingMode();
								this.specificationPanel.setTranformingMode();
							}
							else{
								
								this.notifyObservers(e);
							}
						}
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
	private JMenu editMenu;
	
	private JMenuItem modelOpenItem;
	private JMenuItem editItem;
	
	private JMenuItem trasformItem;
	
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
		
		editMenu=new JMenu("Project");
		editMenu.addActionListener(this);
		
		editItem = new JMenuItem("Edit", KeyEvent.VK_T);
		editMenu.add(editItem);
		editItem.setMnemonic(KeyEvent.VK_O);
		editItem.addActionListener(this);
		
		trasformItem = new JMenuItem("Trasform", KeyEvent.VK_T);
		editMenu.add(trasformItem);
		trasformItem.setMnemonic(KeyEvent.VK_O);
		trasformItem.addActionListener(this);
		
		menuBar.add(editMenu);


		
		
		jframe.setJMenuBar(menuBar);
		menuBar.setVisible(true);
		
	}
}
