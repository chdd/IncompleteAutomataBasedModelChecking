package it.polimi.view;

import it.polimi.controller.actions.CheckAction;
import it.polimi.controller.actions.LoadClaimAction;
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
import it.polimi.view.automaton.IntersectionAutomatonJPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Observable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

public class View<S1 extends State, T1 extends LabelledTransition, S extends IntersectionState<S1>, T extends ConstrainedTransition<S1>> extends Observable implements ViewInterface<S1,T1,S,T>, ActionListener{

	private BuchiAutomatonJPanel<S1,T1, BuchiAutomaton<S1, T1>>  specificationPanelResults;
	private BuchiAutomatonJPanel<S1,T1, BuchiAutomaton<S1, T1>>  specificationPanel;
	
	private JTextArea claimArea;
	private JButton loadClaim;
	
	private IntersectionAutomatonJPanel<S1, T1, S, T,IntersectionAutomaton<S1, T1, S, T>> verificationPanelIntersection;
	private IntersectionAutomatonJPanel<S1, T1, S, T,IntersectionAutomaton<S1, T1, S, T>> intersectionPanelIntersection;
	
	private TextArea brzozowskiSystem;
	
	private JFrame jframe;
	private ResultsJPanel<S1, T1, S, T,IntersectionAutomaton<S1, T1, S, T>> resultsJPanel;
	
	
	private IncompleteBuchiAutomatonJPanel<S1,T1,IncompleteBuchiAutomaton<S1,T1>> modelTabmodel;
	private IncompleteBuchiAutomatonJPanel<S1,T1,IncompleteBuchiAutomaton<S1,T1>> verificationPanelmodel;
		
	
	public View(IncompleteBuchiAutomaton<S1, T1> model,
			BuchiAutomaton<S1, T1> specification,
			IntersectionAutomaton<S1, T1,S,T> intersection) {
		
		
		 this.jframe=new JFrame();
		 // setting the size of the jframe
		 this.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 this.jframe.getContentPane().setBackground(Color.getColor("myColor"));
		 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		 this.jframe.setSize(screenSize);
		 this.jframe.getContentPane();
		
		 
		 // creating the menu bar
		 this.createMenuBar(jframe);
		 
		 JTabbedPane tabbedPane = new JTabbedPane();
		 tabbedPane.setSize(this.jframe.getContentPane().getSize());
		 tabbedPane.setPreferredSize(this.jframe.getContentPane().getSize());
		 
		 JComponent modelTab = makeTextPanel("Model");
		 tabbedPane.addTab("Model",modelTab);
		 tabbedPane.setMnemonicAt(0, KeyEvent.VK_2);
		
		 JComponent specificationTab = makeTextPanel("Claim");
		 tabbedPane.addTab("Claim", specificationTab);
		 tabbedPane.setMnemonicAt(1, KeyEvent.VK_3);
		 
		 
		 JComponent verificationResults = makeTextPanel("Verification Results");
		 tabbedPane.addTab("Verification Results", verificationResults);
		 tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
		 
		 JComponent verificationSnapshot = makeTextPanel("Verification Snapshot");
		 tabbedPane.addTab("Verification Snapshot", verificationSnapshot);
		 tabbedPane.setMnemonicAt(3, KeyEvent.VK_1);
		 verificationSnapshot.setLayout(new BoxLayout(verificationSnapshot,BoxLayout.X_AXIS));
		 
		 this.jframe.add(tabbedPane);
		 
		 		
		 JPanel container = new JPanel();
		 container.setAutoscrolls(false);
		 
		 JScrollPane scrPane = new JScrollPane(container);
		 jframe.add(scrPane);
		 
		 
		 container.setLayout(new BoxLayout(container,BoxLayout.X_AXIS));
		 container.add(tabbedPane);
		 
		 JPanel container1=new JPanel();
		 container1.setLayout(new BoxLayout(container1,BoxLayout.Y_AXIS));
		
		 
		 
		 // claim tab
		 specificationTab.setLayout(new BoxLayout(specificationTab,BoxLayout.Y_AXIS));
		 specificationTab.add(new JLabel("Claim"));
		 JPanel containerClaim=new JPanel();
		 containerClaim.setLayout(new FlowLayout(FlowLayout.CENTER));
		 claimArea=new JTextArea();
		 claimArea.setLineWrap(true);
	       
	        //
	        // Filter a new line, pressing enter key will be replaced
	        // with a space instead.
	        //
		 claimArea.getDocument().putProperty("filterNewlines",
	                Boolean.TRUE);
		 claimArea.setRows(1);
		 containerClaim.add(claimArea);
			
		 loadClaim=new JButton("Convert LTL BA");
		 containerClaim.add(loadClaim);
		 loadClaim.addActionListener(this);
		 specificationTab.add(containerClaim);
		 
		 specificationPanel=new BuchiAutomatonJPanel<S1,T1, BuchiAutomaton<S1, T1>>(specification, this);
		 specificationTab.add(specificationPanel);
		 
		 
		 
		 // model tab
		 JLabel modelLabel=new JLabel("Model");
		 container1.add(modelLabel);
		 this.modelTabmodel=new IncompleteBuchiAutomatonJPanel<S1,T1,IncompleteBuchiAutomaton<S1,T1>>(model, this);
		 this.verificationPanelmodel=new IncompleteBuchiAutomatonJPanel<S1,T1,IncompleteBuchiAutomaton<S1,T1>>(model, this);
		 modelTab.add(modelTabmodel);
		 
		 container1.add(verificationPanelmodel);
		
		 
		 
		 JLabel specificationLabel=new JLabel("Specification");
		 container1.add(specificationLabel);
		 	
		 
		 this.specificationPanelResults=new BuchiAutomatonJPanel<S1,T1, BuchiAutomaton<S1, T1>>(specification, this);
		 container1.add(specificationPanelResults);
		 
		 verificationSnapshot.add(container1);
		 
		 JPanel container2=new JPanel();
		 container2.setLayout(new BoxLayout(container2,BoxLayout.Y_AXIS));
		 
		 JLabel intersectionLabel=new JLabel("Intersection Automaton");
		 container2.add(intersectionLabel);
		 this.verificationPanelIntersection=new IntersectionAutomatonJPanel<S1, T1, S, T,IntersectionAutomaton<S1, T1, S, T>>(intersection, this);
		 container2.add(verificationPanelIntersection);
		 
		 
		 
		 
		 // Verification Results Tab
		 
		 this.intersectionPanelIntersection=new IntersectionAutomatonJPanel<S1, T1, S, T,IntersectionAutomaton<S1, T1, S, T>>(intersection, this);
		 JPanel containerInt1=new JPanel();
		 containerInt1.setLayout(new BoxLayout(containerInt1,BoxLayout.Y_AXIS));
		 containerInt1.add(new JLabel("Intersection automaton"));
		 containerInt1.add(this.intersectionPanelIntersection);
			
		 JPanel containerInt2=new JPanel();
		 containerInt2.setLayout(new BoxLayout(containerInt2,BoxLayout.Y_AXIS));
		 containerInt2.add(new JLabel("Brzozowski representation"));
		 this.brzozowskiSystem=new TextArea();
		 containerInt2.add(this.brzozowskiSystem);
		
		 verificationResults.add(containerInt1);
		 verificationResults.add(containerInt2);
		 
		
		 // Model checking results Tab	
		 JLabel resultsLabel=new JLabel("Model Checking results");
		 container2.add(resultsLabel);
		 
		 this.resultsJPanel=new ResultsJPanel<S1, T1, S, T,IntersectionAutomaton<S1, T1, S, T>>();
		 container2.add(resultsJPanel);
			
		 verificationSnapshot.add(container2);
		 jframe.setResizable(true);
		 jframe.setVisible(true);
		 
	}
	
	@Override
	public void updateModel(IncompleteBuchiAutomaton<S1, T1> model){
		this.modelTabmodel.update(model);
		this.verificationPanelmodel.update(model);
	}
	@Override
	public void updateSpecification(BuchiAutomaton<S1, T1> specification){
		this.specificationPanel.update(specification);
		this.specificationPanelResults.update(specification);
	}

	@Override
	public void updateIntersection(IntersectionAutomaton<S1, T1,S,T> intersection){
		this.verificationPanelIntersection.update(intersection);
		this.intersectionPanelIntersection.update(intersection);
	}

	@Override
	public void updateVerificationResults(ModelCheckerParameters<S1, S> verificationResults,
			IntersectionAutomaton<S1, T1,S,T> intersection) {
		
		this.resultsJPanel.updateResults(verificationResults);
		if(verificationResults.getResult()==0){
			this.intersectionPanelIntersection.highlightPath(verificationResults.getViolatingPath(), intersection);
		}
	}

	public void setBrzozoski(String system){
		this.brzozowskiSystem.setText(system);
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
						if(e.getSource().equals(this.editItem)){
							this.modelTabmodel.setEditingMode();
							this.verificationPanelmodel.setEditingMode();
							this.specificationPanelResults.setEditingMode();
						}
						else{
							if(e.getSource().equals(this.trasformItem)){
								this.modelTabmodel.setEditingMode();
								this.verificationPanelmodel.setEditingMode();

								this.specificationPanelResults.setTranformingMode();
							}
							else{
								if(e.getSource().equals(this.checkItem)){
									this.notifyObservers(new CheckAction());
								}
								else{
									if(e.getSource().equals(this.loadClaim)){
										this.notifyObservers(new LoadClaimAction(this.claimArea.getText()));
									}
								}
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
	private JMenuItem checkItem;
	
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
		
		checkItem=new JMenuItem("Check", KeyEvent.VK_T);
		editMenu.add(checkItem);
		checkItem.setMnemonic(KeyEvent.VK_O);
		checkItem.addActionListener(this);

		trasformItem.setMnemonic(KeyEvent.VK_O);
		trasformItem.addActionListener(this);
		
		
		menuBar.add(editMenu);
		
		

		
		
		jframe.setJMenuBar(menuBar);
		menuBar.setVisible(true);
		
	}
	
	protected JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        panel.setLayout(new GridLayout(1, 1));
        return panel;
    }
}
